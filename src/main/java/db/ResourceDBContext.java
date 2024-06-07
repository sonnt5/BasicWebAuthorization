/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.KeyAttribute;
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
public class ResourceDBContext extends DBContext<Resource, ResourceKey> {

    @Override
    public ArrayList<Resource> list() throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<Resource> list(int uid) throws CustomException
    {
        PreparedStatement stm = null;
        ArrayList<Resource> resources = new ArrayList<>();
        try {
            String sql = "SELECT r.[resourceid]\n"
                    + "                    ,r.[resourcename]\n"
                    + "                    ,r.[create_time]\n"
                    + "                    ,c.uid as [creatorid]\n"
                    + "                    ,c.displayname as [creatordisplayname]\n"
                    + "                    ,u.uid as [updaterid]\n"
                    + "                    ,u.displayname as [updaterdisplayname]\n"
                    + "                    ,r.[update_time]\n"
                    + "                    ,r.[file_url]\n"
                    + "					,us.uid as [roleuid],us.displayname as [roledisplayname]\n"
                    + "					,ur.action\n"
                    + "                    FROM [resources] r INNER JOIN users c ON c.uid = r.creator\n"
                    + "                    LEFT JOIN users u ON u.uid = r.updater\n"
                    + "					LEFT JOIN matrix_users_resources ur ON ur.resourceid = r.resourceid\n"
                    + "					LEFT JOIN users us ON us.uid = ur.userid\n"
                    + "					WHERE us.[uid] = ?\n"
                    + "					ORDER BY r.resourceid ASC, us.displayname";
            stm = getPreparedStatement(sql, List.of(uid));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
               
                    Resource resource = new Resource();
                    User creator = new User();
                    UserKey creatorKey = new UserKey();
                    creatorKey.setId(rs.getInt("creatorid"));
                    creator.setKey(creatorKey);
                    creator.setDisplayname(rs.getString("creatordisplayname"));

                    User updater = new User();
                    UserKey updaterKey = new UserKey();
                    updaterKey.setId(rs.getInt("updaterid"));
                    updater.setKey(updaterKey);
                    updater.setDisplayname(rs.getString("updaterdisplayname"));

                    ResourceKey resourcekey = new ResourceKey();
                    resourcekey.setId(rs.getInt("resourceid"));
                    resource.setKey(resourcekey);
                    resource.setName(rs.getString("resourcename"));
                    resource.setFile_url(rs.getString("file_url"));
                    resource.setCreator(creator);
                    resource.setUpdater(updater);
                    resource.setCreate_time(rs.getTimestamp("create_time"));
                    resource.setUpdate_time(rs.getTimestamp("update_time"));
                
                UserResource ur = new UserResource();
                UserResourceKey ur_key = new UserResourceKey();
                ur_key.setResource(resource);
                User roleuser = new User();
                UserKey roleuserkey = new UserKey();
                roleuserkey.setId(rs.getInt("roleuid"));
                roleuser.setKey(roleuserkey);
                roleuser.setDisplayname(rs.getString("roledisplayname"));
                ur_key.setUser(roleuser);
                ur.setKey(ur_key);
                String action = rs.getString("action");
                ur.setCanEdit(action.contains("E"));
                ur.setCanView(action.contains("V"));
                resource.getUsers().add(ur);
                resources.add(resource);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode()+":"+ ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resources;
    }

    @Override
    public Resource get(ResourceKey key) throws CustomException {
        PreparedStatement stm = null;
        Resource resource = null;
        try {
            String sql = "SELECT r.[resourceid]\n"
                    + "                    ,r.[resourcename]\n"
                    + "                    ,r.[create_time]\n"
                    + "                    ,c.uid as [creatorid]\n"
                    + "                    ,c.displayname as [creatordisplayname]\n"
                    + "                    ,u.uid as [updaterid]\n"
                    + "                    ,u.displayname as [updaterdisplayname]\n"
                    + "                    ,r.[update_time]\n"
                    + "                    ,r.[file_url]\n"
                    + "					,us.uid as [roleuid],us.displayname as [roledisplayname]\n"
                    + "					,ur.action\n"
                    + "                    FROM [resources] r INNER JOIN users c ON c.uid = r.creator\n"
                    + "                    LEFT JOIN users u ON u.uid = r.updater\n"
                    + "					LEFT JOIN matrix_users_resources ur ON ur.resourceid = r.resourceid\n"
                    + "					LEFT JOIN users us ON us.uid = ur.userid\n"
                    + "					WHERE r.[resourceid] = ?\n"
                    + "					ORDER BY r.resourceid ASC, us.displayname";
            stm = getPreparedStatement(sql, List.of(key.getId()));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (resource == null) {

                    User creator = new User();
                    UserKey creatorKey = new UserKey();
                    creatorKey.setId(rs.getInt("creatorid"));
                    creator.setKey(creatorKey);
                    creator.setDisplayname(rs.getString("creatordisplayname"));

                    User updater = new User();
                    UserKey updaterKey = new UserKey();
                    updaterKey.setId(rs.getInt("updaterid"));
                    updater.setKey(updaterKey);
                    updater.setDisplayname(rs.getString("updaterdisplayname"));

                    resource = new Resource();
                    ResourceKey resourcekey = new ResourceKey();
                    resourcekey.setId(rs.getInt("resourceid"));
                    resource.setKey(key);
                    resource.setName(rs.getString("resourcename"));
                    resource.setFile_url(rs.getString("file_url"));
                    resource.setCreator(creator);
                    resource.setUpdater(updater);
                    resource.setCreate_time(rs.getTimestamp("create_time"));
                    resource.setUpdate_time(rs.getTimestamp("update_time"));
                }
                UserResource ur = new UserResource();
                UserResourceKey ur_key = new UserResourceKey();
                ur_key.setResource(resource);
                User roleuser = new User();
                UserKey roleuserkey = new UserKey();
                roleuserkey.setId(rs.getInt("roleuid"));
                roleuser.setKey(roleuserkey);
                roleuser.setDisplayname(rs.getString("roledisplayname"));
                ur_key.setUser(roleuser);
                ur.setKey(ur_key);
                String action = rs.getString("action");
                ur.setCanEdit(action.contains("E"));
                ur.setCanView(action.contains("V"));
                resource.getUsers().add(ur);
            }
        } catch (SQLException ex) {

        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resource;
    }

    @Override
    public void insert(Resource model) throws CustomException {
        String sql_insert = "INSERT INTO [resources]\n"
                + "           ([resourcename]\n"
                + "           ,[creator]\n"
                + "           ,[create_time]\n"
                + "           ,[file_url])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,GETDATE()\n"
                + "           ,?)";
        String sql_queryid = "SELECT @@IDENTITY as resourceid";
        PreparedStatement stm_insert = null;
        PreparedStatement stm_queryid = null;
        ArrayList<PreparedStatement> stm_insert_matrixes = new ArrayList<>();
        String sql_inser_matrix = "INSERT INTO [matrix_users_resources]\n"
                + "           ([resourceid]\n"
                + "           ,[userid]\n"
                + "           ,[action])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        try {
            connection.setAutoCommit(false);
            List<Object> params = List.of(model.getName(), model.getCreator().key.getId(), model.getFile_url());
            stm_insert = getPreparedStatement(sql_insert, params);
            stm_insert.executeUpdate();

            stm_queryid = getPreparedStatement(sql_queryid, List.of());
            ResultSet rs = stm_queryid.executeQuery();
            if (rs.next()) {
                ResourceKey key = new ResourceKey();
                key.setId(rs.getInt("resourceid"));
                model.setKey(key);
            }

            for (UserResource ur : model.getUsers()) {
                String action = ur.isCanView() ? "V" : "";
                action += ur.isCanEdit() ? "E" : "";
                PreparedStatement stm_insert_matrix = getPreparedStatement(sql_inser_matrix,
                        List.of(model.getKey().getId(),
                                ur.getKey().getUser().getKey().getId(),
                                action
                        ));
                stm_insert_matrixes.add(stm_insert_matrix);
                stm_insert_matrix.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex1);
                //throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
            }
            Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                connection.setAutoCommit(true);
                stm_insert.close();
                stm_queryid.close();
                for (PreparedStatement stm_insert_matrixe : stm_insert_matrixes) {
                    stm_insert_matrixe.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex);
                //throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
            }
        }
    }

    @Override
    public void update(Resource model) throws CustomException {
        ArrayList<PreparedStatement> stm_insert_matrixes = new ArrayList<>();
        PreparedStatement stm_update_r = null;
        PreparedStatement stm_remove_urs = null;
        try {
            connection.setAutoCommit(false);
            String sql_update_resource = "UPDATE [resources]\n"
                    + "   SET [resourcename] = ?\n"
                    + "      ,[updater] = ?\n"
                    + "      ,[update_time] = GETDATE()\n"
                    + "      WHERE [resourceid] = ?";

            stm_update_r = getPreparedStatement(sql_update_resource, List.of(model.getName(),
                     model.getUpdater().getKey().getId(), model.getKey().getId()));
            stm_update_r.executeUpdate();

            String sql_remove_urs = "DELETE FROM [matrix_users_resources]\n"
                    + "WHERE resourceid = ? ";
            stm_remove_urs = getPreparedStatement(sql_remove_urs, List.of(model.getKey().getId()));
            stm_remove_urs.executeUpdate();
            
            String sql_inser_matrix = "INSERT INTO [matrix_users_resources]\n"
                + "           ([resourceid]\n"
                + "           ,[userid]\n"
                + "           ,[action])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
            for (UserResource ur : model.getUsers()) {
                String action = ur.isCanView() ? "V" : "";
                action += ur.isCanEdit() ? "E" : "";
                PreparedStatement stm_insert_matrix = getPreparedStatement(sql_inser_matrix,
                        List.of(model.getKey().getId(),
                                ur.getKey().getUser().getKey().getId(),
                                action
                        ));
                stm_insert_matrixes.add(stm_insert_matrix);
                stm_insert_matrix.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex1);
                //throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
            }
            Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                connection.setAutoCommit(true);
                stm_update_r.close();
                stm_remove_urs.close();
                for (PreparedStatement stm_insert_matrixe : stm_insert_matrixes) {
                    stm_insert_matrixe.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex);
                //throw new CustomException(ex.getErrorCode() + ":" + Arrays.toString(ex.getStackTrace()));
            }
        }
    }

    @Override
    public void delete(ResourceKey key) throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public UserResource getUserResource(User user, Resource resource) throws CustomException {
        String sql = "SELECT action FROM matrix_users_resources WHERE userid = ? AND resourceid = ?";
        PreparedStatement stm = null;
        UserResourceKey urkey = new UserResourceKey();
        urkey.setResource(resource);
        urkey.setUser(user);
        UserResource ur = new UserResource();
        ur.setKey(urkey);
        try {
            stm = getPreparedStatement(sql, List.of(user.getKey().getId(),
                    resource.getKey().getId()
            ));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String action = rs.getString("action");
                ur.setCanView(action.contains("V"));
                ur.setCanEdit(action.contains("E"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getStackTrace());
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ResourceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ur;
    }

}
