/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.WorkPrice;
import java.sql.*;

/**
 *
 * @author Jipsu
 */
public class WorkPriceCont {
    
    private ArrayList<WorkPrice> recentWorkPrices = new ArrayList<WorkPrice>();
    
    private WorkPrice lastUsed;
    
    public WorkPrice createWorkPrice(String workType, double price) {
        WorkPrice x = new WorkPrice(workType, price);
        recentWorkPrices.add(x);
        lastUsed = x;
        return x;
    }
    
    public void addNewWorkPrice(WorkPrice x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO tyohinnasto(tyyppi, hinta) VALUES(?, ?)");
            pStatement.setString(1, x.getWorkType());
            pStatement.setDouble(2, x.getPrice());
            pStatement.executeUpdate();
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }    	
    }
    
    public WorkPrice findWorkPriceByWorkType(String type, Connection con) throws SQLException {
        WorkPrice wP = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyyppi, hinta FROM tyohinnasto WHERE tyyppi = ?");
            pStatement.setString(1, type);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                wP = createWorkPrice(resultSet.getString(1), resultSet.getDouble(2));
            }
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return wP;
    }
    
    public WorkPrice removeWorkPrice(WorkPrice x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM tyohinnasto WHERE tyyppi = ?");
            pStatement.setString(1, x.getWorkType());
            pStatement.executeUpdate();
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
            }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return x;    
    }
}
