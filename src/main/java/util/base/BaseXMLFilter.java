/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package util.base;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author sonng
 */

public abstract class BaseXMLFilter implements Filter {
    public NodeList readUrlsFromXML(FilterConfig filterConfig,String filename) throws ServletException, IOException, ParserConfigurationException, SAXException
    {
        // Load the XML file
            ServletContext context = filterConfig.getServletContext();
            InputStream inputStream = context.getResourceAsStream("/WEB-INF/"+filename+".xml");

            if (inputStream == null) {
                throw new ServletException("Config file not found");
            }

            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            // Extract URL elements
            return document.getElementsByTagName("url");
    }
}
