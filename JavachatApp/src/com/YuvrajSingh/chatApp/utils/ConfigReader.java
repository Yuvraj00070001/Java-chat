package com.YuvrajSingh.chatApp.utils;

import java.util.ResourceBundle;

public class ConfigReader {
    private ConfigReader() {} // Private constructor to prevent instantiation

    private static ResourceBundle rb = ResourceBundle.getBundle("config");

    public static String getValue(String key) {
        return rb.getString(key);
    }
}
