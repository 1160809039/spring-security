package xin.shaozeming.security.core.access;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/8/14
 * \* Time: 14:13
 * \* Description:
 * \  自定义传入的请求需要什么权限，返回Null不需要任何权限，也不需要认证
// */
@Component("urlFilterInvocationSecurityMetadataSource")
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    Map<String,String> map=new HashMap<>();

    public UrlFilterInvocationSecurityMetadataSource(){
        /**
         * 此处模拟权限数据，该数据可以从数据库中读取
        * */
        map.put("user","ROLE_AUTH");
        map.put("test","ROLE_ADMIN");
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation)o).getRequest();
        String uri=  request.getRequestURI();
        uri=uri.substring(1,uri.length());

       //创建该请求所需的权限列表
        Collection<ConfigAttribute> roles=   new ArrayList<>();
       for (String key: map.keySet()){
           if(key.equals(uri)){
               roles.add(new SecurityConfig(map.get(key)));
           }
       }

        return roles;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}