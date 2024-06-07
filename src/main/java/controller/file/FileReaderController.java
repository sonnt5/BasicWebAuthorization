/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.file;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public abstract class FileReaderController extends HttpServlet {
   
    private static final int BUFFER_SIZE = 4096;

    protected void serveFile(HttpServletRequest request, HttpServletResponse response, String filePath, List<String> allowedFileTypes)
            throws IOException {
        File file = new File(filePath);

        if (!file.exists() || file.isDirectory()) {
            throw new CustomException(HttpServletResponse.SC_NOT_FOUND+": File not found!");
        }

        String fileName = file.getName();
        String fileExtension = getFileExtension(fileName);
        if (!allowedFileTypes.contains(fileExtension)) {
             throw new CustomException(HttpServletResponse.SC_BAD_REQUEST+": File type is not allowed!");
        }

        String mimeType = getServletContext().getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) file.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"", file.getName());
        response.setHeader(headerKey, headerValue);

        try (FileInputStream inStream = new FileInputStream(file);
             OutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        } else {
            return "";
        }
    }
}
