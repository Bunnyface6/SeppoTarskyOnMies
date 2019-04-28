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
    
    public WorkPerformance createWorkPerformance(int nmbr,int workSiteNmbr) {
        WorkPerformance x = new WorkPerformance(nmbr, workSiteNmbr);
        recentWorkPerformances.add(x);
        lastUsed = x;
        return x;
    }
    
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
    
    public WorkPerformance findWorkPerformanceByNmbr(int nmbr, Connection con) throws SQLException {
        WorkPerformance wP = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyosuoritusnumero, tyokohdenumero FROM tyosuoritus WHERE tyosuoritusnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                wP = createWorkPerformance(resultSet.getInt(1), resultSet.getInt(2));
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
    
    public ArrayList<WorkPerformance> findWorkPerformanceByWorkSiteNmbr(int nmbr, Connection con) throws SQLException {
        WorkPerformance wP = null;
        ArrayList<WorkPerformance> wPAL = new ArrayList<WorkPerformance>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyosuoritusnumero, tyokohdenumero FROM tyosuoritus WHERE tyokohdenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            
                while (resultSet.next()) {
                    wPAL.add(createWorkPerformance(resultSet.getInt(1), resultSet.getInt(2)));
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
        return wPAL;
    }
    
    public WorkPerformance removeWorkPerformance(WorkPerformance x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM tyosuoritus WHERE tyosuoritusnumero = ?");
            pStatement.setInt(1, x.getNmbr());
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