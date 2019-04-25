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
    
    public void addNewPrivateClient(PrivateClient x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO asiakas(osoitenumero) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, x.getLocationNmbr());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            resultSet.last();
            pStatement.clearParameters();
            pStatement = con.prepareStatement("INSERT INTO henkilo VALUES(?, ?, ?)");
            pStatement.setInt(1, resultSet.getInt(1));
            pStatement.setString(2, x.getfName());
            pStatement.setString(2, x.getlName());
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
    
    //IDllä
    public PrivateClient findPrivateClient(int nmbr, Connection con) throws SQLException{
        PrivateClient pC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT henkilo.asiakasnumero, etunimi, sukunimi, asiakas.osoitenumero FROM henkilo, asiakas WHERE asiakas.asiakasnumero = henkilo.asiakasnumero AND henkilo.asiakasnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                pC = createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1), resultSet.getInt(4));
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
        return pC;
    }
    //Etunimellä
    public ArrayList<PrivateClient> findPrivateClientByFName(String fName, Connection con) throws SQLException{
        ArrayList<PrivateClient> pCAL = new ArrayList<PrivateClient>();
	PrivateClient pC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
	int rows = 0;
        try {
            con.setAutoCommit(false);
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
        return pCAL;
    }

    //Sukunimellä
    public ArrayList<PrivateClient> findPrivateClientByLName(String lName, Connection con) throws SQLException{
        ArrayList<PrivateClient> pCAL = new ArrayList<PrivateClient>();
	PrivateClient pC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
	int rows = 0;
        try {
            con.setAutoCommit(false);
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
        return pCAL;
    }
    
    public PrivateClient removePrivateClient(PrivateClient x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM henkilo WHERE asiakasnumero = ?");
            pStatement.setInt(1, x.getNmbr());
            pStatement.executeUpdate();
            pStatement.clearParameters();
            pStatement = con.prepareStatement("DELETE FROM asiakas WHERE asiakasnumero = ?");
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
    
    public ArrayList<PrivateClient> findPrivateClientByUnpaid(Connection con) throws SQLException {
        ArrayList<PrivateClient> l = new ArrayList<PrivateClient>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT henkilo.asiakasnumero, etunimi, sukunimi, asiakas.osoitenumero FROM henkilo, asiakas, lasku WHERE asiakas.asiakasnumero = henkilo.asiakasnumero AND lasku.asiakasnumero = henkilo.asiakasnumero AND maksupaiva IS NULL");
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1), resultSet.getInt(4));
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
        return l;
    }
}