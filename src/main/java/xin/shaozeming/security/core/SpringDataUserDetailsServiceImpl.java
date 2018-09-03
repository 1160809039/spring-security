package xin.shaozeming.security.core;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xin.shaozeming.security.common.UserInfoVO;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/8/14
 * \* Time: 9:21
 * \* Description:
 * \ 用于从数据库中取出用户信息，并且与传入的username参数做对比，判断用户名是否存在
 */
@Component("springDataUserDetailsService")
public class SpringDataUserDetailsServiceImpl implements UserDetailsService {

/**
 * 模拟一个用户，此处可以根据username去数据库select
* */

 private UserInfoVO userInfoVO;
public SpringDataUserDetailsServiceImpl(){
    List<GrantedAuthority> roles=new ArrayList<>();
    roles.add(new SimpleGrantedAuthority("ROLE_AUTH"));
    userInfoVO=new UserInfoVO(
            1,
            "admin",
            "admin",
            true,
            true,
            true,
            true,
            roles
    );
}



    @SuppressWarnings("unchecked")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        if(userInfoVO!=null){
            if(username.equals(userInfoVO.getUsername())){
                return userInfoVO;
            }
        }
        throw   new UsernameNotFoundException("用户名不存在");

    }
}