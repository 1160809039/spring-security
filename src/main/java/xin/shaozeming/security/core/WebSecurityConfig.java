package xin.shaozeming.security.core;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import xin.shaozeming.security.core.access.UrlAccessDecisionManager;
import xin.shaozeming.security.core.access.UrlFilterInvocationSecurityMetadataSource;
import xin.shaozeming.security.core.filter.CustomerAuthenticationProcessingFilter;
import xin.shaozeming.security.core.handler.*;
import xin.shaozeming.security.core.provider.CustomerAuthenticationProvider;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/8/13
 * \* Time: 18:05
 * \* Description:
 * \
 */

@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Resource
    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    @Resource
    private UrlAccessDecisionManager urlAccessDecisionManager;
    @Resource
    private SpringDataUserDetailsServiceImpl springDataUserDetailsService;

    @Resource
    private AuthFailedHandler authFailedHandler;

    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Resource
    private LoginFailedHandler loginFailedHandler;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private CustomerAuthenticationProvider customerAuthenticationProvider;
    @Resource
    private CustomerAuthEntryPoint customerAuthEntryPoint;


    /**
     * 配置用户service
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(springDataUserDetailsService);
    }

    /**
     * 配置需要忽略的路径（此处示例）
    * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**");

    }
    private  CustomerAuthenticationProcessingFilter customerAuthenticationProcessingFilter(AuthenticationManager  authenticationManager) {

        CustomerAuthenticationProcessingFilter customerAuthenticationProcessingFilter = new CustomerAuthenticationProcessingFilter();
        //为过滤器添加认证器
        customerAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        //重写认证失败时的跳转页面
        customerAuthenticationProcessingFilter.setAuthenticationFailureHandler(loginFailedHandler);
        customerAuthenticationProcessingFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        return customerAuthenticationProcessingFilter;
    }
    /**
     * 核心配置
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomerAuthenticationProcessingFilter customerAuthenticationProcessingFilter=  new CustomerAuthenticationProcessingFilter();
        List<AuthenticationProvider> list= new ArrayList<>();
        list.add(customerAuthenticationProvider);
        customerAuthenticationProcessingFilter.setAuthenticationManager(new ProviderManager(list));
        customerAuthenticationProcessingFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        customerAuthenticationProcessingFilter.setAuthenticationFailureHandler(loginFailedHandler);

        http
                .addFilterBefore(customerAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(customerAuthenticationProvider)
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <T extends FilterSecurityInterceptor> T postProcess(T accessManager) {
                        accessManager.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        accessManager.setAccessDecisionManager(urlAccessDecisionManager);
                        return accessManager;
                    }
                }).mvcMatchers("/**")
                .authenticated()
                .and().sessionManagement().sessionAuthenticationFailureHandler(loginFailedHandler)
                .and().csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
            @Override
            public boolean matches(HttpServletRequest request) {
                String uri=request.getRequestURI();
//                if(uri.contains("person")||uri.contains("commentadd")||uri.contains("login")){
//                    return true;
//                }
                // 此处可以自定义需要csrf的链接
                return true;
            }
        })
                .and().logout().addLogoutHandler(logoutSuccessHandler).permitAll()
                .and().exceptionHandling().accessDeniedHandler(authFailedHandler).authenticationEntryPoint(customerAuthEntryPoint);

//        ExceptionHandlingConfigurer
//        FormLoginConfigurer
//        SecurityContextPersistenceFilter
//        ExceptionTranslationFilter
//        AnonymousAuthenticationFilter
//        SessionManagementFilter

    }
}