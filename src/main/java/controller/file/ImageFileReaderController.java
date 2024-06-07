/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.file;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class ImageFileReaderController extends FileReaderController {
   
   private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("name");
        if (fileName == null || fileName.isEmpty()) {
            throw new CustomException(HttpServletResponse.SC_BAD_REQUEST+": File type is not allowed!");
        }
        ServletContext cntx= request.getServletContext();
        String filePath = cntx.getRealPath("file/img/" + fileName);
        serveFile(request, response, filePath, ALLOWED_IMAGE_TYPES);
    }

}
