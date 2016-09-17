package rest.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import rest.controller.UserController;
import rest.entity.Consumer;
import rest.entity.Employee;
import rest.entity.User;
import rest.service.LoginHistoryService;
import rest.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * .hasAuthority()与.hasRole的区别:
 * hasAuthority直接进行比较
 * hasRole会将取得的结果加上前缀"ROLE_"再进行比较
 *
 * 可以通过实现AuthenticationProvider接口自定义用户验证方式
 * 注意：权限验证会取各个方法权限的并集，一处定义permitAll，另一处需要role，则访问需要role
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private loginSuccessHandler loginSuccessHandler;

    @Autowired
    private loginFailHandler loginFailHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/login").permitAll()
                    .antMatchers("/dishes/**").authenticated() //.hasAuthority("ADMIN") //.hasRole("ADMIN")
                    .antMatchers("/order/**").authenticated()
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .failureHandler(loginFailHandler).failureUrl("/login?error")
                    .successHandler(loginSuccessHandler)
                .and()
                    .logout().logoutSuccessUrl("/").invalidateHttpSession(true)
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new Md5PasswordEncoder());
    }

    @Component
    private class loginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
        @Autowired
        private LoginHistoryService loginService;
        @Override
        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
            logger.info(httpServletRequest.getParameter("username")+"登录成功");
            User user = (User) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            httpServletRequest.getSession().setAttribute("user",user);
            loginService.loginSuccess(user,httpServletRequest.getRemoteAddr());
            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
        }
    }

    @Component
    private class loginFailHandler extends SimpleUrlAuthenticationFailureHandler {
        @Autowired
        private LoginHistoryService loginService;

        @Override
        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
            logger.info(httpServletRequest.getParameter("username")+"登录失败");
            loginService.loginFailure(httpServletRequest.getParameter("username"),httpServletRequest.getParameter("password"),
                    httpServletRequest.getRemoteAddr());
            super.onAuthenticationFailure(httpServletRequest,httpServletResponse,exception);
        }
    }
}
