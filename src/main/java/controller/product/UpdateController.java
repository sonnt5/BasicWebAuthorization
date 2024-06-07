/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.product;

import db.CategoryDBContext;
import db.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import model.business.Category;
import model.business.CategoryKey;
import model.business.Product;
import model.business.ProductKey;
import model.rbac.User;

/**
 *
 * @author sonng
 */
public class UpdateController extends HttpServlet {
   
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int productid = Integer.parseInt(request.getParameter("id"));
        ProductDBContext db = new ProductDBContext();
        ProductKey key = new ProductKey();
        key.setId(productid);
        Product product = db.get(key);
        request.setAttribute("product", product);
        
        CategoryDBContext categoryDB = new CategoryDBContext();
        ArrayList<Category> categories = categoryDB.list();
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("../view/product/update.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        boolean active = (request.getParameter("active")!=null);
        int categoryid = Integer.parseInt(request.getParameter("categoryid"));
        User user = (User)request.getSession().getAttribute("user");
        
        Product product = new Product();
        ProductKey key = new ProductKey();
        key.setId(id);
        product.setKey(key);
        product.setName(name);
        product.setPrice(price);
        product.setUpdater(user);
        product.setActive(active);
        Category category = new Category();
        CategoryKey ck = new CategoryKey();
        ck.setId(categoryid);
        category.setKey(ck);
        product.setCategory(category);
        
        ProductDBContext db = new ProductDBContext();
        db.update(product);
        
        response.sendRedirect("detail?id="+product.getKey().getId());
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
