/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.business.Order;
import model.business.OrderKey;
import model.business.OrderLine;
import model.business.OrderLineKey;
import model.business.Product;
import model.business.ProductKey;
import model.rbac.User;
import model.rbac.UserKey;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class OrderDBContext extends DBContext<Order, OrderKey> {

    public ArrayList<Order> list(int customerid) throws CustomException {
        ArrayList<Order> orders = new ArrayList<>();
        PreparedStatement stm = null;
        String sql = "SELECT \n"
                + "	o.orderid,\n"
                + "	o.orderdate,\n"
                + "	o.status,\n"
                + "	o.processdate,\n"
                + "	l.quantity,\n"
                + "	l.price,\n"
                + "	c.uid as customer,\n"
                + "	c.displayname as customerdisplayname,\n"
                + "	s.uid as sale,\n"
                + "	s.displayname as saledisplayname,\n"
                + "	p.productid,\n"
                + "	p.productname\n"
                + "FROM orders o INNER JOIN order_lines l ON o.orderid = l.orderid\n"
                + "					INNER JOIN products p ON l.productid = p.productid\n"
                + "					INNER JOIN users c ON c.uid = o.customer\n"
                + "					LEFT JOIN users s ON s.uid = o.sale\n"
                + "WHERE c.uid = ? ORDER BY o.orderid, p.productid DESC";
        try {
            stm = getPreparedStatement(sql, List.of(customerid));
            ResultSet rs = stm.executeQuery();
            Order c_order = new Order();
            OrderKey c_okey = new OrderKey();
            c_okey.setId(0);
            c_order.setKey(c_okey);
            while (rs.next()) {
                int orderid = rs.getInt("orderid");
                if (orderid != c_order.getKey().getId()) {
                    c_order = new Order();
                    c_okey = new OrderKey();
                    c_okey.setId(orderid);
                    c_order.setKey(c_okey);
                    c_order.setStatus(rs.getInt("status"));
                    c_order.setOrderdate(rs.getTimestamp("orderdate"));

                    User customer = new User();
                    UserKey customerKey = new UserKey();
                    customerKey.setId(rs.getInt("customer"));
                    customer.setKey(customerKey);
                    customer.setDisplayname(rs.getString("customerdisplayname"));
                    c_order.setCustomer(customer);

                    int saleid = rs.getInt("sale");
                    if (saleid != 0) {
                        User sale = new User();
                        UserKey saleKey = new UserKey();
                        saleKey.setId(saleid);
                        sale.setKey(saleKey);
                        sale.setDisplayname(rs.getString("saledisplayname"));
                        c_order.setSale(sale);
                        c_order.setProcessdate(rs.getTimestamp("processdate"));
                    }
                    orders.add(c_order);
                }

                OrderLine line = new OrderLine();
                OrderLineKey linekey = new OrderLineKey();
                line.setKey(linekey);
                linekey.setOrder(c_order);
                Product product = new Product();
                ProductKey pkey = new ProductKey(rs.getInt("productid"));
                product.setKey(pkey);
                product.setName(rs.getString("productname"));
                linekey.setProduct(product);
                line.setQuantity(rs.getInt("quantity"));
                line.setPrice(rs.getFloat("price"));
                c_order.getLines().add(line);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return orders;
    }

    @Override
    public ArrayList<Order> list() throws CustomException {
        ArrayList<Order> orders = new ArrayList<>();
        PreparedStatement stm = null;
        String sql = "SELECT \n"
                + "	o.orderid,\n"
                + "	o.orderdate,\n"
                + "	o.status,\n"
                + "	o.processdate,\n"
                + "	l.quantity,\n"
                + "	l.price,\n"
                + "	c.uid as customer,\n"
                + "	c.displayname as customerdisplayname,\n"
                + "	s.uid as sale,\n"
                + "	s.displayname as saledisplayname,\n"
                + "	p.productid,\n"
                + "	p.productname\n"
                + "FROM orders o INNER JOIN order_lines l ON o.orderid = l.orderid\n"
                + "					INNER JOIN products p ON l.productid = p.productid\n"
                + "					INNER JOIN users c ON c.uid = o.customer\n"
                + "					LEFT JOIN users s ON s.uid = o.sale\n"
                + "ORDER BY o.orderid, p.productid DESC";
        try {
            stm = getPreparedStatement(sql, List.of());
            ResultSet rs = stm.executeQuery();
            Order c_order = new Order();
            OrderKey c_okey = new OrderKey();
            c_okey.setId(0);
            c_order.setKey(c_okey);
            while (rs.next()) {
                int orderid = rs.getInt("orderid");
                if (orderid != c_order.getKey().getId()) {
                    c_order = new Order();
                    c_okey = new OrderKey();
                    c_okey.setId(orderid);
                    c_order.setKey(c_okey);
                    c_order.setStatus(rs.getInt("status"));
                    c_order.setOrderdate(rs.getTimestamp("orderdate"));

                    User customer = new User();
                    UserKey customerKey = new UserKey();
                    customerKey.setId(rs.getInt("customer"));
                    customer.setKey(customerKey);
                    customer.setDisplayname(rs.getString("customerdisplayname"));
                    c_order.setCustomer(customer);

                    int saleid = rs.getInt("sale");
                    if (saleid != 0) {
                        User sale = new User();
                        UserKey saleKey = new UserKey();
                        saleKey.setId(saleid);
                        sale.setKey(saleKey);
                        sale.setDisplayname(rs.getString("saledisplayname"));
                        c_order.setSale(sale);
                        c_order.setProcessdate(rs.getTimestamp("processdate"));
                    }
                    orders.add(c_order);
                }

                OrderLine line = new OrderLine();
                OrderLineKey linekey = new OrderLineKey();
                line.setKey(linekey);
                linekey.setOrder(c_order);
                Product product = new Product();
                ProductKey pkey = new ProductKey(rs.getInt("productid"));
                product.setKey(pkey);
                product.setName(rs.getString("productname"));
                linekey.setProduct(product);
                line.setQuantity(rs.getInt("quantity"));
                line.setPrice(rs.getFloat("price"));
                c_order.getLines().add(line);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return orders;
    }

    @Override
    public Order get(OrderKey key) throws CustomException {
        Order order = null;
        PreparedStatement stm = null;
        String sql = "SELECT \n"
                + "	o.orderid,\n"
                + "	o.orderdate,\n"
                + "	o.status,\n"
                + "	o.processdate,\n"
                + "	l.quantity,\n"
                + "	l.price,\n"
                + "	c.uid as customer,\n"
                + "	c.displayname as customerdisplayname,\n"
                + "	s.uid as sale,\n"
                + "	s.displayname as saledisplayname,\n"
                + "	p.productid,\n"
                + "	p.productname\n"
                + "FROM orders o INNER JOIN order_lines l ON o.orderid = l.orderid\n"
                + "					INNER JOIN products p ON l.productid = p.productid\n"
                + "					INNER JOIN users c ON c.uid = o.customer\n"
                + "					LEFT JOIN users s ON s.uid = o.sale\n"
                + "WHERE o.orderid = ?";
        try {
            stm = getPreparedStatement(sql, List.of(key.getId()));
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                if (order == null) {
                    order = new Order();
                    order.setKey(key);
                    order.setStatus(rs.getInt("status"));
                    order.setOrderdate(rs.getTimestamp("orderdate"));

                    User customer = new User();
                    UserKey customerKey = new UserKey();
                    customerKey.setId(rs.getInt("customer"));
                    customer.setKey(customerKey);
                    customer.setDisplayname(rs.getString("customerdisplayname"));
                    order.setCustomer(customer);

                    int saleid = rs.getInt("sale");
                    if (saleid != 0) {
                        User sale = new User();
                        UserKey saleKey = new UserKey();
                        saleKey.setId(saleid);
                        sale.setKey(saleKey);
                        sale.setDisplayname(rs.getString("saledisplayname"));
                        order.setSale(sale);
                        order.setProcessdate(rs.getTimestamp("processdate"));
                    }
                }

                OrderLine line = new OrderLine();
                OrderLineKey linekey = new OrderLineKey();
                line.setKey(linekey);
                linekey.setOrder(order);
                Product product = new Product();
                ProductKey pkey = new ProductKey(rs.getInt("productid"));
                product.setKey(pkey);
                product.setName(rs.getString("productname"));
                linekey.setProduct(product);
                line.setQuantity(rs.getInt("quantity"));
                line.setPrice(rs.getFloat("price"));
                order.getLines().add(line);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return order;
    }

    @Override
    public void insert(Order model) throws CustomException {
        PreparedStatement stm_insert_order = null;
        PreparedStatement stm_get_orderid = null;
        ArrayList<PreparedStatement> stm_insert_orderlines = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            String sql_insert_order = "INSERT INTO [orders]\n"
                    + "           ([orderdate]\n"
                    + "           ,[customer]\n"
                    + "           ,[status]\n"
                    + "          )\n"
                    + "     VALUES\n"
                    + "           (GETDATE()\n"
                    + "           ,?\n"
                    + "           ,0)";
            String sql_get_orderid = "SELECT @@IDENTITY as orderid";
            String sql_insert_line = "INSERT INTO [order_lines]\n"
                    + "           ([orderid]\n"
                    + "           ,[productid]\n"
                    + "           ,[quantity]\n"
                    + "           ,[price])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            stm_insert_order = getPreparedStatement(sql_insert_order,
                    List.of(model.getCustomer().getKey().getId()));
            stm_insert_order.executeUpdate();

            stm_get_orderid = getPreparedStatement(sql_get_orderid, List.of());
            ResultSet rs = stm_get_orderid.executeQuery();
            if (rs.next()) {
                OrderKey key = new OrderKey();
                key.setId(rs.getInt("orderid"));
                model.setKey(key);
            }

            for (OrderLine line : model.getLines()) {
                line.getKey().setOrder(model);
                PreparedStatement stm_insert_line = getPreparedStatement(sql_insert_line,
                        List.of(line.getKey().getOrder().getKey().getId(),
                                line.getKey().getProduct().getKey().getId(),
                                line.getQuantity(),
                                line.getPrice())
                );
                stm_insert_line.executeUpdate();
                stm_insert_orderlines.add(stm_insert_line);
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                connection.setAutoCommit(true);
                stm_insert_order.close();
                stm_get_orderid.close();
                for (PreparedStatement stm_insert_orderline : stm_insert_orderlines) {
                    stm_insert_orderline.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void update(Order model) throws CustomException {
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE [orders]\n"
                    + "   SET \n"
                    + "       [status] = ?\n"
                    + "      ,[sale] = ?\n"
                    + "      ,[processdate] = GETDATE()\n"
                    + " WHERE [orderid] = ?";
            stm = getPreparedStatement(sql, List.of(model.getStatus()
                    ,model.getSale().getKey().getId(),model.getKey().getId()));
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(OrderKey key) throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
