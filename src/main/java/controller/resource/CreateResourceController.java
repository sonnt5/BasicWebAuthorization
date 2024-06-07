/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.resource;

import db.ResourceDBContext;
import db.UserDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import model.accesscontrol.Resource;
import model.rbac.User;
import model.rbac.UserKey;
import model.accesscontrol.UserResource;
import model.accesscontrol.UserResourceKey;
import util.base.CustomException;
import util.base.FileHelper;

/**
 *
 * @author sonng
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024, // 1MB
        maxRequestSize = 1024 * 1024 * 2)      // 2MB
public class CreateResourceController extends HttpServlet {

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("pdf","doc","xls", "docx", "txt", "jpg", "jpeg", "png", "gif", "bmp","xlsx","zip","rar","csv");
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
        UserDBContext db = new UserDBContext();
        ArrayList<User> users = db.list();
        request.setAttribute("users", users);
        request.getRequestDispatcher("../view/resource/create.jsp").forward(request, response);
    }

    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "/file/resource";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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

        

        Resource resource = new Resource();
        resource.setName(name);
        User user = (User) request.getSession().getAttribute("user");
        resource.setCreator(user);
        resource.setFile_url(fileName);

        String[] raw_uid_views = request.getParameterValues("uid_view");
        ArrayList<String> uid_views = new ArrayList<>();
        Arrays.asList(raw_uid_views).forEach(item->uid_views.add(item));
        uid_views.add(user.getKey().getId()+"");
        
        String[] raw_uid_edits = request.getParameterValues("uid_edit");
        List<String> uid_edits = new ArrayList<>();
        Arrays.asList(raw_uid_edits).forEach(item->uid_edits.add(item));
        uid_edits.add(user.getKey().getId()+"");
        for (int i = 0; i < (uid_views.size() + uid_edits.size()); i++) {
            String uid_str = (i < uid_views.size()) ? uid_views.get(i) :
                    uid_edits.get(i-(uid_views.size()));

            int uid = Integer.parseInt(uid_str);
            Optional<UserResource> our = resource.getUsers().stream().filter(item
                    -> item.getKey().getUser().getKey().getId() == uid).findFirst();
            UserResource ur = null;
            if (our.isPresent()) {
                ur = our.get();
            } else {
                ur = new UserResource();
                UserResourceKey ur_key = new UserResourceKey();
                User u = new User();
                UserKey u_key = new UserKey();
                u_key.setId(uid);
                u.setKey(u_key);
                ur_key.setUser(u);
                ur_key.setResource(resource);
                ur.setKey(ur_key);
                resource.getUsers().add(ur);
            }
            if (i < uid_views.size()) {
                ur.setCanView(true);
            } else {
                ur.setCanEdit(true);
            }
        }
        ResourceDBContext db = new ResourceDBContext();
        db.insert(resource);
        // Write the file to the server
        part.write(uploadFilePath + File.separator + fileName);
        
        response.sendRedirect("detail?id=" + resource.getKey().getId());
    }

   

}
