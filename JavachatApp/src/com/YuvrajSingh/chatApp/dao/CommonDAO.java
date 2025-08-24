package com.YuvrajSingh.chatApp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static com.YuvrajSingh.chatApp.utils.ConfigReader.getValue;

public interface CommonDAO {
    static final String URL  = getValue("CONNECTION_URL");
    static final String USER = getValue("USERID");
    static final String PASS = getValue("PASSWORD");

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(getValue("DRIVER")); // optional in JDBC 4+, harmless
        return DriverManager.getConnection(URL, USER, PASS);
    }

    
}
