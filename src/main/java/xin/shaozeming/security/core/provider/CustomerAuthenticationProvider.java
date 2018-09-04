package xin.shaozeming.security.core.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xin.shaozeming.security.common.UserInfoVO;
import xin.shaozeming.security.core.SpringDataUserDetailsServiceImpl;

import javax.annotation.Resource;

/**
 * @author: 邵泽铭
 * @date: 2018/8/30
 * @description:
 *  provider 主要用途 调用验证账号密码方法 对传入的参数和返回结果进一步处理
 **/
@Component("customerAuthenticationProvider")
public class CustomerAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Resource
    private SpringDataUserDetailsServiceImpl userDetailsService;

    @Resource
    private PasswordEncoderImpl passwordEncoder;

//    private volatile String userNotFoundEncodedPassword;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = this.passwordEncoder.encode(authentication.getCredentials().toString());
            if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    /**
    * 此处可以自定义用户不存在异常
    * */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser =new UserInfoVO();
        try {
            loadedUser = userDetailsService.loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            } else {
                return loadedUser;
            }
        } catch (UsernameNotFoundException var4) {

            this.mitigateAgainstTimingAttack(authentication,loadedUser.getPassword()==null?"":loadedUser.getPassword());
            throw var4;
        } catch (InternalAuthenticationServiceException var5) {
            throw var5;
        } catch (Exception var6) {
            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
        }
    }

    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication, String password) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, password);
        }

    }

//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
}