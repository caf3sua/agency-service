package com.baoviet.agency.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String formatterDateText = "dd/MM/yyyy";
    public static final String formatterDateTimeText = "dd/MM/yyyy HH:mm:ss";
    
    public static final String SUCCESS = "Success";
    
    private Constants() {
    }
}
