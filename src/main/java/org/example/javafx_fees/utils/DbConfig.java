/**
 * Winter 2025 Java Programming for OOSD
 * CMPP 264 Assignment 2
 * Carlos Hernandez-Zelaya
 * Feb 2025
 */

package org.example.javafx_fees.utils;

import java.io.InputStream;
import java.util.Properties;

public class DbConfig {
    //* Object of class Property to access any .properties file.
    private static Properties prop = new Properties();
    // creating a static block of code that will be loaded at the onset of the application
    static {
        // reading the db.properties file
        try(InputStream in = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("db.properties file not found");
            }
            //loads the input stream from the .properties files into Properties object
            prop.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Error loading properties file",e);
        }
    }
    // read from properties object
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
