/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;
import java.sql.*;
import java.util.ArrayList;
import tietokantaharkka.baseClasses.Location;

/**
 * Lokaatiokontrolleri
 * @author Cassu
 */
public class LocationCont {
    
    // Ei varmuutta tarvitaanko ArrayListia, tehty varalle.
    private ArrayList<Location> recentLocations = new ArrayList<Location>();
    private Location lastUsed;
    
    /**
     * luo lokaatio-olion
     * @param nmbr
     * @param address
     * @param postNmbr
     * @param city
     * @return luotu lokaatio
     */
    public Location createLocation(int nmbr, String address, int postNmbr, String city){
       Location x = new Location(nmbr, address, postNmbr, city);
       recentLocations.add(x);
       lastUsed = x;
       return x;
    }
    
    /**
     * lisää lokaation databaseen
     * @param x
     * @param con
     * @return lokaationumero
     * @throws SQLException jos lisäyksessä virhe
     */
    public int addNewLocation(Location x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        int lN = 0;
        try {
            pStatement = con.prepareStatement("INSERT INTO osoite(katuosoite, postinumero, postitoimipaikka) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, x.getAddress());
            pStatement.setInt(2, x.getPostNmbr());
	    pStatement.setString(3, x.getCity());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            resultSet.next();
            lN = resultSet.getInt(1);
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return lN;
    }
        
    // Haku idllä

    /**
     * haku lokaationumerolla
     * @param nmbr
     * @param con
     * @return lokaatio
     * @throws SQLException jos etsinnässä virhe
     */
    public Location findLocationByNmbr(int nmbr, Connection con) throws SQLException {
        Location l = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE osoitenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                l = createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4));
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

	// Haku katuosoitteella

    /**
     * etsii lokaatioita osoitteen perusteella
     * @param address
     * @param con
     * @return arraylist lokaatioista
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Location> findLocationByAddress(String address, Connection con) throws SQLException {
        ArrayList<Location> l = new ArrayList<Location>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE katuosoite = ?");
            pStatement.setString(1, address);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4)));
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

	//haku postinumerolla

    /**
     * hakee lokaatioita postinumerolla
     * @param postNmbr
     * @param con
     * @return arraylist lokaatioista
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Location> findLocationByPostNmbr(int postNmbr, Connection con) throws SQLException {
        ArrayList<Location> l = new ArrayList<Location>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE postinumero = ?");
            pStatement.setInt(1, postNmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4)));
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

    /**
     * hakee lokaatioita kaupungilla
     * @param city
     * @param con
     * @return arraylist lokaatioista
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Location> findLocationByCity(String city, Connection con) throws SQLException {
        ArrayList<Location> l = new ArrayList<Location>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE postitoimipaikka = ?");
            pStatement.setString(1, city);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4)));
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
    
    /**
     * hakee lokaatioita osoitteella, postinumerolla ja kaupungilla
     * @param address
     * @param zipCode
     * @param city
     * @param con
     * @return lokaatio
     * @throws SQLException jos etsinnässä virhe
     */
    public Location findLocation(String address, int zipCode, String city, Connection con) throws SQLException {
        Location l = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE katuosoite = ? AND postinumero = ? AND postitoimipaikka = ?");
            pStatement.setString(1, address);
            pStatement.setInt(2, zipCode);
            pStatement.setString(3, city);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                l = createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4));
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

    /**
     * poistaa lokaation
     * @param x
     * @param con
     * @return poistettu lokaatio
     * @throws SQLException jos poistossa virhe
     */
    public Location removeLocation(Location x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM osoite WHERE osoitenumero = ?");
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