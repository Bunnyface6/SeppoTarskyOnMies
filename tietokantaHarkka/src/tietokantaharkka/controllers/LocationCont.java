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
 *
 * @author Cassu
 */
public class LocationCont {
    
    // Ei varmuutta tarvitaanko ArrayListia, tehty varalle.
    private ArrayList<Location> recentLocations = new ArrayList<Location>();
    private Location lastUsed;
    
    public Location createLocation(int nmbr, String address, int postNmbr, String city){
       Location x = new Location(nmbr, address, postNmbr, city);
       recentLocations.add(x);
       lastUsed = x;
       return x;
    }
    
    public int addNewLocation(Location x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        int lN = 0;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO osoite(katuosoite, postinumero, postitoimipaikka) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, x.getAddress());
            pStatement.setInt(2, x.getPostNmbr());
	    pStatement.setString(3, x.getCity());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            resultSet.last();
            lN = resultSet.getInt(1);
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
        return lN;
    }
        
    // Haku idllä
    public Location findLocationByNmbr(int nmbr, Connection con) throws SQLException {
        Location l = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE osoitenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                l = createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4));
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

	// Haku katuosoitteella
    public ArrayList<Location> findLocationByAddress(String address, Connection con) throws SQLException {
        ArrayList<Location> l = new ArrayList<Location>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE katuosoite = ?");
            pStatement.setString(1, address);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4)));
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

	//haku postinumerolla
    public ArrayList<Location> findLocationByPostNmbr(int postNmbr, Connection con) throws SQLException {
        ArrayList<Location> l = new ArrayList<Location>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE postinumero = ?");
            pStatement.setInt(1, postNmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4)));
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

    public ArrayList<Location> findLocationByCity(String city, Connection con) throws SQLException {
        ArrayList<Location> l = new ArrayList<Location>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE postitoimipaikka = ?");
            pStatement.setString(1, city);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                l.add(createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4)));
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
    
    public Location findLocation(String address, int zipCode, String city, Connection con) throws SQLException {
        Location l = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE katuosoite = ? AND postinumero = ? AND postitoimipaikka = ?");
            pStatement.setString(3, city);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                l = createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4));
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

    public Location removeLocation(Location x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM osoite WHERE osoitenumero = ?");
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