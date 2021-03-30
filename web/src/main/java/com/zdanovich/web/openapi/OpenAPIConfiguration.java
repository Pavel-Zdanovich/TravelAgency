package com.zdanovich.web.openapi;

import com.zdanovich.core.entity.User;
import com.zdanovich.core.utils.CoreUtils;
import com.zdanovich.web.controller.system.AuthController;
import com.zdanovich.web.utils.WebUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
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
@ComponentScan(basePackages = {"org.springdoc", "org.springframework.boot.autoconfigure.jackson"})
public class OpenAPIConfiguration {

    public static final String OPEN_API_PATH = "/v3/api-docs";
    public static final String SWAGGER_UI_PATH = "/swagger-ui";

    @Autowired
    private Properties webProperties;

    @Bean
    @Autowired
    public OpenApiCustomiser openApiCustomiser(Contact contact, License license, RequestBody requestBody, SecurityScheme securityScheme,
                                               List<SecurityRequirement> securityRequirements) {
        return new OpenApiCustomiser() {
            @Override
            public void customise(OpenAPI openApi) {
                openApi.getInfo()
                        .title(webProperties.getProperty(WebUtils.OPEN_API_INFO_TITLE))
                        .description(webProperties.getProperty(WebUtils.OPEN_API_INFO_DESCRIPTION))
                        .termsOfService(webProperties.getProperty(WebUtils.OPEN_API_INFO_TERMS_OF_SERVICE))
                        .contact(contact)
                        .license(license)
                        .version(webProperties.getProperty(WebUtils.OPEN_API_INFO_VERSION));

                openApi.getPaths().forEach((path, pathItem) -> {
                    pathItem.readOperations().forEach(operation -> {
                        operation.setSecurity(securityRequirements);
                        if (operation.getParameters() != null) {
                            operation.getParameters().forEach(parameter -> {
                                if (parameter != null) {
                                    parameter.setRequired(Boolean.FALSE);
                                }
                            });
                        }
                    });
                });

                openApi.getPaths()
                        .get(AuthController.PATH + AuthController.SIGN_IN)
                        .getPost()
                        .security(null)
                        .setRequestBody(requestBody);
                openApi.getPaths()
                        .get(AuthController.PATH + AuthController.SIGN_UP)
                        .getPost()
                        .security(null)
                        .setRequestBody(requestBody);//TODO remove username and password example

                openApi.getComponents()
                        .addSecuritySchemes(webProperties.getProperty(WebUtils.OPEN_API_SECURITY_SCHEMA), securityScheme);
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
    public RequestBody requestBody() {
        RequestBody requestBody = new RequestBody();
        requestBody.setDescription("Authentication request body");

        Content content = new Content();

        MediaType mediaType = new MediaType();

        Schema<Object> authenticationSchema = new ObjectSchema();
        authenticationSchema.addRequiredItem(CoreUtils.USERNAME);
        authenticationSchema.addRequiredItem(CoreUtils.PASSWORD);

        Schema<String> usernameSchema = new StringSchema();
        usernameSchema.maxLength(30);
        usernameSchema.minLength(5);
        usernameSchema.setPattern(User.ONE_WORD_REGEX);
        usernameSchema.setExample("ElonMusk");
        authenticationSchema.addProperties(CoreUtils.USERNAME, usernameSchema);

        Schema<String> passwordSchema = new StringSchema();
        passwordSchema.maxLength(100);
        passwordSchema.minLength(5);
        passwordSchema.setExample("SpaceXXX");
        authenticationSchema.addProperties(CoreUtils.PASSWORD, passwordSchema);

        mediaType.setSchema(authenticationSchema);

        content.addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType);

        requestBody.setContent(content);

        requestBody.setRequired(Boolean.TRUE);
        return requestBody;
    }

    @Bean
    public SecurityScheme securityScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.APIKEY);
        securityScheme.setName(webProperties.getProperty(WebUtils.JWT_HEADER_NAME));
        securityScheme.setIn(SecurityScheme.In.HEADER);
        return securityScheme;
    }

    @Bean
    public List<SecurityRequirement> securityRequirements() {
        List<SecurityRequirement> securityRequirements = new ArrayList<>();
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList(webProperties.getProperty(WebUtils.OPEN_API_SECURITY_SCHEMA));
        securityRequirements.add(securityRequirement);
        return securityRequirements;
    }

    @Bean
    public HttpMessageConverter<?> stringHttpMessageConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }
}