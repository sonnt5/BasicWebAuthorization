/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.config;

import java.io.IOException;

/**
 *
 * @author sonng
 */
public class DBConfigPropertiesReader extends PropertiesReader {

    public DBConfigPropertiesReader() throws IOException {
       super("config/dbconfig.properties");
    }
    public String getUser()
    {
        return properties.getProperty("db.user");
    }
    public String getPass()
    {
        return properties.getProperty("db.pass");
    }
    public String getJDBCUrl()
    {
        return properties.getProperty("db.jdbcurl");
    }
    
    
}
