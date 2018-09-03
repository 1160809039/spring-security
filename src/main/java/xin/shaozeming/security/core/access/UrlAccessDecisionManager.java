package xin.shaozeming.security.core.access;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import xin.shaozeming.security.common.exception.NoLoginException;


import java.util.Collection;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/8/14
 * \* Time: 14:40
 * \* Description:
 *
 * \ 判断是否登录以及是否具有相应的权限，抛出相应的异常，登录且有权限的不做处理
 * 这里只判断是否登录
 */
@Component("urlAccessDecisionManager")
public class UrlAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
    boolean islogin= authentication  instanceof AnonymousAuthenticationToken;
    if(islogin){
        throw new NoLoginException("未登录");
    }else {
        for (ConfigAttribute role:collection){
            String needRole= role.getAttribute();
            if(!authentication.getAuthorities().toString().contains(needRole)){
                throw new AccessDeniedException("权限不足!");
            }
        }
    }
       //  throw new AccessDeniedException("权限不足!");

    }


    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}