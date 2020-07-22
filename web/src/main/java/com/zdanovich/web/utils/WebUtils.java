package com.zdanovich.web.utils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WebUtils {

    public static final String OPEN_API_INFO_TITLE = "openapi.info.title";
    public static final String OPEN_API_INFO_DESCRIPTION = "openapi.info.description";
    public static final String OPEN_API_INFO_TERMS_OF_SERVICE = "openapi.info.termsOfService";
    public static final String OPEN_API_INFO_VERSION = "openapi.info.version";
    public static final String OPEN_API_SERVER_DESCRIPTION = "openapi.server[%d].description";
    public static final String OPEN_API_SERVER_URL = "openapi.server[%d].url";
    public static final String OPEN_API_CONTACT_NAME = "openapi.contact.name";
    public static final String OPEN_API_CONTACT_URL = "openapi.contact.url";
    public static final String OPEN_API_CONTACT_EMAIL = "openapi.contact.email";
    public static final String OPEN_API_LICENSE_NAME = "openapi.license.name";
    public static final String OPEN_API_LICENSE_URL = "openapi.license.url";

    private WebUtils() {
    }
}