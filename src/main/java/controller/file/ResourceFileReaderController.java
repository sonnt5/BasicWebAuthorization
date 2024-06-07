/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.file;

import db.ResourceDBContext;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import model.accesscontrol.Resource;
import model.accesscontrol.ResourceKey;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class ResourceFileReaderController extends FileReaderController {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("pdf","doc","xls", "docx", "txt", "jpg", "jpeg", "png", "gif", "bmp","xlsx","zip","rar","csv");
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int resourceid = Integer.parseInt(request.getParameter("id"));
        ResourceDBContext db = new ResourceDBContext();
        ResourceKey key = new ResourceKey();
        key.setId(resourceid);
        Resource resource = db.get(key);
        if(resource ==null)
            throw new CustomException("Resouce does not exist!");
        ServletContext cntx= request.getServletContext();
        String filePath = cntx.getRealPath("file/resource/" + resource.getFile_url());
        serveFile(request, response, filePath, ALLOWED_FILE_TYPES);
    } 

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
        processRequest(request, response);
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
        processRequest(request, response);
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
