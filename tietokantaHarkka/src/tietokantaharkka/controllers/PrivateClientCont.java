/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.PrivateClient;
import java.sql.*;

/**
 *
 * @author Jipsu
 */
public class PrivateClientCont {
    
    private ArrayList<PrivateClient> recentPrivateClients = new ArrayList<PrivateClient>();
    
    private PrivateClient lastUsed;
    
    public PrivateClient createPrivateClient(String fName, String lName, int pCNmbr, int locationNmbr) {
        PrivateClient x = new PrivateClient(fName, lName, pCNmbr, locationNmbr);
        recentPrivateClients.add(x);
        lastUsed = x;
        return x;
    }
    
    /**
     * Lisää tietokannan asiakas- ja henkilo-tauluun uudet asiakas tiedot parametrina saadun olion attribuuttien arvojen perusteella.
     * 
     * @param x viite yksityisasiakas-olioon
     * @param con viite tietokannan yhteys-olioon
     * @throws SQLException jos lisäyksessä tapahtuu virhe
     */
    public void addNewPrivateClient(PrivateClient x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("INSERT INTO asiakas(osoitenumero) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, x.getLocationNmbr());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            resultSet.next();
            pStatement.clearParameters();
            pStatement = con.prepareStatement("INSERT INTO henkilo VALUES(?, ?, ?)");
            pStatement.setInt(1, resultSet.getInt(1));
            pStatement.setString(2, x.getfName());
            pStatement.setString(3, x.getlName());
            pStatement.executeUpdate();
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
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * yksityisasiakkaan tietoja henkilo- ja asiakas-taulusta.
     * 
     * @param nmbr asiakasnumero
     * @param con viite tietokannan yhteys olioon
     * @return yksityisasiakas-olio, jolla tietokannan henkilo-taulun ja asiakas-taulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */		
    public PrivateClient findPrivateClient(int nmbr, Connection con) throws SQLException{
        PrivateClient pC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT henkilo.asiakasnumero, etunimi, sukunimi, asiakas.osoitenumero FROM henkilo, asiakas WHERE asiakas.asiakasnumero = henkilo.asiakasnumero AND henkilo.asiakasnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                pC = createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1), resultSet.getInt(4));
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
        return pC;
    }
    
    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittamien
     * yksityisasiakkaiden tietoja henkilo- ja asiakas-taulusta.
     * 
     * @param fName asiakkaan etunimi
     * @param con viite tietokannan yhteys olioon
     * @return lista yksityisasiakas-oliosta, joilla tietokannan henkilo-taulun ja asiakas-taulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */		
    public ArrayList<PrivateClient> findPrivateClientByFName(String fName, Connection con) throws SQLException{
        ArrayList<PrivateClient> pCAL = new ArrayList<PrivateClient>();
	PrivateClient pC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
	int rows = 0;
        try {
            pStatement = con.prepareStatement("SELECT COUNT(*) FROM henkilo WHERE etunimi = ?");
	    pStatement.setString(1, fName);
	    resultSet = pStatement.executeQuery();
            resultSet.next();
	    rows = resultSet.getInt(1);
	    for (int i = 0; i < rows; i++) {
	        pStatement = con.prepareStatement("SELECT asiakasnumero, etunimi, sukunimi, asiakas.osoitenumero, ROW_NUMBER() over (ORDER BY asiakasnumero) as rownum FROM henkilo, asiakas WHERE asiakas.asiakasnumero = henkilo.asiakasnumero AND etunimi = ? AND rownum = ?");
	        pStatement.setString(1, fName);
		pStatement.setInt(2, i+1);
                resultSet = pStatement.executeQuery();
                resultSet.next();
		pC = createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1), resultSet.getInt(4));
		pCAL.add(pC);
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
        return pCAL;
    }

    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittamien
     * yksityisasiakkaiden tietoja henkilo- ja asiakas-taulusta.
     * 
     * @param lName asiakkaan sukunimi
     * @param con viite tietokannan yhteys olioon
     * @return lista yksityisasiakas-olioista, joilla tietokannan henkilo-taulun ja asiakas-taulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */		
    public ArrayList<PrivateClient> findPrivateClientByLName(String lName, Connection con) throws SQLException{
        ArrayList<PrivateClient> pCAL = new ArrayList<PrivateClient>();
	PrivateClient pC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
	int rows = 0;
        try {
            pStatement = con.prepareStatement("SELECT COUNT(*) FROM henkilo WHERE sukunimi = ?");
	    pStatement.setString(1, lName);
	    resultSet = pStatement.executeQuery();
            resultSet.next();
	    rows = resultSet.getInt(1);
	    for (int i = 0; i < rows; i++) {
	        pStatement = con.prepareStatement("SELECT henkilo.asiakasnumero, etunimi, sukunimi, asiakas.osoitenumero, ROW_NUMBER() over (ORDER BY asiakasnumero) as rownum FROM henkilo, asiakas WHERE asiakas.asiakasnumero = henkilo.asiakasnumero AND sukunimi = ? AND rownum = ?");
	        pStatement.setString(1, lName);
		pStatement.setInt(2, i+1);
                resultSet = pStatement.executeQuery();
                resultSet.next();
		pC = createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1), resultSet.getInt(4));
		pCAL.add(pC);
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
        return pCAL;
    }
    
    /**
     * Poistaa parametrin osoittaman asiakkaan tiedot tietokannan henkilo- ja asiakas-tauluista.
     * 
     * @param x yksityisasiakas-olio
     * @param con viite tietokannan yhteys olioon
     * @return yksityisasiakas-olio, jonka tiedot tietokannasta poistettiin
     * @throws SQLException jos kysely aiheuttaa virheen
     */		
    public PrivateClient removePrivateClient(PrivateClient x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM henkilo WHERE asiakasnumero = ?");
            pStatement.setInt(1, x.getNmbr());
            pStatement.executeUpdate();
            pStatement.clearParameters();
            pStatement = con.prepareStatement("DELETE FROM asiakas WHERE asiakasnumero = ?");
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
    
    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan yksityisasiakkaita, joiden laskut on maksamatta.
     * 
     * @param con viite tietokannan yhteys olioon
     * @return lista yksityisasiakas-olioista, joilla tietokannan henkilo-taulun ja asiakas-taulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */		
    public ArrayList<PrivateClient> findPrivateClientByUnpaid(Connection con) throws SQLException {
        ArrayList<PrivateClient> l = new ArrayList<PrivateClient>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT henkilo.asiakasnumero, etunimi, sukunimi, asiakas.osoitenumero FROM henkilo, asiakas, lasku WHERE asiakas.asiakasnumero = henkilo.asiakasnumero AND lasku.asiakasnumero = henkilo.asiakasnumero AND maksupaiva IS NULL");
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1), resultSet.getInt(4)));
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
        return l;
    }
}