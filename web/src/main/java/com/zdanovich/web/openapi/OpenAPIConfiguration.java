package com.zdanovich.web.openapi;

import com.zdanovich.web.controller.system.AuthController;
import com.zdanovich.web.utils.WebUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
        "org.springdoc",
        "org.springframework.boot.autoconfigure.jackson"
})
public class OpenAPIConfiguration {

    public static final String OPEN_API_PATH = "/v3/api-docs";
    public static final String SWAGGER_UI_PATH = "/swagger-ui";

    @Autowired
    private Properties webProperties;

    @Bean
    @Autowired
    public OpenApiCustomiser openApiCustomiser(Contact contact, License license, Components components, List<SecurityRequirement> securityRequirements) {
        return new OpenApiCustomiser() {
            @Override
            public void customise(OpenAPI openApi) {
                openApi.getInfo()
                        .termsOfService(webProperties.getProperty(WebUtils.OPEN_API_INFO_TERMS_OF_SERVICE))
                        .contact(contact)
                        .license(license);

                openApi.components(components)
                        .security(securityRequirements);
            }
        };
    }

    @Bean
    public Contact contact() {
        Contact contact = new Contact();
        contact.setName(webProperties.getProperty(WebUtils.OPEN_API_CONTACT_NAME));
        contact.setUrl(webProperties.getProperty(WebUtils.OPEN_API_CONTACT_URL));
        contact.setEmail(webProperties.getProperty(WebUtils.OPEN_API_CONTACT_EMAIL));
        return contact;
    }

    @Bean
    public License license() {
        License license = new License();
        license.setName(webProperties.getProperty(WebUtils.OPEN_API_LICENSE_NAME));
        license.setUrl(webProperties.getProperty(WebUtils.OPEN_API_LICENSE_URL));
        return license;
    }

    @Bean
    @Autowired
    public Components components(SecurityScheme securityScheme) {
        Components components = new Components();
        components.addSecuritySchemes("json web token", securityScheme);
        return components;
    }

    @Bean
    @Autowired
    public SecurityScheme securityScheme(OAuthFlows oAuthFlows) {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.set$ref("$ref");
        securityScheme.setBearerFormat("bearer format");
        securityScheme.setDescription("description");

        securityScheme.setFlows(oAuthFlows);

        securityScheme.setName("security scheme name");
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.setOpenIdConnectUrl("open id connect url");
        securityScheme.setScheme("security scheme scheme");
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        return securityScheme;
    }

    @Bean
    public OAuthFlows oAuthFlows() {
        OAuthFlows oAuthFlows = new OAuthFlows();

        OAuthFlow oAuthFlow = new OAuthFlow();
        oAuthFlow.setAuthorizationUrl(AuthController.PATH + AuthController.LOGIN);
        oAuthFlow.setRefreshUrl("refresh url");
        oAuthFlow.setScopes(new Scopes()
                .addString("create", "access to create")
                .addString("read", "access to read")
                .addString("update", "access to update")
                .addString("delete", "access to delete"));
        oAuthFlow.setTokenUrl("token url");

        oAuthFlows.authorizationCode(oAuthFlow);
        return oAuthFlows;
    }

    @Bean
    public List<SecurityRequirement> securityRequirements() {
        List<SecurityRequirement> securityRequirements = new ArrayList<>();
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("JWTSecurity", "read");
        securityRequirements.add(securityRequirement);
        return securityRequirements;
    }

    @Bean
    public HttpMessageConverter<?> stringHttpMessageConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }
}