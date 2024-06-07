/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import jakarta.enterprise.context.Destroyed;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.KeyAttribute;
import model.task.Feedback;
import model.task.FeedbackKey;
import util.base.CustomException;

/**
 *
 * @author sonng
 */
public class FeedbackDBContext extends DBContext<Feedback, FeedbackKey> {

    @Override
    public ArrayList<Feedback> list() throws CustomException {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        PreparedStatement stm = null;
        try {

            String sql = "SELECT [feedbackid]\n"
                    + "      ,[message]\n"
                    + "      ,[subject]\n"
                    + "	  ,[time]\n"
                    + "  FROM [feedbacks]\n"
                    + "  ORDER BY [time] DESC";
            stm = getPreparedStatement(sql, List.of());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feedback f = new Feedback();
                FeedbackKey key = new FeedbackKey();
                key.setId(rs.getInt("feedbackid"));
                f.setKey(key);
                f.setMessage(rs.getString("message"));
                f.setSubject(rs.getString("subject"));
                f.setTime(rs.getTimestamp("time"));
                feedbacks.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getMessage() + "." + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return feedbacks;
    }

    @Override
    public Feedback get(FeedbackKey key) throws CustomException {
        Feedback f = null;
        PreparedStatement stm = null;
        try {
            String sql = "SELECT [feedbackid]\n"
                    + "      ,[message]\n"
                    + "      ,[subject]\n"
                    + "	  ,[time]\n"
                    + "  FROM [feedbacks]\n"
                    + "  WHERE [feedbackid] = ?\n"
                    + "  ORDER BY [time] DESC";
            stm = getPreparedStatement(sql, List.of(key.getId()));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                f = new Feedback();
                f.setKey(key);
                f.setMessage(rs.getString("message"));
                f.setSubject(rs.getString("subject"));
                f.setTime(rs.getTimestamp("time"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getMessage() + "." + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return f;
    }

    @Override
    public void insert(Feedback model) throws CustomException {
        String sql = "INSERT INTO [feedbacks]\n"
                + "           ([message]\n"
                + "           ,[subject]\n"
                + "           ,[time])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,GETDATE())";
        PreparedStatement stm = null;
        try {
            stm = getPreparedStatement(sql, List.of(model.getMessage(),model.getSubject()));
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getErrorCode() + ":" + ex.getMessage() + "." + Arrays.toString(ex.getStackTrace()));
        }
        finally
        {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Feedback model) throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(FeedbackKey key) throws CustomException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
