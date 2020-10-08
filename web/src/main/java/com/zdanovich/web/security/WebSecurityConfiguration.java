package com.zdanovich.web.security;

import com.zdanovich.web.controller.system.AuthController;
import com.zdanovich.web.openapi.OpenAPIConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.Filter;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {// TODO AuthConfiguration

    public static final String ANY_URL = "/**";

    public static final AuthenticationEntryPoint AUTHENTICATION_ENTRY_POINT = new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    public static final LogoutSuccessHandler LOGOUT_SUCCESS_HANDLER = new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK);

    @Autowired
    private AuthenticationProvider authService;
    @Autowired
    private Filter jsonWebAuthenticationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authService);
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.requiresChannel().antMatchers(ANY_URL).requiresSecure().and() //add ChannelProcessingFilter //TODO read about enable https on Tomcat

                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //TODO read about session fixation problem

                .csrf().disable()//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()//TODO read about csrf token for React

                //.headers().contentSecurityPolicy("csp-report-endpoint").and().and() //TODO read about CSP

                .authenticationProvider(authService).authorizeRequests().anyRequest().authenticated().and()

                .exceptionHandling().authenticationEntryPoint(AUTHENTICATION_ENTRY_POINT).and()

                //WebAsyncManagerIntegrationFilter
                //SecurityContextPersistenceFilter
                //HeaderWriterFilter
                //CsrfFilter
                //LogoutFilter
                //RequestCacheAwareFilter
                //SecurityContextHolderAwareRequestFilter
                //INSERT JsonWebAuthenticationFilter HERE
                .addFilterBefore(jsonWebAuthenticationFilter, AnonymousAuthenticationFilter.class)
                //AnonymousAuthenticationFilter
                .anonymous().authorities(new ArrayList<>(Authorities.getFor(Authorities.GUEST_ROLE))).and()
                //SessionManagementFilter
                //ExceptionTranslationFilter
                //FilterSecurityInterceptor

                .formLogin().disable()
                .httpBasic().disable()

                .logout().logoutUrl(AuthController.PATH + AuthController.LOGOUT).logoutSuccessHandler(LOGOUT_SUCCESS_HANDLER);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                OpenAPIConfiguration.OPEN_API_PATH + ANY_URL,
                OpenAPIConfiguration.SWAGGER_UI_PATH + ANY_URL,
                OpenAPIConfiguration.SWAGGER_UI_PATH + ".html"
        );
    }
}