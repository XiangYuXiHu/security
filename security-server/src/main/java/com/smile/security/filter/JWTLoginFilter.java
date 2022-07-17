package com.smile.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smile.security.basic.MyUserDetails;
import com.smile.security.basic.User;
import com.smile.security.exception.BizException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * 验证用户名密码正确后，生成一个token，放在header里，返回给客户端
 *
 * @Description
 * @ClassName JWTLoginFilter
 * @Author smile
 * @date 2022.07.17 15:12
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 接收并解析用户凭证，出現错误时，返回json数据前端
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = JSONObject.parseObject(request.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList()));
        } catch (IOException e) {
            try {
                handleResponse(response, SC_UNAUTHORIZED, "账号或密码错误", null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new BizException("账号或密码错误");
        }
    }

    /**
     * 用户登录成功后，生成token,并且返回json数据给前端
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                //subject用一个用户名,也可以是多个信息的组合，根据需要来定
                .setSubject(((MyUserDetails) authResult.getPrincipal()).getUsername())
                //设置过期时间 24h
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                //设置token签名、密钥
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret").compact();

        response.addHeader("Authorization", "Bearer " + token);
        handleResponse(response, SC_OK, "登录成功", null);
    }

    /**
     * 处理响应
     *
     * @param response
     * @param code
     * @param message
     * @param data
     * @throws IOException
     */
    private void handleResponse(HttpServletResponse response, int code, String message, Object data) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("message", message);
        params.put("data", data);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(params));
        writer.flush();
        writer.close();
    }
}
