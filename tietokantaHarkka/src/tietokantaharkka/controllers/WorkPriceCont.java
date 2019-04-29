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
 * Työhinnasto-controlleri.
 * @author Jipsu
 */
public class WorkPriceCont {
    
    /** Viimeisimmät työhinnat */
    private ArrayList<WorkPrice> recentWorkPrices = new ArrayList<WorkPrice>();
    
    /** Viimeisin työhinta */
    private WorkPrice lastUsed;
    
    /**
     * Luo uuden työhinnan. Asettaa luodun olion viimeisimpien 
     * olioiden listalle ja viimeksimmän olion viitteeseen.
     * 
     * @param workType tyyppi
     * @param price hinta
     * @return työhinta-olio
     */
    public WorkPrice createWorkPrice(String workType, double price) {
        WorkPrice x = new WorkPrice(workType, price);
        recentWorkPrices.add(x);
        lastUsed = x;
        return x;
    }
    
    /**
     * Asettaa uuden rivin työhinnasto-tauluun parametrina saaduilla olion tiedoilla.
     * 
     * @param x työhinta-olio
     * @param con yhteys-olio
     * @throws SQLException jos rivien asetus epäonnistuu.
     */
    public void addNewWorkPrice(WorkPrice x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("INSERT INTO tyohinnasto(tyyppi, hinta) VALUES(?, ?)");
            pStatement.setString(1, x.getWorkType());
            pStatement.setDouble(2, x.getPrice());
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }    	
    }
    
    /**
     * Hakee tietokannan tyohinnasto-taulusta parametria vastaavan rivin ja käärii sen olioksi.
     * 
     * @param type työtyyppi
     * @param con yhteys-olio
     * @return työhinta-olio
     * @throws SQLException jos kysely epäonnistuu
     */
    public WorkPrice findWorkPriceByWorkType(String type, Connection con) throws SQLException {
        WorkPrice wP = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyyppi, hinta FROM tyohinnasto WHERE tyyppi = ?");
            pStatement.setString(1, type);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                wP = createWorkPrice(resultSet.getString(1), resultSet.getDouble(2));
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
     * Hakee tietokannan tyohinnasto-taulusta rivit ja käärii ne olioksiksi.
     * 
     * @param con yhteys-olio
     * @return työhinnasto-olio-lista
     * @throws SQLException jos haku epäonnistuu
     */    
    public ArrayList<WorkPrice> findWorkPrices(Connection con) throws SQLException {
        ArrayList<WorkPrice> wP = new ArrayList<WorkPrice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyyppi, hinta FROM tyohinnasto");
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                wP.add(createWorkPrice(resultSet.getString(1), resultSet.getDouble(2)));
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
     * Poistaa tietokannan tyohinnasto-taulusta parametrina saatua oliota vastaavan rivin.
     * 
     * @param x työhinta-olio
     * @param con yhtyes-olio
     * @return poistettu työjinta-olio
     * @throws SQLException jos poisto epäonnistuu.
     */    
    public WorkPrice removeWorkPrice(WorkPrice x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM tyohinnasto WHERE tyyppi = ?");
            pStatement.setString(1, x.getWorkType());
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
