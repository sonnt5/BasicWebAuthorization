/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Entity;
import model.KeyAttribute;
import util.base.CustomException;
import util.config.DBConfigPropertiesReader;

/**
 *
 * @author sonng
 * @param <T>
 * @param <K>
 */
public abstract class DBContext<T extends Entity, K extends KeyAttribute> {

    protected Connection connection;
    String user = null;
    String pass = null;
    String jdbcurl = null;

    public DBContext() {
        try {
            readDBConfig();
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(jdbcurl, user, pass);
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readDBConfig() throws CustomException, IOException {
        DBConfigPropertiesReader reader = new DBConfigPropertiesReader();
        user = reader.getUser();
        pass = reader.getPass();
        jdbcurl = reader.getJDBCUrl();
    }

    protected PreparedStatement getPreparedStatement(String sql, List<Object> params) throws SQLException {
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    stm.setInt(i + 1, (Integer) param);
                } else if (param instanceof Boolean) {
                    stm.setBoolean(i + 1, (Boolean) param);
                } else if (param instanceof Double) {
                    stm.setDouble(i + 1, (Double) param);
                } else if (param instanceof Float) {
                    stm.setFloat(i + 1, (Float) param);
                } else if (param instanceof Long) {
                    stm.setLong(i + 1, (Long) param);
                } else if (param instanceof java.sql.Date) {
                    stm.setDate(i + 1, (java.sql.Date) param);
                } else if (param instanceof java.sql.Time) {
                    stm.setTime(i + 1, (java.sql.Time) param);
                } else if (param instanceof java.sql.Timestamp) {
                    stm.setTimestamp(i + 1, (java.sql.Timestamp) param);
                } else if (param instanceof java.util.Date) {
                    stm.setTimestamp(i + 1, (java.sql.Timestamp) param);
                } else {
                    throw new SQLException("Unsupported parameter type: " + param.getClass().getName());
                }
            }
            return stm;
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public abstract ArrayList<T> list() throws CustomException;

    public abstract T get(K key) throws CustomException;

    public abstract void insert(T model) throws CustomException;

    public abstract void update(T model) throws CustomException;

    public abstract void delete(K key) throws CustomException;
}
