/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.business.Category;
import model.business.CategoryKey;
import model.business.Product;
import model.business.ProductKey;
import model.rbac.User;
import model.rbac.UserKey;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class ProductDBContext extends DBContext<Product, ProductKey> {

    public int pagging(String categoryid, int pageindex, int pagesize, ArrayList<Product> products) throws CustomException {
        PreparedStatement stm = null;
        PreparedStatement stm_count = null;
        int count = -1;
        try {
            connection.setAutoCommit(false);
            String condition = categoryid.equals("0") ? "" : "p.categoryid = " + categoryid + " AND";
            String sql = "SELECT  p.[productid]\n"
                    + "      ,p.[productname]\n"
                    + "      ,p.[productprice]\n"
                    + "      ,p.[image_name]\n"
                    + "      ,p.[creator]\n"
                    + "      ,p.[create_time]\n"
                    + "      ,p.[updater]\n"
                    + "      ,p.[update_time]\n"
                    + "      ,p.[active]\n"
                    + "      ,c.[categoryid]\n"
                    + "	  ,c.[categoryname]\n"
                    + "  FROM [products] p INNER JOIN [categories] c ON c.categoryid = p.categoryid\n"
                    + "  WHERE "
                    + condition
                    + " active = 1 "
                    + " ORDER BY p.productid ASC "
                    + " OFFSET (" + pageindex + "-1)*" + pagesize + " ROWS "
                    + " FETCH NEXT " + pagesize + " ROWS ONLY";
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                CategoryKey key = new CategoryKey();
                key.setId(rs.getInt("categoryid"));
                category.setKey(key);
                category.setName(rs.getString("categoryname"));

                Product product = new Product();
                ProductKey pkey = new ProductKey();
                pkey.setId(rs.getInt("productid"));
                product.setKey(pkey);
                product.setCategory(category);

                product.setName(rs.getString("productname"));
                product.setImage(rs.getString("image_name"));
                product.setActive(rs.getBoolean("active"));
                product.setPrice(rs.getFloat("productprice"));
                products.add(product);
            }
            String sql_count = "SELECT  COUNT(*) as total\n"
                    + "  FROM [products] p INNER JOIN [categories] c ON c.categoryid = p.categoryid\n"
                    + "  WHERE " + condition + " active = 1";
            stm_count = getPreparedStatement(sql_count, List.of());
            ResultSet rs_count = stm_count.executeQuery();
            if (rs_count.next()) {
                count = rs_count.getInt("total");
            }
            connection.commit();

        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace()+","+
                    ex.getSQLState().toString()
                    );
        } finally {
            try {
                connection.setAutoCommit(true);
                stm.close();
                stm_count.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }

    @Override
    public ArrayList<Product> list() throws CustomException {
        ArrayList<Product> products = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String sql = "SELECT  p.[productid]\n"
                    + "      ,p.[productname]\n"
                    + "      ,p.[productprice]\n"
                    + "      ,p.[image_name]\n"
                    + "      ,p.[creator]\n"
                    + "      ,p.[create_time]\n"
                    + "      ,p.[updater]\n"
                    + "      ,p.[update_time]\n"
                    + "      ,p.[active]\n"
                    + "      ,c.[categoryid]\n"
                    + "	  ,c.[categoryname]\n"
                    + "  FROM [products] p INNER JOIN [categories] c ON c.categoryid = p.categoryid\n";
                  
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                CategoryKey key = new CategoryKey();
                key.setId(rs.getInt("categoryid"));
                category.setKey(key);
                category.setName(rs.getString("categoryname"));

                Product product = new Product();
                ProductKey pkey = new ProductKey();
                pkey.setId(rs.getInt("productid"));
                product.setKey(pkey);
                product.setCategory(category);

                product.setName(rs.getString("productname"));
                product.setImage(rs.getString("image_name"));
                product.setActive(rs.getBoolean("active"));
                product.setPrice(rs.getFloat("productprice"));
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return products;
    }

    @Override
    public Product get(ProductKey pkey) throws CustomException {
        PreparedStatement stm = null;
        Product product = null;
        try {
            String sql = "SELECT p.[productid]\n"
                    + "      ,p.[productname]\n"
                    + "      ,p.[productprice]\n"
                    + "      ,p.[image_name]\n"
                    + "      ,p.[creator]\n"
                    + "	  ,cr.displayname as [creatordisplayname]\n"
                    + "      ,p.[create_time]\n"
                    + "      ,p.[updater]\n"
                    + "	  ,ud.displayname as [updaterdisplayname]\n"
                    + "      ,p.[update_time]\n"
                    + "      ,p.[active]\n"
                    + "      ,c.[categoryname]\n"
                    + "      ,p.[categoryid]\n"
                    + "  FROM [products] p INNER JOIN categories c ON p.categoryid = c.categoryid\n"
                    + "					INNER JOIN users cr ON cr.uid = p.creator\n"
                    + "					LEFT JOIN users ud ON ud.uid = p.updater\n"
                    + "	WHERE p.[productid] = ?";
            stm = getPreparedStatement(sql, List.of(pkey.getId()));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                CategoryKey key = new CategoryKey();
                key.setId(rs.getInt("categoryid"));
                category.setKey(key);
                category.setName(rs.getString("categoryname"));

                product = new Product();
                product.setKey(pkey);
                product.setCategory(category);

                product.setName(rs.getString("productname"));
                product.setImage(rs.getString("image_name"));
                product.setActive(rs.getBoolean("active"));
                product.setPrice(rs.getFloat("productprice"));

                User creator = new User();
                UserKey creatorkey = new UserKey();
                creatorkey.setId(rs.getInt("creator"));
                creator.setKey(creatorkey);
                creator.setDisplayname(rs.getString("creatordisplayname"));
                product.setCreate_time(rs.getTimestamp("create_time"));
                product.setCreator(creator);

                User updater = new User();
                UserKey updaterkey = new UserKey();
                updaterkey.setId(rs.getInt("updater"));
                updater.setKey(updaterkey);
                updater.setDisplayname(rs.getString("updaterdisplayname"));
                product.setUpdate_time(rs.getTimestamp("update_time"));
                product.setUpdater(updater);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return product;
    }

    @Override
    public void insert(Product model) throws CustomException {
        PreparedStatement stm = null;
        PreparedStatement stm_getid = null;
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [products]\n"
                    + "           ([productname]\n"
                    + "           ,[productprice]\n"
                    + "           ,[image_name]\n"
                    + "           ,[creator]\n"
                    + "           ,[create_time]\n"
                    + "           ,[active]\n"
                    + "           ,[categoryid])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,GETDATE()\n"
                    + "           ,?\n"
                    + "           ,?)";
            stm = getPreparedStatement(sql, List.of(
                    model.getName(),
                    model.getPrice(),
                    model.getImage(),
                    model.getCreator().getKey().getId(),
                    model.isActive(),
                    model.getCategory().getKey().getId()
            ));
            stm.executeUpdate();

            String sql_getid = "SELECT @@IDENTITY as productid";
            stm_getid = getPreparedStatement(sql_getid, List.of());
            ResultSet rs = stm_getid.executeQuery();
            if (rs.next()) {
                ProductKey key = new ProductKey();
                key.setId(rs.getInt("productid"));
                model.setKey(key);
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                connection.setAutoCommit(true);
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Product model) throws CustomException {
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE [products]\n"
                    + "   SET [productname] = ?\n"
                    + "      ,[productprice] = ?\n"
                    + "      ,[updater] = ?\n"
                    + "      ,[update_time] = GETDATE()\n"
                    + "      ,[active] = ?\n"
                    + "      ,[categoryid] = ?\n"
                    + " WHERE [productid] = ?";
            stm = getPreparedStatement(sql, List.of(
                    model.getName(),
                    model.getPrice(),
                    model.getUpdater().getKey().getId(),
                    model.isActive(),
                    model.getCategory().getKey().getId(),
                    model.getKey().getId()
            ));
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(ProductKey key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
