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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.business.Category;
import model.business.CategoryKey;
import model.business.Product;
import model.rbac.User;
import util.base.CustomException;
import util.base.FileHelper;

/**
 *
 * @author sonng
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024, // 1MB
        maxRequestSize = 1024 * 1024 * 2)      // 2MB
public class CreateController extends HttpServlet {
   
private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp","webp","tiff","tif","svg");
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
        CategoryDBContext categoryDB = new CategoryDBContext();
        ArrayList<Category> categories = categoryDB.list();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("../view/product/create.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "/file/img";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Get the absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIR;

        // Create the save directory if it does not exist
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        
        // Get the file part from the request
        Part part = request.getPart("file");
        String extension = FileHelper.getFileExtension(FileHelper.extractFileName(part));
        if(!ALLOWED_FILE_TYPES.stream().anyMatch(e->e.equals(extension)))
        {
            throw new CustomException("File type is not accepted! only files in list are accepted "+ ALLOWED_FILE_TYPES);
        }
                
        
        String fileName = FileHelper.generateRandomFileName() + "."
                + extension;
       
        
        // Get the name from the request
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        boolean active = (request.getParameter("active")!=null);
        int categoryid = Integer.parseInt(request.getParameter("categoryid"));
        User user = (User)request.getSession().getAttribute("user");
        
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCreator(user);
        product.setActive(active);
        product.setImage(fileName);
        
        Category category = new Category();
        CategoryKey ck = new CategoryKey();
        ck.setId(categoryid);
        category.setKey(ck);
        product.setCategory(category);
        
        
        ProductDBContext db = new ProductDBContext();
        db.insert(product);
        part.write(uploadFilePath + File.separator + fileName);
        
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
