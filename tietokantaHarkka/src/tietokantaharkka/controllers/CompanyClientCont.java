/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tietokantaharkka.controllers;
import java.sql.*;
import tietokantaharkka.baseClasses.CompanyClient;
import java.util.ArrayList;

/**
 * Yritysasiakas controlleri.
 * @author Jipsu
 */
public class CompanyClientCont {
    
    /** Viimeisimmät yritysasiakkaat */
    private ArrayList<CompanyClient> recentCompanyClients = new ArrayList<CompanyClient>();
    
    /** Viimeisin yritysasiakas */
    private CompanyClient lastUsed;
    
    /**
     * Luo uuden yritysasiakkaan. Asettaa luodun olion viimeisimpien 
     * olioiden listalle ja viimeksimmän olion viitteeseen.
     * 
     * @param name nimi
     * @param yIdentifier y-tunnus
     * @param nmbr asiakasnumero
     * @param locationNmbr osoitenumero
     * @return yrtitysasiakas-olio
     */
    public CompanyClient createCompanyClient(String name, int yIdentifier, int nmbr, int locationNmbr) {
        CompanyClient x = new CompanyClient(name, yIdentifier, nmbr, locationNmbr);
        recentCompanyClients.add(x);
        lastUsed = x;
        return x;
    }
    
    /**
      * Lisää tietokannan asiakas- ja yritys-tauluun uudet asiakas tiedot parametrina saadun olion attribuuttien arvojen perusteella.
      * 
      * @param x viite yritysasiakas-olioon
      * @param con viite tietokannan yhteys-olioon
      * @throws SQLException jos lisäyksessä tapahtuu virhe
      */
    public void addNewCompanyClient(CompanyClient x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("INSERT INTO asiakas(osoitenumero) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, x.getLocationNmbr());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            resultSet.next();
            pStatement.clearParameters();
            pStatement = con.prepareStatement("INSERT INTO yritys(asiakasnumero, y-tunnus, nimi) VALUES(?, ?, ?)");
            pStatement.setInt(1, resultSet.getInt(1));
            pStatement.setInt(2, x.getyIdentifier());
            pStatement.setString(2, x.getName());
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
      * yritysasiakkaan tietoja yritys- ja asiakas-taulusta.
      * 
      * @param nmbr asiakasnumero
      * @param con viite tietokannan yhteys-olioon
      * @return yritysasiakas-olio, jolla tietokannan yritys-taulun ja asiakas-taulun tiedot
      * @throws SQLException jos kysely aiheuttaa virheen
      */		
    public CompanyClient findCompanyClient(int nmbr, Connection con) throws SQLException{
        CompanyClient cC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT yritys.asiakasnumero, yritys.y-tunnus, yritys.nimi, asiakas.osoitenumero FROM yritys, asiakas WHERE asiakas.asiakasnumero = yritys.asiakasnumero AND yritys.asiakasnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                cC = createCompanyClient(resultSet.getString(3), resultSet.getInt(2), resultSet.getInt(1), resultSet.getInt(4));
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
        return cC;
    }
    
    /**
      * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittamien
      * yritysasiakkaiden tietoja yritys- ja asiakas-taulusta.
      * 
      * @param name yrityksen nimi
      * @param con viite tietokannan yhteys olioon
      * @return lista yritysasiakas-oliosta, joilla tietokannan yritys-taulun ja asiakas-taulun tiedot
      * @throws SQLException jos kysely aiheuttaa virheen
      */		
    public ArrayList<CompanyClient> findCompanyClientByName(String name, Connection con) throws SQLException{
        ArrayList<CompanyClient> cCAL = new ArrayList<CompanyClient>();
	CompanyClient cC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
	int rows = 0;
        try {
            pStatement = con.prepareStatement("SELECT COUNT(*) FROM yritys WHERE nimi = ?");
	    pStatement.setString(1, name);
	    resultSet = pStatement.executeQuery();
	    resultSet.next();
            rows = resultSet.getInt(1);
	    for (int i = 0; i < rows; i++) {
	        pStatement = con.prepareStatement("SELECT yritys.asiakasnumero, y-tunnus, nimi, asiakas.osoitenumero, ROW_NUMBER() over (ORDER BY asiakasnumero) as rownum FROM yritys, asiakas WHERE asiakas.asiakasnumero = yritys.asiakasnumero AND nimi = ? AND rownum = ?");
	        pStatement.setString(1, name);
		pStatement.setInt(2, i+1);
                resultSet = pStatement.executeQuery();
                resultSet.next();
		cC = createCompanyClient(resultSet.getString(3), resultSet.getInt(2), resultSet.getInt(1), resultSet.getInt(4));
		cCAL.add(cC);
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
        return cCAL;
    }


    /**
      * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
      * yritysasiakkaan tietoja yritys- ja asiakas-taulusta.
      * 
      * @param yIdentifier y-tunnus
      * @param con viite tietokannan yhteys-olioon
      * @return yritysasiakas-olio, jolla tietokannan yritys-taulun ja asiakas-taulun tiedot
      * @throws SQLException jos kysely aiheuttaa virheen
      */		
    public CompanyClient findCompanyClientByYIdentifier(int yIdentifier, Connection con) throws SQLException{
        CompanyClient cC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT yritys.asiakasnumero, yritys.y-tunnus, yritys.nimi, asiakas.osoitenumero FROM yritys, asiakas WHERE asiakas.asiakasnumero = yritys.asiakasnumero AND yritys.y-tunnus = ?");
            pStatement.setInt(1, yIdentifier);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                cC = createCompanyClient(resultSet.getString(3), resultSet.getInt(2), resultSet.getInt(1), resultSet.getInt(4));
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
        return cC;
    }
    
    /**
      * Poistaa parametrin osoittaman asiakkaan tiedot tietokannan yritys- ja asiakas-tauluista.
      * 
      * @param x yritysasiakas-olio
      * @param con viite tietokannan yhteys olioon
      * @return yritysasiakas-olio, jonka tiedot tietokannasta poistettiin
      * @throws SQLException jos kysely aiheuttaa virheen
      */		
    public CompanyClient removeCompanyClient(CompanyClient x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM yritys WHERE asiakasnumero = ?");
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
      * Suorittaa tietokantaan kyselyn, jonka avulla haetaan yritysasiakkaita, joiden laskut on maksamatta.
      * 
      * @param con viite tietokannan yhteys-olioon
      * @return lista yritysasiakas-olioista, joilla tietokannan yritys-taulun ja asiakas-taulun tiedot
      * @throws SQLException jos kysely aiheuttaa virheen
      */
    public ArrayList<CompanyClient> findCompanyClientByUnpaid(Connection con) throws SQLException {
        ArrayList<CompanyClient> l = new ArrayList<CompanyClient>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("\"SELECT yritys.asiakasnumero, yritys.y-tunnus, yritys.nimi, asiakas.osoitenumero FROM yritys, asiakas, lasku WHERE asiakas.asiakasnumero = yritys.asiakasnumero AND lasku.asiakasnumero = henkilo.asiakasnumero AND maksupaiva IS NULL");
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createCompanyClient(resultSet.getString(3), resultSet.getInt(2), resultSet.getInt(1), resultSet.getInt(4)));
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