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
 * Työkohde-controlleri.
 * @author Jipsu
 */
public class WorkSiteCont {
    
    /** Viimeisimmät työkohteet */
    private ArrayList<WorkSite> recentWorkSites = new ArrayList<WorkSite>();
    
    /** Viimeisin työkohde */
    private WorkSite lastUsed;
    
    /**
     * Luo uuden työkohteen. Asettaa luodun olion viimeisimpien 
     * olioiden listalle ja viimeksimmän olion viitteeseen.
     * 
     * @param locationNmbr osoitekohdenumero
     * @param clientNmbr asiakasnumero
     * @param nmbr työkohdenumero
     * @param contractPrice urakkahinta
     * @return työkohde-olio
     */
    public WorkSite createWorkSite(int locationNmbr, int clientNmbr, int nmbr, double contractPrice) {
        WorkSite x = new WorkSite(locationNmbr, clientNmbr, nmbr, contractPrice);
        recentWorkSites.add(x);
        lastUsed = x;
        return x;
    }
    
    /**
     * Asettaa uuden rivin työkohde tauluun ja urakka tauluun parametrina saaduilla olion tiedoilla.
     * 
     * @param x työkohde-olio
     * @param con yhteys-olio
     * @throws SQLException jos rivien asetus epäonnistuu.
     */
    public void addNewWorkSite(WorkSite x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("INSERT INTO tyokohde(asiakasnro, osoitenumero) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, x.getClientNmbr());
            pStatement.setInt(2, x.getLocationNmbr());
            pStatement.executeUpdate();
            if (x.getContractPrice() > 0) {
                resultSet = pStatement.getGeneratedKeys();
                resultSet.last();
                pStatement.clearParameters();
                pStatement = con.prepareStatement("INSERT INTO urarakka(tyokohdenumero, urakkahinta) VALUES(?, ?)");
                pStatement.setInt(1, resultSet.getInt(1));
                pStatement.setDouble(2, x.getContractPrice());
                pStatement.executeUpdate();
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
    }   
    

    /**
     * Hakee tietokannan tyokohde-taulusta parametria vastaavan rivin ja käärii sen olioksi.
     * 
     * @param nmbr työkohdenumero
     * @param con yhteys-olio
     * @return työkohde-olio
     * @throws SQLException jos kysely epäonnistuu
     */
    public WorkSite findWorkSiteByNmbr(int nmbr, Connection con) throws SQLException {
        WorkSite wS = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyokohde.tyokohdenumero, tyokohde.osoitenumero, tyokohde.asiakasnro, urakka.urakkahinta "
                                              + "FROM tyokohde LEFT OUTER JOIN urakka ON tyokohde.tyokohdenumero = urakka.tyokohdenumero WHERE tyokohde.tyokohdenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                Double d = resultSet.getDouble(4);
                if (d.isNaN()) {
                    d = new Double(0); 
                }
                wS = createWorkSite(resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(1), d);
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
        return wS;
    }

    /**
     * Hakee tietokannan tyokohde-taulusta parametria vastaavat rivit ja käärii ne olioksiksi.
     * 
     * @param clientNmbr asiakasnumero
     * @param con yhteys-olio
     * @return työkohde-olio-lista
     * @throws SQLException jos haku epäonnistuu
     */
    public ArrayList<WorkSite> findWorkSiteByClientNmbr(int clientNmbr, Connection con) throws SQLException {
        WorkSite wS = null;
	ArrayList<WorkSite> wSAL = new ArrayList<WorkSite>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
	         pStatement = con.prepareStatement("SELECT tyokohde.tyokohdenumero, tyokohde.osoitenumero, tyokohde.asiakasnro, urakka.urakkahinta FROM tyokohde LEFT OUTER JOIN urakka ON tyokohde.tyokohdenumero = urakka.tyokohdenumero WHERE tyokohde.asiakasnro = ?");
	         pStatement.setInt(1, clientNmbr);
                 resultSet = pStatement.executeQuery();
                         
            while(resultSet.next()){
                Double d = resultSet.getDouble(4);
                     if (d.isNaN()) {
                         d = new Double(0); 
                     }
		 wS = createWorkSite(resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(1), d);
		 wSAL.add(wS);
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
        return wSAL;
    }
    

    /**
     * Hakee tietokannan tyokohde-taulusta parametria vastaavat rivit ja käärii ne olioksiksi.
     * 
     * @param locationNmbr osoitenumero
     * @param con yhteys-olio
     * @return työkohde-olio-lista
     * @throws SQLException jos haku epäonnistuu
     */
    public ArrayList<WorkSite> findWorkSiteByLocationNmbr(int locationNmbr, Connection con) throws SQLException {
        WorkSite wS = null;
	ArrayList<WorkSite> wSAL = new ArrayList<WorkSite>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
	int rows = 0;
        try {
            pStatement = con.prepareStatement("SELECT COUNT(*) FROM tyokohde WHERE osoitenumero = ?");
	    pStatement.setInt(1, locationNmbr);
	    resultSet = pStatement.executeQuery();
            resultSet.next();
	    rows = resultSet.getInt(1);
	    for (int i = 0; i < rows; i++) {
	         pStatement = con.prepareStatement("SELECT tyokohde.tyokohdenumero, tyokohde.osoitenumero, tyokohde.asiakasnro, urakka.urakkahinta ROW_NUMBER() over (ORDER BY asiakasnumero) as rownum "
                                              + "FROM tyokohde LEFT OUTER JOIN urakka ON tyokohde.tyokohdenumero = urakka.tyokohdenumero WHERE tyokohde.osoitenumero = ? AND rownum = ?");
	         pStatement.setInt(1, locationNmbr);
		 pStatement.setInt(2, i+1);
                 resultSet = pStatement.executeQuery();
                 resultSet.next();
		 Double d = resultSet.getDouble(4);
                     if (d.isNaN()) {
                         d = new Double(0); 
                     }
		 wS = createWorkSite(resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(1), d);
		 wSAL.add(wS);
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
        return wSAL;
    }
    
    /**
     * Poistaa tietokannan tyokohde-taulusta ja urakka-taulusta parametrina saatua oliota vastaavat rivit.
     * 
     * @param x työkohde-olio
     * @param con yhtyes-olio
     * @return poistettu työkohde-olio
     * @throws SQLException jos poisto epäonnistuu.
     */    
    public WorkSite removeWorkSite(WorkSite x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM urakka WHERE tyokohdenumero = ?");
            pStatement.setInt(1, x.getNmbr());
            pStatement.executeUpdate();
            pStatement.clearParameters();
            pStatement = con.prepareStatement("DELETE FROM tyokohde WHERE tyokohdenumero = ?");
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