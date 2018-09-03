package xin.shaozeming.security.api;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.shaozeming.security.common.Enum.State;
import xin.shaozeming.security.common.Response;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author: 邵泽铭
 * @date: 2018/8/13
 * @description:
 */


@RestController
public class TestController {

    private  static  final  String CSRFATTR="org.springframework.core.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    @GetMapping("/login_error")
    public Response loginFail(){
        return new Response<String>(State.RES_NOLOGIN.getCode());
    }


    @GetMapping("/user") //需要 ROLE_AUTH 权限
    public Response user(){
        return  new Response(State.RES_SUCCES.getCode());
    }

    @GetMapping("/test")  //需要 ROLE_ADMIN 权限
    public Response test(){
        return  new Response(State.RES_SUCCES.getCode());
    }


    /**
    *  自制csrf
    * */
    @GetMapping("/getToken")
    public Response getToken(HttpServletRequest request){
       String uuid=  UUID.randomUUID().toString();
        HttpSession session;
        session = request.getSession(false);
        if (session != null) {
            session.getAttribute(CSRFATTR);
            session.removeAttribute(CSRFATTR);
        }
        CsrfToken token=  new DefaultCsrfToken("X-CSRF-TOKEN","_csrf",uuid);
        session = request.getSession();
        session.setAttribute(CSRFATTR, token);
        request.setAttribute(token.getParameterName(), token);
        Response<CsrfToken> response=new Response<>(State.RES_SUCCES.getCode());
        response.setData(token);
        return response;
    }
}