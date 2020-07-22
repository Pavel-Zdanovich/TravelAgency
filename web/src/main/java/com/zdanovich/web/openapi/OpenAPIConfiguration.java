package com.zdanovich.web.openapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class OpenAPIConfiguration {

    public static final String OPEN_API_PATH = "/v3/api-docs";
    public static final String SWAGGER_UI_PATH = "/swagger-ui";

    @Autowired
    private Properties webProperties;

    /*@Bean
    @Autowired
    public OpenAPI openAPI(Info info, ExternalDocumentation externalDoc, List<Server> servers, List<SecurityRequirement> securityRequirements) {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
        openAPI.setExternalDocs(externalDoc);
        openAPI.setServers(servers);
        openAPI.setSecurity(securityRequirements);
        *//*openAPI.setTags(tags);
        openAPI.setPaths(paths);
        openAPI.setComponents(components);*//*
        return openAPI;
    }

    @Bean
    @Autowired
    public Info info(Contact contact, License license) {
        Info info = new Info();
        info.setTitle(webProperties.getProperty(WebUtils.OPEN_API_INFO_TITLE));
        info.setDescription(webProperties.getProperty(WebUtils.OPEN_API_INFO_DESCRIPTION));
        info.setTermsOfService(webProperties.getProperty(WebUtils.OPEN_API_INFO_TERMS_OF_SERVICE));
        info.setContact(contact);
        info.setLicense(license);
        info.setVersion(webProperties.getProperty(WebUtils.OPEN_API_INFO_VERSION));
        return info;
    }

    @Bean
    public ExternalDocumentation externalDocumentation() {
        ExternalDocumentation externalDocumentation = new ExternalDocumentation();
        externalDocumentation.setDescription("");
        externalDocumentation.setUrl("");
        return externalDocumentation;
    }

    @Bean
    public List<Server> servers() {
        List<Server> servers = new ArrayList<>();
        Server server = new Server();
        server.setDescription(webProperties.getProperty(String.format(WebUtils.OPEN_API_SERVER_URL, 0)));
        server.setUrl(webProperties.getProperty(String.format(WebUtils.OPEN_API_SERVER_URL, 0)));
        //server.setVariables(new ServerVariables().addServerVariable("", new ServerVariable()));
        servers.add(server);
        return servers;
    }

    @Bean
    public List<SecurityRequirement> securityRequirements() {
        List<SecurityRequirement> securityRequirements = new ArrayList<>();
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("JWTSecurity", "");
        securityRequirements.add(securityRequirement);
        return securityRequirements;
    }*/

    /*@Bean
    @Autowired
    public List<Tag> tags(ExternalDocumentation externalDocumentation) {
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("");
        tag.setDescription("");
        tag.setExternalDocs(externalDocumentation);
        tags.add(tag);
        return tags;
    }

    @Bean
    public Paths paths() {
        Paths paths = new Paths();
        paths.setExtensions(Collections.emptyMap());
        return paths;
    }

    @Bean
    public Components components() {
        Components components = new Components();
        components.addCallbacks("", );
        return components;
    }*/

    /*@Bean
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
    }*/
}