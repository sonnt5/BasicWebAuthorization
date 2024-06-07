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
import model.rbac.Feature;
import model.rbac.FeatureKey;
import model.rbac.Role;
import model.rbac.RoleKey;
import model.rbac.User;
import model.rbac.UserKey;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class UserDBContext extends DBContext<User, UserKey> {

    public User get(String username, String password) throws CustomException {
        PreparedStatement stm = null;
        User user = null;
        try {
            String sql = "SELECT u.uid,u.username,u.displayname\n"
                    + "		,r.roleid,r.rolename\n"
                    + "		,f.featureid,f.feature_name,f.access_url,f.httpmethod,f.params\n"
                    + "FROM users u LEFT JOIN matrix_users_roles mur ON u.uid = mur.uid\n"
                    + "					  LEFT JOIN roles r ON r.roleid = mur.roleid AND r.active = 1\n"
                    + "					  LEFT JOIN matrix_roles_features mrf ON mrf.roleid = r.roleid\n"
                    + "					  LEFT JOIN features f ON f.featureid = mrf.featureid AND f.active = 1\n"
                    + "WHERE username = ? AND password = ? ORDER BY u.uid,r.roleid,f.featureid ASC";
            List<Object> params = List.of(username, password);
            stm = getPreparedStatement(sql, params);
            ResultSet rs = stm.executeQuery();
            int c_roleid = -1;
            Role r = null;
            int c_featureid = -1;
            Feature f = null;
            while (rs.next()) {
                if (user == null) {
                    user = new User();
                    user.setKey(new UserKey(rs.getInt("uid")));
                    user.setUsername(username);
                    user.setDisplayname(rs.getString("displayname"));
                }
                int roleid = rs.getInt("roleid");
                int featureid = rs.getInt("featureid");
                if (roleid != c_roleid && roleid!=0) {
                    r = new Role();
                    RoleKey rk = new RoleKey();
                    rk.setId(roleid);
                    r.setKey(rk);
                    r.setRolename(rs.getString("rolename"));
                    user.getRoles().add(r);
                    c_roleid = roleid;
                }
                if (featureid != c_featureid && featureid !=0 && roleid!=0  ) {
                    f = new Feature();
                    FeatureKey fk = new FeatureKey();
                    fk.setId(featureid);
                    f.setKey(fk);
                    f.setName(rs.getString("feature_name"));
                    f.setUrl(rs.getString("access_url"));
                    f.setHtppmethod(rs.getString("httpmethod"));
                    f.setParam(rs.getString("params"));
                    r.getFeatures().add(f);
                    c_featureid = featureid;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                //throw new CustomException(ex.getErrorCode()+":"+Arrays.toString(ex.getStackTrace()));
            }
        }
        return user;
    }

    public User get(String username) throws CustomException {
        PreparedStatement stm = null;
        User user = null;
        try {
            String sql = "SELECT u.uid,u.username,u.displayname\n"
                    + "		,r.roleid,r.rolename\n"
                    + "		,f.featureid,f.feature_name,f.access_url,f.httpmethod,f.params\n"
                    + "FROM users u LEFT JOIN matrix_users_roles mur ON u.uid = mur.uid\n"
                    + "					  LEFT JOIN roles r ON r.roleid = mur.roleid AND r.active = 1\n"
                    + "					  LEFT JOIN matrix_roles_features mrf ON mrf.roleid = r.roleid\n"
                    + "					  LEFT JOIN features f ON f.featureid = mrf.featureid AND f.active = 1\n"
                    + "WHERE username = ? ORDER BY u.uid,r.roleid,f.featureid ASC";
            List<Object> params = List.of(username);
            stm = getPreparedStatement(sql, params);
            ResultSet rs = stm.executeQuery();
            int c_roleid = -1;
            Role r = null;
            int c_featureid = -1;
            Feature f = null;
            while (rs.next()) {
                if (user == null) {
                    user = new User();
                    user.setKey(new UserKey(rs.getInt("uid")));
                    user.setUsername(username);
                    user.setDisplayname(rs.getString("displayname"));
                }
                int roleid = rs.getInt("roleid");
                int featureid = rs.getInt("featureid");
                if (roleid != c_roleid && roleid!=0) {
                    r = new Role();
                    RoleKey rk = new RoleKey();
                    rk.setId(roleid);
                    r.setKey(rk);
                    r.setRolename(rs.getString("rolename"));
                    user.getRoles().add(r);
                    c_roleid = roleid;
                }
                if (featureid != c_featureid && featureid !=0 && roleid!=0  ) {
                    f = new Feature();
                    FeatureKey fk = new FeatureKey();
                    fk.setId(featureid);
                    f.setKey(fk);
                    f.setName(rs.getString("feature_name"));
                    f.setUrl(rs.getString("access_url"));
                    f.setHtppmethod(rs.getString("httpmethod"));
                    f.setParam(rs.getString("params"));
                    r.getFeatures().add(f);
                    c_featureid = featureid;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                //throw new CustomException(ex.getErrorCode()+":"+Arrays.toString(ex.getStackTrace()));
            }
        }
        return user;
    }
    
    @Override
    public ArrayList<User> list() throws CustomException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String sql = "SELECT [uid]\n"
                    + "      ,[username]\n"
                    + "      ,[password]\n"
                    + "      ,[displayname]\n"
                    + "  FROM [users] ";
            stm = getPreparedStatement(sql, List.of());
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                User u = new User();
                UserKey key = new UserKey();
                u.setKey(key);
                key.setId(rs.getInt("uid"));
                u.setDisplayname(rs.getString("displayname"));
                u.setUsername(rs.getString("username"));
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                //throw new CustomException(ex.getErrorCode()+":"+Arrays.toString(ex.getStackTrace()));
            }
        }
        return users;
    }

    @Override
    public User get(UserKey key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(UserKey key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
