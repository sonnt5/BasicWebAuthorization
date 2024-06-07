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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import model.accesscontrol.Resource;
import model.accesscontrol.ResourceKey;
import model.rbac.User;
import model.rbac.UserKey;
import model.accesscontrol.UserResource;
import model.accesscontrol.UserResourceKey;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class EditResourceController extends HttpServlet {
   
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
        int resourceid = Integer.parseInt(request.getParameter("id"));
        ResourceDBContext db = new ResourceDBContext();
        ResourceKey key = new ResourceKey();
        key.setId(resourceid);
        Resource resource = db.get(key);
        if(resource ==null)
            throw new CustomException("Resouce does not exist!");
        request.setAttribute("resource", resource);
        
        UserDBContext udb = new UserDBContext();
        ArrayList<User> users = udb.list();
        request.setAttribute("users", users);
        
        request.getRequestDispatcher("../view/resource/edit.jsp").forward(request, response);
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
        
        ResourceDBContext db = new ResourceDBContext();
        ResourceKey key_fromdb = new ResourceKey();
        key_fromdb.setId(id);
        Resource resource_fromdb = db.get(key_fromdb);
        if(resource_fromdb ==null)
            throw new CustomException("Resouce does not exist!");
        
        String name = request.getParameter("name");
        Resource resource = new Resource();
        ResourceKey key = new ResourceKey();
        key.setId(id);
        resource.setKey(key);
        resource.setName(name);
        User user = (User) request.getSession().getAttribute("user");
        resource.setUpdater(user);
        
        String[] raw_uid_views = request.getParameterValues("uid_view");
        ArrayList<String> uid_views = new ArrayList<>();
        Arrays.asList(raw_uid_views).forEach(item->uid_views.add(item));
        uid_views.add(user.getKey().getId()+"");
        if(!uid_views.contains(resource_fromdb.getCreator().getKey().getId()+""))
            uid_views.add(resource_fromdb.getCreator().getKey().getId()+"");
        
        
        String[] raw_uid_edits = request.getParameterValues("uid_edit");
        List<String> uid_edits = new ArrayList<>();
        Arrays.asList(raw_uid_edits).forEach(item->uid_edits.add(item));
        uid_edits.add(user.getKey().getId()+"");
        if(!uid_edits.contains(resource_fromdb.getCreator().getKey().getId()+""))
            uid_edits.add(resource_fromdb.getCreator().getKey().getId()+"");
        
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
        ResourceDBContext db_update = new ResourceDBContext();//get new connection
        db_update.update(resource);
        response.sendRedirect("detail?id=" + resource.getKey().getId());
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
