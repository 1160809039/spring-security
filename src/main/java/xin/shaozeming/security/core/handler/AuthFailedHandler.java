package xin.shaozeming.security.core.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
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
 * \* Date: 2018/8/14
 * \* Time: 18:42
 * \* Description:
 * \  权限不足时抛出异常的处理handler
 */
@Component("authenticationAccessDeniedHandler")
public class AuthFailedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=utf-8");
         PrintWriter out = resp.getWriter();
         Response<CsrfToken> res= new Response<CsrfToken>(State.RES_NOPERMIT.getCode());

         if(e instanceof MissingCsrfTokenException || e instanceof InvalidCsrfTokenException){
             res= new Response<>(State.RES_PARAMERROR.getCode());
             //csrffilter类会将_scrf放入request里面，可以取出这个参数，此处示例，该项目不采用这种方法获取csrf参数
             res.setData((CsrfToken)req.getAttribute("_csrf"));
         }

        out.write(JSONObject.toJSONString(res));
        out.flush();
        out.close();
    }
}