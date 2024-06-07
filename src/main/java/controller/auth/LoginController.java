/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import db.UserDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import model.rbac.User;

/**
 *
 * @author sonng
 */
public class LoginController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDBContext db = new UserDBContext();
        User user = db.get(username, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            String visiturl = request.getParameter("visiturl");
            if (request.getParameter("remember") != null) {
                String token = Base64.getEncoder().encodeToString(
                        Base64.getEncoder().encodeToString(user.getUsername().getBytes()).getBytes());
                Cookie auth_token = new Cookie("auth_token", token);
                auth_token.setMaxAge(3600 * 24 * 7);
                response.addCookie(auth_token);
            }

            if (visiturl != null && visiturl.trim().length() > 0) {
                response.sendRedirect(visiturl);
            } else {
                response.sendRedirect("home/feature");
            }
        } else {
            request.setAttribute("error", "invalid username or password");
            request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
