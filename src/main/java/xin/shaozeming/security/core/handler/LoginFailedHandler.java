package xin.shaozeming.security.core.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import xin.shaozeming.security.common.Enum.State;
import xin.shaozeming.security.common.Response;
import xin.shaozeming.security.common.exception.NoLoginException;
import xin.shaozeming.security.common.exception.ParamException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/8/15
 * \* Time: 11:39
 * \* Description:
 * \ 登录时异常处理
 */
@Component("loginFailedHandler")
public class LoginFailedHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        String res="";

        if (e instanceof BadCredentialsException) {
                res=JSONObject.toJSONString(new Response<>(State.RES_NOUSERORPASS.getCode()));
        }else if(e instanceof NoLoginException){
            res=JSONObject.toJSONString(new Response<>(State.RES_NOLOGIN.getCode()));
        }else if(e instanceof ParamException){
            res=JSONObject.toJSONString(new Response<>(State.RES_PARAMERROR.getCode()));
        }
        else {
            res=JSONObject.toJSONString(new Response<>(State.RES_INTERNET.getCode()));
        }

        out.write(res);
        out.flush();
        out.close();
    }
}