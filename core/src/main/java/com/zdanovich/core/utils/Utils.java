package com.zdanovich.core.utils;

public class Utils {

    public static final String RESOURCES = "resources";

    public static final String DATABASE = "database";
    public static final String DRIVER_CLASS_NAME = "driver";
    public static final String CLASS_PATH = "classpath";
    public static final String URL = "url";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String LIQUIBASE_CHANGELOG_FILE = "changeLogFile";

    public static final String HIBERNATE_GENERATE_DDL = "hibernate.generate_ddl";

    public static final String AIR_CONDITIONER = "air conditioner";
    public static final String CABLE_TV = "cable TV";
    public static final String CAR_RENTAL = "car rental";
    public static final String MINI_BAR = "mini-bar";
    public static final String PARKING = "parking";
    public static final String RESTAURANT = "restaurant";
    public static final String ROOM_SERVICE = "room service";
    public static final String SPA = "spa";
    public static final String SWIMMING_POOL = "swimming pool";
    public static final String WI_FI = "wi-fi";

    public static final String EMPTY_STRING = "";

    private Utils() {}

    public static String absoluteToClasspath(String absolutePath) {
        String relativePath = absolutePath.substring(absolutePath.lastIndexOf(RESOURCES) + RESOURCES.length());
        return String.format("%s:%s", CLASS_PATH, relativePath);
    }
}
