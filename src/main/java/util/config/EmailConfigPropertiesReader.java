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
public class EmailConfigPropertiesReader extends PropertiesReader {
    
    public EmailConfigPropertiesReader() throws IOException {
       super("config/emailconfig.properties");
    }
    
    public String getSendByEmail()
    {
        return properties.getProperty("sendby.email");
    }
    public String getSendByPass()
    {
        return properties.getProperty("sendby.password");
    }
    public String getNotifyEmail()
    {
        return properties.getProperty("notify.email");
    }
    
}
