/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package controller.auth.intercept;

import db.UserDBContext;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.DrbgParameters;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import model.rbac.Feature;
import model.rbac.Role;
import model.rbac.User;
import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.base.BaseXMLFilter;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class URLRequiredAuthenticationFilter extends BaseXMLFilter {

    private List<String> urls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            // Initialize acceptedUrls list
            urls = new ArrayList<>();
            // Extract URL elements
            NodeList urlNodes = readUrlsFromXML(filterConfig, "required-authentication-urls");
            for (int i = 0; i < urlNodes.getLength(); i++) {
                urls.add(urlNodes.item(i).getTextContent());
            }
        } catch (ServletException | IOException | ParserConfigurationException | DOMException | SAXException e) {
            throw new ServletException("Failed to parse config file", e);
        }
    }

    private void autoAuthenticate(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cooky : cookies) {
                    if (cooky.getName().equals("auth_token")) {
                        UserDBContext db = new UserDBContext();
                        String value = cooky.getValue();
                        String username = new String(Base64.getDecoder().
                                decode(new String(Base64.getDecoder().decode(value))));
                        try {
                            User user = db.get(username);
                            if (user != null) {
                                request.getSession().setAttribute("user", user);
                            } else {
                                cooky.setMaxAge(0);
                                response.addCookie(cooky);
                            }
                        } catch (CustomException ex) {
                            Logger.getLogger(URLRequiredAuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        autoAuthenticate(httpRequest, httpResponse);
        String current = httpRequest.getServletPath();
        User user = (User) httpRequest.getSession().getAttribute("user");
        boolean isRequired = false;
        // Example: Log the accepted URLs
        for (String url : urls) {
            if (url.equals(current)) {
                isRequired = true;
                break;
            }
        }
        if (isRequired && user == null) {
            String message = "you are not yet authenticated! List of required authentication:" + urls.toString();
            throw new CustomException(message);
        } else if (isRequired && user != null) {
            boolean isPassed = false;
            for (Role role : user.getRoles()) {
                for (Feature feature : role.getFeatures()) {
                    if (feature.getUrl().equals(current)) {
                        isPassed = true;
                        break;
                    }
                }
            }
            if (isPassed) {
                chain.doFilter(request, response);
            } else {
                throw new CustomException(HttpServletResponse.SC_FORBIDDEN + ":"
                        + "Access Denied"
                );
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if necessary
    }

}
