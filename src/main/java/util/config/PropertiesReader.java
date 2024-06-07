/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public abstract class PropertiesReader {
    Properties properties = null;
    String filename;
    public PropertiesReader(String filename) throws IOException
    {
        this.filename = filename;
        properties = getReader();
    }
    private Properties getReader() throws CustomException, IOException
    {
       InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
        if (input == null) {
            throw new CustomException("cannot load database configuration!");
        }
        Properties properties = new Properties();
        properties.load(input);
        return properties;
    }
}
