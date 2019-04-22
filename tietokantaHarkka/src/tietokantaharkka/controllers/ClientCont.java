/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.Client;
import java.sql.*;

public class ClientCont {
	
    private ArrayList<Client> recentClients = new ArrayList<Client>();
    private Client lastUsed;
	
    public Client createClient(int nmbr, int locationNmbr){
        Client x = new Client(nmbr, locationNmbr);
	recentClients.add(x);
	lastUsed = x;
	return x;
    }
	
    public void addNewClient(Client x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO asiakas(osoitenumero) VALUES(?)");
            pStatement.setInt(1, x.getLocationNmbr());
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
	
    public Client findClientByNmbr(int nmbr, Connection con) throws SQLException {
        Client c = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT asiakasnumero, osoitenumero FROM asiakas WHERE asiakasnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                c = createClient(resultSet.getInt(1), resultSet.getInt(2));
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
        return c;
    }
    
    public ArrayList<Client> findClientByLocationNmbr(int nmbr, Connection con) throws SQLException {
        ArrayList<Client> c = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT asiakasnumero, osoitenumero FROM asiakas WHERE osoitenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                c.add(createClient(resultSet.getInt(1), resultSet.getInt(2)));
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
        return c;
    }
    
    public Client removeClient(Client x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
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
}