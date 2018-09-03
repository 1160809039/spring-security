package xin.shaozeming.security.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import xin.shaozeming.security.common.Enum.State;
import xin.shaozeming.security.common.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/8/15
 * \* Time: 11:58
 * \* Description:
 * \
 */
@Component("logoutSuccessHandler")
public class LogoutSuccessHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Response response=new Response<>(State.RES_SUCCES.getCode());
        response.setMsg("退出成功");
        String  res= JSONObject.toJSONString(response);
        out.write(res);
        out.flush();
        out.close();
    }
}