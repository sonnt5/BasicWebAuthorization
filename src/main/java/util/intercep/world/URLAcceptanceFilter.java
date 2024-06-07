/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package util.intercep.world;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.base.BaseXMLFilter;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class URLAcceptanceFilter extends BaseXMLFilter {
    
    private List<String> acceptedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            // Initialize acceptedUrls list
            acceptedUrls = new ArrayList<>();
            // Extract URL elements
            NodeList urlNodes = readUrlsFromXML(filterConfig, "accepted-urls");
            for (int i = 0; i < urlNodes.getLength(); i++) {
                acceptedUrls.add(urlNodes.item(i).getTextContent());
            }
        } catch (ServletException | IOException | ParserConfigurationException | DOMException | SAXException e) {
            throw new ServletException("Failed to parse config file", e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String current = httpRequest.getServletPath();
        boolean isAllowed = false;
        // Example: Log the accepted URLs
        for (String url : acceptedUrls) {
            if(url.equals(current))
            {
                isAllowed = true;
                break;
            }
        }
        //chain.doFilter(request, response);
        if(isAllowed)
        // Continue with the filter chain
            chain.doFilter(request, response);
        else
            throw new CustomException(HttpServletResponse.SC_FORBIDDEN+":"+
                    "Access Denied"
                    );
    }

    @Override
    public void destroy() {
        // Cleanup code if necessary
    }
    
}
