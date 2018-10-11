package fr.istic.gm.weplan.server.config;

import fr.istic.gm.weplan.server.security.AjaxAuthFailureHandler;
import fr.istic.gm.weplan.server.security.AjaxAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static fr.istic.gm.weplan.infra.broker.impl.EventBrokerImpl.EVENT;
import static fr.istic.gm.weplan.server.config.consts.ApiParams.PWD;
import static fr.istic.gm.weplan.server.config.consts.ApiParams.USERNAME;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.API;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.CITY;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.DEPARTMENT;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.LOGIN;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.LOGOUT;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.REGION;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.USER;

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

    private final AjaxAuthSuccessHandler ajaxAuthSuccessHandler;

    private final AjaxAuthFailureHandler ajaxAuthFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(API_ALL)
                .antMatchers("/**.{js,html,css}")
                .antMatchers("/assets/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginProcessingUrl(LOGIN)
                .successHandler(ajaxAuthSuccessHandler)
                .failureHandler(ajaxAuthFailureHandler)
                .usernameParameter(USERNAME)
                .passwordParameter(PWD)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT))
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, USER).permitAll()
                .antMatchers(HttpMethod.GET, LOGIN).permitAll()
                .antMatchers(HttpMethod.GET, LOGOUT).authenticated()
                .antMatchers(HttpMethod.GET, CITY_ALL).authenticated()
                .antMatchers(HttpMethod.POST, CITY_ALL).authenticated()
                .antMatchers(HttpMethod.GET, DEPARTMENT_ALL).authenticated()
                .antMatchers(HttpMethod.GET, REGION_ALL).authenticated()
                .antMatchers(HttpMethod.GET, EVENT_ALL).authenticated()
                .antMatchers(HttpMethod.GET, USER_ALL).authenticated();
    }


    /**
     * @param auth the auth to use
     * @throws Exception if the user can't login
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("password")
                .roles("ADMIN", "USER");
    }
}
