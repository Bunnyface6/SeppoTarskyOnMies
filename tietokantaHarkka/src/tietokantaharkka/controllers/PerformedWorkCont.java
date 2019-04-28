/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.PerformedWork;
import java.sql.*;

/**
 *
 * @author Jipsu
 */
public class PerformedWorkCont {
    
    private ArrayList<PerformedWork> recentPerformedWorks = new ArrayList<PerformedWork>();
    
    private PerformedWork lastUsed;
    
    public PerformedWork createPerformedWork(String workType, int workPerformanceNmbr, int numOfHours, int discountPer) {
        PerformedWork x = new PerformedWork(workType, workPerformanceNmbr, numOfHours, discountPer);
        recentPerformedWorks.add(x);
        lastUsed = x;
        return x;
    }
    
    public void addNewPerformedWork(PerformedWork x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            if (findPerformedWorkOfWorkPerformance(x.getWorkType(), x.getWorkPerformanceNmbr(), con) == null) {
                pStatement = con.prepareStatement("INSERT INTO hinnoiteltu_tyosuoritus(tyotyyppi, tyosuoritusnumero, maara, alennusprosentti) VALUES(?, ?, ?, ?)");
                pStatement.setString(1, x.getWorkType());
                pStatement.setInt(2, x.getWorkPerformanceNmbr());
                pStatement.setInt(3, x.getNumOfHours());
                pStatement.setInt(4, x.getDiscountPer());
                pStatement.executeUpdate();
            }
            else {
                pStatement = con.prepareStatement("UPDATE hinnoiteltu_tyosuoritus SET maara = maara + ?, alennusprosentti = ? WHERE tyotyyppi = ? AND tyosuoritusnumero = ?");
                pStatement.setInt(1, x.getNumOfHours());
                pStatement.setInt(2, x.getDiscountPer());
                pStatement.setString(3, x.getWorkType());
                pStatement.setInt(4, x.getWorkPerformanceNmbr());
                pStatement.executeUpdate();
            }
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
    
    public ArrayList<PerformedWork> findPerformedWorkByWorkType(String workType, Connection con) throws SQLException {
        ArrayList<PerformedWork> pW = new ArrayList<PerformedWork>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyotyyppi, tyosuoritusnumero, maara, alennusprosentti FROM hinnoiteltu_tyosuoritus WHERE tyotyyppi = ?");
            pStatement.setString(1, workType);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                pW.add(createPerformedWork(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)));
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
        return pW;
    }
    
    public ArrayList<PerformedWork> findPerformedWorkByWorkPerformanceNmbr(int nmbr, Connection con) throws SQLException {
        ArrayList<PerformedWork> pW = new ArrayList<PerformedWork>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyotyyppi, tyosuoritusnumero, maara, alennusprosentti FROM hinnoiteltu_tyosuoritus WHERE tyosuoritusnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                pW.add(createPerformedWork(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)));
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
        return pW;
    }
    
    public PerformedWork findPerformedWorkOfWorkPerformance(String type, int wBN, Connection con) throws SQLException {
        PerformedWork pW = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyotyyppi, tyosuoritusnumero, maara, alennusprosentti FROM hinnoiteltu_tyosuoritus WHERE tyotyyppi = ? AND tyosuoritusnumero = ?");
            pStatement.setString(1, type);
            pStatement.setInt(2, wBN);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
               pW = createPerformedWork(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)); 
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
        return pW;
    }
    
    public PerformedWork removePerformedWork (PerformedWork x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM hinnoiteltu_tyosuoritus WHERE tyotyyppi = ? AND tyosuoritusnumero = ?");
            pStatement.setString(1, x.getWorkType());
            pStatement.setInt(2, x.getWorkPerformanceNmbr());
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
    
    public boolean updateDiscount(PerformedWork x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        boolean oK = false;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("UPDATE hinnoiteltu_tyosuoritus SET alennusprosentti = ? WHERE tyotyyppi = ? AND tyosuoritusnumero = ?");
            pStatement.setInt(1, x.getDiscountPer());
            pStatement.setString(2, x.getWorkType());
            pStatement.setInt(3, x.getWorkPerformanceNmbr());
            int rV = pStatement.executeUpdate();
            if (rV == 1) {
                oK = true;
            }
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
        return oK;        
    }
}