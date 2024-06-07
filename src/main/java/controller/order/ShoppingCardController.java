/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.order;

import db.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import model.business.Order;
import model.business.OrderKey;
import model.business.OrderLine;
import model.business.OrderLineKey;
import model.business.Product;
import model.business.ProductKey;
import model.rbac.User;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class ShoppingCardController extends HttpServlet {
   
 

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
        request.getRequestDispatcher("../view/order/cart.jsp").forward(request, response);
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
        int productid = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String param = request.getParameter("param");
        ProductDBContext db = new ProductDBContext();
        ProductKey pkey = new ProductKey();
        pkey.setId(productid);
        Product product = db.get(pkey);
        if(product ==null)
            throw new CustomException(HttpServletResponse.SC_NOT_FOUND+":"+
                    "Product does not exist!"
                    );
        
        Order order = (Order)request.getSession().getAttribute("order");
        if(order == null) //first item in order
        {
            order = new Order();
        }
        Optional<OrderLine> option = order.getLines().stream().filter(item->item.key.getProduct().getKey().getId() == 
                product.getKey().getId()).findFirst();
        OrderLine line = null;
        if(option.isPresent())
        {
           line = option.get();
        }
        else
        {
           line = new OrderLine();
           OrderLineKey key = new OrderLineKey();
           key.setProduct(product);
           key.setOrder(order);
           line.setKey(key);
           line.setPrice(product.getPrice());
           order.getLines().add(line);
        }
         if(quantity >0)
           line.setQuantity(line.getQuantity() + quantity);
         else
           order.getLines().remove(line); 
         
        request.getSession().setAttribute("order", order);
        if(param!=null)
            response.sendRedirect("../product/list"+param);
        else
            response.sendRedirect("cart");
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
