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
import model.KeyAttribute;
import model.accesscontrol.Resource;
import model.accesscontrol.ResourceKey;
import model.accesscontrol.UserResource;
import model.accesscontrol.UserResourceKey;
import model.business.Category;
import model.business.CategoryKey;
import model.rbac.User;
import model.rbac.UserKey;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class CategoryDBContext extends DBContext<Category, CategoryKey> {

    @Override
    public ArrayList<Category> list() throws CustomException {
        PreparedStatement stm = null;
        ArrayList<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT categoryid, categoryname FROM categories";
            stm = getPreparedStatement(sql, List.of());
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                Category category = new Category();
                CategoryKey key = new CategoryKey();
                key.setId(rs.getInt("categoryid"));
                category.setKey(key);
                category.setName(rs.getString("categoryname"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode()+":"+ ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return categories;
    }

    @Override
    public Category get(CategoryKey key) throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Category model) throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Category model) throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(CategoryKey key) throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
