/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;
import java.sql.*;
import tietokantaharkka.baseClasses.WorkSite;
import java.util.ArrayList;

/**
 *
 * @author Jipsu
 */
public class WorkSiteCont {
    
    private ArrayList<WorkSite> recentWorkSites = new ArrayList<WorkSite>();
    
    private WorkSite lastUsed;
    
    public WorkSite createWorkSite(int locationNmbr, int clientNmbr) {
        WorkSite x = new WorkSite(locationNmbr, clientNmbr);
        recentWorkSites.add(x);
        lastUsed = x;
        return x;
    }

    public void addNewWorkSite(int clientNmbr, WorkSite x){
        //TODO Lisää uusi locaatio databaseen
    }
    
    public WorkSite findWorkSite(int nmbr, Connection con) throws SQLException{
        WorkSite wS = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, asiakasnumero FROM tyokohde WHERE tyokohdenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            wS = new WorkSite(resultSet.getInt(1), resultSet.getInt(2));
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
        return wS;
    }
    
    //public WorkSite findWorkSite(int locationNmbr, int clientNmbr){
        //TODO Hae db.stä sekä palauta
    //}
    
    public WorkSite removeWorkSite(WorkSite x) {
        
    }
}
