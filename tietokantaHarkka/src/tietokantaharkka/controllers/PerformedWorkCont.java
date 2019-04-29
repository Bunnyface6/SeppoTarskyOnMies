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
    
    /**
     * Luo työsuorituksen
     * @param workType
     * @param workPerformanceNmbr
     * @param numOfHours
     * @param discountPer
     * @return tehty työ
     */
    public PerformedWork createPerformedWork(String workType, int workPerformanceNmbr, int numOfHours, int discountPer) {
        PerformedWork x = new PerformedWork(workType, workPerformanceNmbr, numOfHours, discountPer);
        recentPerformedWorks.add(x);
        lastUsed = x;
        return x;
    }
    
    /**
     * lisää työsuorituksen
     * @param x
     * @param con
     * @throws SQLException
     */
    public void addNewPerformedWork(PerformedWork x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
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
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }    	
    }
    
    /**
     * etsii työsuorituksia työtyypillä
     * @param workType
     * @param con
     * @return arraylist työsuorituksista
     * @throws SQLException jos virhe etsinnässä
     */
    public ArrayList<PerformedWork> findPerformedWorkByWorkType(String workType, Connection con) throws SQLException {
        ArrayList<PerformedWork> pW = new ArrayList<PerformedWork>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyotyyppi, tyosuoritusnumero, maara, alennusprosentti FROM hinnoiteltu_tyosuoritus WHERE tyotyyppi = ?");
            pStatement.setString(1, workType);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                pW.add(createPerformedWork(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)));
            }
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
    
    /**
     * etsii työuorituksia numerolla
     * @param nmbr
     * @param con
     * @return arraylist työsuorituksista
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<PerformedWork> findPerformedWorkByWorkPerformanceNmbr(int nmbr, Connection con) throws SQLException {
        ArrayList<PerformedWork> pW = new ArrayList<PerformedWork>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyotyyppi, tyosuoritusnumero, maara, alennusprosentti FROM hinnoiteltu_tyosuoritus WHERE tyosuoritusnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                pW.add(createPerformedWork(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)));
            }
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
    
    /**
     * etsii suorituksia
     * @param type
     * @param wBN
     * @param con
     * @return työsuoritus
     * @throws SQLException jos etsinnässä virheitä
     */
    public PerformedWork findPerformedWorkOfWorkPerformance(String type, int wBN, Connection con) throws SQLException {
        PerformedWork pW = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyotyyppi, tyosuoritusnumero, maara, alennusprosentti FROM hinnoiteltu_tyosuoritus WHERE tyotyyppi = ? AND tyosuoritusnumero = ?");
            pStatement.setString(1, type);
            pStatement.setInt(2, wBN);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
               pW = createPerformedWork(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)); 
            }                   
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
    
    /**
     * poistaa yösuorituksen
     * @param x
     * @param con
     * @return poistettu työsuoritus
     * @throws SQLException jos poistossa virhe
     */
    public PerformedWork removePerformedWork (PerformedWork x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM hinnoiteltu_tyosuoritus WHERE tyotyyppi = ? AND tyosuoritusnumero = ?");
            pStatement.setString(1, x.getWorkType());
            pStatement.setInt(2, x.getWorkPerformanceNmbr());
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return x;    
    }
    
    /**
     * päivittää alennuksen
     * @param x
     * @param con
     * @return true/false
     * @throws SQLException jos päivityksessä virhe
     */
    public boolean updateDiscount(PerformedWork x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        boolean oK = false;
        try {
            pStatement = con.prepareStatement("UPDATE hinnoiteltu_tyosuoritus SET alennusprosentti = ? WHERE tyotyyppi = ? AND tyosuoritusnumero = ?");
            pStatement.setInt(1, x.getDiscountPer());
            pStatement.setString(2, x.getWorkType());
            pStatement.setInt(3, x.getWorkPerformanceNmbr());
            int rV = pStatement.executeUpdate();
            if (rV == 1) {
                oK = true;
            }
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return oK;        
    }
}