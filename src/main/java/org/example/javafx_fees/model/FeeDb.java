/**
 * Winter 2025 Java Programming for OOSD
 * CMPP 264 Assignment 2
 * Carlos Hernandez-Zelaya
 * Feb 2025
 */


package org.example.javafx_fees.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafx_fees.utils.DbConfig;

import java.sql.*;

public class FeeDb {

    //? Db Connection
    public static Connection getConnection() {

        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        Connection conn;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to the database" + e);
        }
        return conn;
    }


    public static ObservableList<Fee> getFees() throws SQLException {
        ObservableList<Fee> fees = FXCollections.observableArrayList();
        Fee fee;
        //create connection using static method
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM fees");
        while (rs.next()) {//iterates each row of result from db.
            fee = new Fee(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getString(4)
            );
            // Print the data to the console for debugging
            System.out.println("FeeId: " + rs.getString(1) +
                    ", FeeName: " + rs.getString(2) +
                    ", FeeAmt: " + rs.getDouble(3) +
                    ", FeeDesc: " + rs.getString(4));


            fees.add(fee); //add created fee to list.
        }
        return fees; // return observable list of fees.
    }

    public static int addFee(Fee fee) throws SQLException {
        Connection conn = getConnection();
        int affectedRows = 0;
        String sql = "insert into fees ( feeid, feename, feeamt,feedesc) values (?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, fee.getFeeId());
        stmt.setString(2, fee.getFeeName());
        stmt.setDouble(3, fee.getFeeAmt());
        stmt.setString(4, fee.getFeeDesc());

        affectedRows = stmt.executeUpdate();
        return affectedRows;
    }

    public static int updateFee(Fee fee) throws SQLException {
        Connection conn = getConnection();
        int affectedRows = 0;
        String sql = "update fees Set feeid = ? , feename = ? , feeamt = ? , feedesc = ? where feeid = ?" ;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, fee.getFeeId());
        stmt.setString(2, fee.getFeeName());
        stmt.setDouble(3, fee.getFeeAmt());
        stmt.setString(4, fee.getFeeDesc());
        stmt.setString(5, fee.getFeeId());
        affectedRows = stmt.executeUpdate();
        stmt.close();
        return affectedRows;
    }

    public static int deleteFees(String fees) throws SQLException {
        Connection conn = getConnection();
        int affectedRows = 0;
        String sql = "delete from fees where feeid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, fees);
        affectedRows = stmt.executeUpdate();
        stmt.close();
        return affectedRows;

    }


}//class
