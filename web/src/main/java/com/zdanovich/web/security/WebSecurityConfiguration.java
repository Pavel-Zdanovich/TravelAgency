package com.zdanovich.web.security;

import com.zdanovich.web.controller.auth.AuthController;
import com.zdanovich.web.security.auth.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String DEFAULT_FILTER_PROCESSES_URL = "/**";

    @Autowired
    private AuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Filter jwtAuthenticationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();// BCryptPasswordEncoder TODO
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
        //auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.requiresChannel()
                //.mvcMatchers("/**") //TODO mvc, ant, regex, request, what the difference?
                //.requiresSecure()
                //.and()

                /*.sessionManagement()
                .sessionFixation()//TODO read about session fixation problem
                .none()
                .and()*/

                //csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()//TODO read about csrf token for React

                .authorizeRequests().antMatchers(AuthController.PATH).permitAll().and()

                .authorizeRequests().anyRequest().authenticated().and()

                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint()).and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .logout().logoutUrl(AuthController.PATH + AuthController.LOGOUT);
                //.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AuthController.PATH + DEFAULT_FILTER_PROCESSES_URL);
    }
}