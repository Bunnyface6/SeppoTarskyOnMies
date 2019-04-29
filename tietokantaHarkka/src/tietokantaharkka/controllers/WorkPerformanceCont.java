/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.WorkPerformance;
import java.sql.*;

/**
 *
 * @author Jipsu
 */
public class WorkPerformanceCont {
    
    private ArrayList<WorkPerformance> recentWorkPerformances = new ArrayList<WorkPerformance>();
    
    private WorkPerformance lastUsed;
    
    /**
     * luo työsuorituksen
     * @param nmbr
     * @param workSiteNmbr
     * @return luotu työsuoritus
     */
    public WorkPerformance createWorkPerformance(int nmbr,int workSiteNmbr) {
        WorkPerformance x = new WorkPerformance(nmbr, workSiteNmbr);
        recentWorkPerformances.add(x);
        lastUsed = x;
        return x;
    }
    
    /**
     * lisää työsuorituksen
     * @param x
     * @param con
     * @return työsuoritusnumero
     * @throws SQLException jos virhe
     */
    public int addNewWorkPerformance(WorkPerformance x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        int wPN = 0;
        try {
            pStatement = con.prepareStatement("INSERT INTO tyosuoritus(tyokohdenumero) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, x.getWorkSiteNmbr());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            resultSet.next();
            wPN = resultSet.getInt(1);
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return wPN;        
    }
    
    /**
     * etsii työsuoritusta numerolla
     * @param nmbr
     * @param con
     * @return työsuoritus
     * @throws SQLException jos virhe
     */
    public WorkPerformance findWorkPerformanceByNmbr(int nmbr, Connection con) throws SQLException {
        WorkPerformance wP = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyosuoritusnumero, tyokohdenumero FROM tyosuoritus WHERE tyosuoritusnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                wP = createWorkPerformance(resultSet.getInt(1), resultSet.getInt(2));
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
        return wP;
    }
    
    /**
     * etsii työsuorituksia työkohdenumerolla
     * @param nmbr
     * @param con
     * @return arraylist työsuorituksista
     * @throws SQLException jos virhe
     */
    public ArrayList<WorkPerformance> findWorkPerformanceByWorkSiteNmbr(int nmbr, Connection con) throws SQLException {
        WorkPerformance wP = null;
        ArrayList<WorkPerformance> wPAL = new ArrayList<WorkPerformance>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyosuoritusnumero, tyokohdenumero FROM tyosuoritus WHERE tyokohdenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            
                while (resultSet.next()) {
                    wPAL.add(createWorkPerformance(resultSet.getInt(1), resultSet.getInt(2)));
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
        return wPAL;
    }
    
    /**
     * poistaa työsuorituksen
     * @param x
     * @param con
     * @return poistettu työsuoritus
     * @throws SQLException jos virhe
     */
    public WorkPerformance removeWorkPerformance(WorkPerformance x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM tyosuoritus WHERE tyosuoritusnumero = ?");
            pStatement.setInt(1, x.getNmbr());
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return x;    
    }
}