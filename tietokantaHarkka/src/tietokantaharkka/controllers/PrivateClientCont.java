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
    
    public PrivateClient createPrivateClient(String fName, String lName, int pCNmbr) {
        PrivateClient x = new PrivateClient(fName, lName, pCNmbr);
        recentPrivateClients.add(x);
        lastUsed = x;
        return x;
    }
    
    public void addNewPrivateClient(PrivateClient x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO henkilo VALUES(?, ?, ?)");
            pStatement.setInt(1, x.getNmbr());
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
            pStatement = con.prepareStatement("SELECT asiakasnumero, etunimi, sukunimi FROM henkilo WHERE asiakasnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            pC = createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1));
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
	    rows = resultSet.getInt(1);
	    for (int i = 0; i < rows; i++) {
	         pStatement = con.prepareStatement("SELECT asiakasnumero, etunimi, sukunimi, ROW_NUMBER() over (ORDER BY asiakasnumero) as rownum FROM henkilo WHERE etunimi = ? AND rownum = ?");
	         pStatement.setString(1, fName);
		 pStatement.setInt(2, i+1);
		 pC = createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1));
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
	    rows = resultSet.getInt(1);
	    for (int i = 0; i < rows; i++) {
	         pStatement = con.prepareStatement("SELECT asiakasnumero, etunimi, sukunimi, ROW_NUMBER() over (ORDER BY asiakasnumero) as rownum FROM henkilo WHERE sukunimi = ? AND rownum = ?");
	         pStatement.setString(1, lName);
		 pStatement.setInt(2, i+1);
		 pC = createPrivateClient(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(1));
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
