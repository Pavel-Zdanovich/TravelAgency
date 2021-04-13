package com.zdanovich.web.security;

import com.zdanovich.web.controller.system.AuthController;
import com.zdanovich.web.openapi.OpenAPIConfiguration;
import com.zdanovich.web.security.jwt.JsonWebAuthenticationFilter;
import com.zdanovich.web.service.AuthService;
import com.zdanovich.web.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(
        prePostEnabled = true //enables @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
        //securedEnabled = true, //enables @Secured
        //jsr250Enabled = true //enables @RolesAllowed
)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {// TODO AuthConfiguration

    public static final String ANY_URL = "/**";

    public static final RequestMatcher CORS_REQUEST_MATCHER = new AntPathRequestMatcher(ANY_URL, HttpMethod.OPTIONS.name());
    public static final RequestMatcher SIGN_UP_REQUEST_MATCHER = new AntPathRequestMatcher(AuthController.PATH + AuthController.SIGN_UP, HttpMethod.POST.name(), true);
    public static final RequestMatcher SIGN_IN_REQUEST_MATCHER = new AntPathRequestMatcher(AuthController.PATH + AuthController.SIGN_IN, HttpMethod.POST.name(), true);

    public static final AuthenticationEntryPoint AUTHENTICATION_ENTRY_POINT = new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    public static final LogoutSuccessHandler LOGOUT_SUCCESS_HANDLER = new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK);

    @Autowired
    private Properties webProperties;
    @Autowired
    private AuthService authService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.requiresChannel().antMatchers(ANY_URL).requiresSecure().and() //add ChannelProcessingFilter //TODO read about enable https on Tomcat

                //.headers().contentSecurityPolicy("csp-report-endpoint").and().and() //TODO read about CSP

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //TODO read about session fixation problem

                //.authorizeRequests(authorizeRequests -> authorizeRequests.requestMatchers(CORS_REQUEST_MATCHER, SIGN_UP_REQUEST_MATCHER, SIGN_IN_REQUEST_MATCHER).permitAll())

                //.authorizeRequests().anyRequest().authenticated().and()

                .authenticationProvider(this.authService).exceptionHandling().authenticationEntryPoint(AUTHENTICATION_ENTRY_POINT).and()

                /** {@link org.springframework.security.config.annotation.web.builders.FilterComparator} */

                //WebAsyncManagerIntegrationFilter
                //SecurityContextPersistenceFilter
                //HeaderWriterFilter

                .cors(httpSecurityCorsConfigurer -> {
                    UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
                    corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
                    corsConfiguration.setAllowedHeaders(Arrays.asList(HttpHeaders.CONTENT_TYPE, webProperties.getProperty(WebUtils.JWT_HEADER_NAME)));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfigurationSource.registerCorsConfiguration(ANY_URL, corsConfiguration);
                    httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource);
                }) //CorsFilter

                .csrf().disable() //C̶s̶r̶f̶F̶i̶l̶t̶e̶r̶

                .addFilterBefore(
                        new JsonWebAuthenticationFilter(
                                new NegatedRequestMatcher(
                                        new OrRequestMatcher(
                                                WebSecurityConfiguration.CORS_REQUEST_MATCHER,
                                                WebSecurityConfiguration.SIGN_UP_REQUEST_MATCHER,
                                                WebSecurityConfiguration.SIGN_IN_REQUEST_MATCHER
                                        )
                                ),
                                this.authService
                        ),
                        LogoutFilter.class
                )

                .logout().logoutUrl(AuthController.PATH + AuthController.SIGN_OUT).logoutSuccessHandler(LOGOUT_SUCCESS_HANDLER).and() //LogoutFilter

                //U̶s̶e̶r̶n̶a̶m̶e̶P̶a̶s̶s̶w̶o̶r̶d̶A̶u̶t̶h̶e̶n̶t̶i̶c̶a̶t̶i̶o̶n̶F̶i̶l̶t̶e̶r̶
                .formLogin().disable() //D̶e̶f̶a̶u̶l̶t̶L̶o̶g̶i̶n̶P̶a̶g̶e̶G̶e̶n̶e̶r̶a̶t̶i̶n̶g̶F̶i̶l̶t̶e̶r̶  D̶e̶f̶a̶u̶l̶t̶L̶o̶g̶o̶u̶t̶P̶a̶g̶e̶G̶e̶n̶e̶r̶a̶t̶i̶n̶g̶F̶i̶l̶t̶e̶r̶
                .httpBasic().disable() //B̶a̶s̶i̶c̶A̶u̶t̶h̶e̶n̶t̶i̶c̶a̶t̶i̶o̶n̶F̶i̶l̶t̶e̶r̶
                //RequestCacheAwareFilter
                //SecurityContextHolderAwareRequestFilter
                .anonymous().disable() //A̶n̶o̶n̶y̶m̶o̶u̶s̶A̶u̶t̶h̶e̶n̶t̶i̶c̶a̶t̶i̶o̶n̶F̶i̶l̶t̶e̶r̶
                //SessionManagementFilter
                //ExceptionTranslationFilter
                //FilterSecurityInterceptor
                //.headers().cacheControl().disable() //TODO
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        OpenAPIConfiguration.OPEN_API_PATH + ANY_URL,
                        OpenAPIConfiguration.SWAGGER_UI_PATH + ANY_URL,
                        OpenAPIConfiguration.SWAGGER_UI_PATH + ".html"
                );
    }
}