package org.example;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_url = "jdbc:derby:database/forum;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    public Database() {
        createConnection();
        createFileTable();
    }

    private void createFileTable(){
        String TABLE_NAME = "FILE";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dmn = conn.getMetaData();
            ResultSet tables = dmn.getTables(null, null, TABLE_NAME, null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists");
            } else {
                String statement = "CREATE TABLE " + TABLE_NAME + "("
                + "name varchar(200) primary key, \n"
                + "path varchar(200), \n"
                + "extension varchar(200), \n"
                + "size varchar(200))";
                System.out.println(statement);
                stmt.execute(statement);
            }
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }

    private void createConnection() {
        try {
            conn = DriverManager.getConnection(DB_url);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    public boolean execAction (String qu) {
        try{
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("exception at execQuery" + ex.getLocalizedMessage());
            return false;
        }
    }

    public ResultSet execQuery (String query) {
        ResultSet resultSet;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at Execute query");
            return null;
        }
        return resultSet;
    }

}
