package xin.shaozeming.security.core.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xin.shaozeming.security.common.Enum.State;
import xin.shaozeming.security.common.Response;
import xin.shaozeming.security.common.exception.NoLoginException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 邵泽铭
 * @date: 2018/8/30
 * @description:
 * 登录入口点，当非登录请求需要登录认证且未登录时，调用
 **/

@Component("customerAuthEntryPoint")
public class CustomerAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        String res="";
        if(e instanceof NoLoginException){
            res= JSONObject.toJSONString(new Response<>(State.RES_NOLOGIN.getCode()));
        }else {
            res= JSONObject.toJSONString(new Response<>(State.RES_INTERNET.getCode()));
        }
        out.write(JSONObject.toJSONString(res));
        out.flush();
        out.close();
    }
}