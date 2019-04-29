/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.Client;
import java.sql.*;

/**
 * Yritysasiakas controlleri.
 * @author Jipsu
 */
public class ClientCont {
    
     /** Viimeisimmät asiakkaat */
    private ArrayList<Client> recentClients = new ArrayList<Client>();
    
    /** Viimeisin asiakas */
    private Client lastUsed;
	
    /**
     * Luo uuden asiakkaan. Asettaa luodun olion viimeisimpien 
     * olioiden listalle ja viimeksimmän olion viitteeseen.
     * 
     * @param nmbr asiakasnumero
     * @param locationNmbr osoitenumero
     * @return asiakas-olio
     */
    public Client createClient(int nmbr, int locationNmbr){
        Client x = new Client(nmbr, locationNmbr);
	recentClients.add(x);
	lastUsed = x;
	return x;
    }
    
    /**
     * Lisää tietokannan asiakas-tauluun uudet asiakas tiedot parametrina saadun olion attribuuttien arvojen perusteella.
     * 
     * @param x asiakas-olio
     * @param con yhteys-olio tietokantaan
     * @throws SQLException jos lisäyksessä tapahtuu virhe
     */
    public void addNewClient(Client x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("INSERT INTO asiakas(osoitenumero) VALUES(?)");
            pStatement.setInt(1, x.getLocationNmbr());
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }    	
    }
    
    /**
     * Etsii asiakkas taulun rivin, koka vastaa parametrina saatua asiakasnumeroa.
     * Tiedot kääritään olioon.
     * 
     * @param nmbr asiakasnumero
     * @param con yhteys-olio
     * @return asiakas-olio
     * @throws SQLException jos kysely tuottaa virheen
     */
    public Client findClientByNmbr(int nmbr, Connection con) throws SQLException {
        Client c = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT asiakasnumero, osoitenumero FROM asiakas WHERE asiakasnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                c = createClient(resultSet.getInt(1), resultSet.getInt(2));
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
        return c;
    }
    
    /**
     * Etsii asiakkas taulun rivin, koka vastaa parametrina saatua osoitenumeroa.
     * Tiedot kääritään olioon.
     * 
     * @param nmbr osoitenumero
     * @param con yhteys-olio
     * @return asiakas-olio
     * @throws SQLException jos kysely tuottaa virheen
     */
    public ArrayList<Client> findClientByLocationNmbr(int nmbr, Connection con) throws SQLException {
        ArrayList<Client> c = new ArrayList<Client>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT asiakasnumero, osoitenumero FROM asiakas WHERE osoitenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                c.add(createClient(resultSet.getInt(1), resultSet.getInt(2)));
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
        return c;
    }
    
    /**
     * Poistaa tietokannan asiakas-taulusta parametrina saatua oliota vastaavan rivin.
     * 
     * @param x poistettava asiakas
     * @param con yhteys-olio
     * @return poistettu olio
     * @throws SQLException 
     */
    public Client removeClient(Client x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
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
}