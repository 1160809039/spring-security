package xin.shaozeming.security.core.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import xin.shaozeming.security.common.exception.ParamException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author: 邵泽铭
 * @date: 2018/8/30
 * @description:
 * 过滤器，用于自定义请求路径，请求方式，取出uername和password参数的方式
 *
 * demo现在的login请求参数是  user={username:'admin',password:'admin'}
 **/

public class CustomerAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_PARAM = "user";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";


    public CustomerAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
     }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username;
            String password;
            String loginParam=request.getParameter(LOGIN_PARAM);
            try {
              Map map= (Map) JSONObject.parse(loginParam);
                username=map.get(USERNAME).toString();
                password=map.get(PASSWORD).toString();
            }catch (Exception e){
                throw new ParamException("异常");
            }

            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}