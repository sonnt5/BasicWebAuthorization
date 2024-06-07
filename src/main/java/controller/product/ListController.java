/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import db.CategoryDBContext;
import db.DBContext;
import db.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.business.Category;
import model.business.CategoryKey;
import model.business.Product;
import model.business.ProductKey;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class ListController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //THIS PAGE CONTAINS A LOTS OF STUPID CODE TO CREATE SECURITY VULNERABILITIES
        //PLEASE HACK, EAT IT
        String raw_category = request.getParameter("category");
        if (raw_category != null) {
            if (raw_category.length() > 15) {
                throw new CustomException("category is too long!");
            }
        } else {
            raw_category = "0";
        }
        
        String raw_pageindex = request.getParameter("page");
        if(raw_pageindex ==null || raw_pageindex.length()==0)
        {
            raw_pageindex = "1";
        }
        int pageindex = Integer.parseInt(raw_pageindex);
        int pagesize = Integer.parseInt(request.getServletContext().getInitParameter("pagesize"));
        
        ProductDBContext db = new ProductDBContext();
        ArrayList<Product> products = new ArrayList<>();
        int count = db.pagging(raw_category,pageindex,pagesize,products);
        int totalpage = (count%pagesize ==0)?count/pagesize:count/pagesize+1;
        
        raw_category = extractFirstNumber(raw_category);
        request.setAttribute("products", products);
        request.setAttribute("pageindex", pageindex);
        request.setAttribute("totalpage", totalpage);
        request.setAttribute("categoryid", raw_category);
        
        CategoryDBContext categoryDB = new CategoryDBContext();
        ArrayList<Category> categories = categoryDB.list();
        Category category = new Category();
        CategoryKey catkey = new CategoryKey();
        catkey.setId(0);
        category.setName("All");
        category.setKey(catkey);
        categories.add(0, category);
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("../view/product/list.jsp").forward(request, response);
    }
    
    private String extractFirstNumber(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "No number found";
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
