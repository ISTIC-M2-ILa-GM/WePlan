package fr.istic.gm.weplan.server.config;

import fr.istic.gm.weplan.domain.model.entities.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.API;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.CITY;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.DEPARTMENT;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.LOGIN;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.LOGOUT;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.REGION;
import static fr.istic.gm.weplan.server.config.consts.ApiRoutes.USER;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ALL = "/**";
    private static final String CITY_ALL = CITY + ALL;
    private static final String DEPARTMENT_ALL = DEPARTMENT + ALL;
    private static final String REGION_ALL = REGION + ALL;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity http) {
        http.ignoring()
                .antMatchers("/**.{js,html,css}")
                .antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .formLogin()
                .loginProcessingUrl(LOGIN)
                .successHandler(ajaxAuthenticationSuccessHandler)
                .failureHandler(ajaxAuthenticationFailureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT))
                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, USER).permitAll()
                .antMatchers(HttpMethod.GET, LOGIN).permitAll()
                .antMatchers(HttpMethod.GET, LOGOUT).authenticated()
                .antMatchers(HttpMethod.GET, CITY_ALL).authenticated()
                .antMatchers(HttpMethod.GET, CITY + "/**").authenticated()
                .antMatchers(HttpMethod.GET, CITY + "/**").authenticated()
                .antMatchers(HttpMethod.GET, CITY + "/**").authenticated()
                .antMatchers(HttpMethod.GET, CITY + "/**").authenticated()
                .antMatchers(HttpMethod.GET, CITY + "/**").authenticated()
                .antMatchers(HttpMethod.GET, CITY + "/**").authenticated()
                .antMatchers(API + "/**").hasAuthority(Role.ADMIN);

    }

    /**
     * @param auth the auth to use
     * @throws Exception if the user can't login
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
