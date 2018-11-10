package fr.istic.gm.weplan.server.config;

import fr.istic.gm.weplan.domain.model.entities.Role;
import fr.istic.gm.weplan.server.security.AjaxAuthFailureHandler;
import fr.istic.gm.weplan.server.security.AjaxAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.STOMP;
import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.WS;
import static fr.istic.gm.weplan.server.config.consts.ApiParams.EMAIL;
import static fr.istic.gm.weplan.server.config.consts.ApiParams.PASWORD;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ALL = "/**";
    private static final String API_ALL = API + ALL;
    private static final String CITY_ALL = CITY + ALL;
    private static final String DEPARTMENT_ALL = DEPARTMENT + ALL;
    private static final String REGION_ALL = REGION + ALL;
    private static final String EVENT_ALL = EVENT + ALL;
    private static final String USER_ALL = USER + ALL;
    private static final String ACTIVITY_ALL = ACTIVITY + ALL;
    private static final String WS_ALL = WS + ALL;
    private static final String STOMP_ALL = STOMP + ALL;

    private final AjaxAuthSuccessHandler ajaxAuthSuccessHandler;

    private final AjaxAuthFailureHandler ajaxAuthFailureHandler;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/**.{js,html,css}")
                .antMatchers("/assets/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .formLogin()
                .loginProcessingUrl(LOGIN)
                .successHandler(ajaxAuthSuccessHandler)
                .failureHandler(ajaxAuthFailureHandler)
                .usernameParameter(EMAIL)
                .passwordParameter(PASWORD)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT))
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(WS_ALL).permitAll()
                .antMatchers(STOMP_ALL).permitAll()
                .antMatchers(HttpMethod.POST, LOGIN).permitAll()
                .antMatchers(HttpMethod.GET, AUTH + USER_CURRENT).authenticated()
                .antMatchers(HttpMethod.POST, USER).permitAll()
                .antMatchers(HttpMethod.POST, USER_ALL).authenticated()
                .antMatchers(HttpMethod.PATCH, USER_ALL).authenticated()
                .antMatchers(HttpMethod.DELETE, USER_ALL).authenticated()
                .antMatchers(HttpMethod.GET, USER_ALL).authenticated()
                .antMatchers(HttpMethod.GET, CITY_ALL).authenticated()
                .antMatchers(HttpMethod.GET, DEPARTMENT_ALL).authenticated()
                .antMatchers(HttpMethod.GET, REGION_ALL).authenticated()
                .antMatchers(HttpMethod.GET, EVENT_ALL).authenticated()
                .antMatchers(HttpMethod.GET, USER_ALL).authenticated()
                .antMatchers(HttpMethod.GET, ACTIVITY_ALL).authenticated()
                .antMatchers(HttpMethod.GET, LOGOUT).authenticated()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(API_ALL).hasAuthority(Role.ADMIN.name())
                .anyRequest().permitAll();
    }


    /**
     * @param auth the auth to use
     * @throws Exception if the user can't login
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
