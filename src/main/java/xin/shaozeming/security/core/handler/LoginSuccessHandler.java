package xin.shaozeming.security.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import xin.shaozeming.security.common.Enum.State;
import xin.shaozeming.security.common.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/8/15
 * \* Time: 11:36
 * \* Description:
 * \ 登录成功处理
 */
@Component("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        Response<CsrfToken> response=new Response<CsrfToken>(State.RES_SUCCES.getCode());
        response.setMsg("登录成功");
        response.setData((CsrfToken)req.getAttribute("_csrf"));
        String  res= JSONObject.toJSONString(response);
        out.write(res);
        out.flush();
        out.close();
    }
}