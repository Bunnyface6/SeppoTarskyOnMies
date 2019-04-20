/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.Location;

/**
 *
 * @author Cassu
 */
public class locationCont {
    
    // Ei varmuutta tarvitaanko ArrayListia, tehty varalle.
    private ArrayList<Location> recentLocations = new ArrayList<Location>();
    private Location lastUsed;
    
    public Location createLocation(int nmbr,String address,int postNmbr,String city){
       Location x = new Location(nmbr, address, postNmbr, city);
       recentLocations.add(x);
       lastUsed = x;
       return x;
        //TODO Luo uusi locaatio
    }
    
    public void addNewLocation(Location x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO osoite(katuosoite, postinumero, postitoimipaikka) VALUES(?, ?, ?)");
            pStatement.setString(1, x.getAddress());
            pStatement.setInt(2, x.getPostNmbr());
	    pStatement.setInt(3, x.getCity());
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
    
    public Location findLocation(int nmbr){
        //TODO Hae db.stä sekä palauta
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
            l = createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),);
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
    public Location findLocationByAddress(String address, Connection con) throws SQLException {
        Location l = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE katuosoite = ?");
            pStatement.setInt(1, address);
            resultSet = pStatement.executeQuery();
            l = createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),);
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
    public Location findLocationByPostNmbr(int postNmbr, Connection con) throws SQLException {
        Location l = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE postinumero = ?");
            pStatement.setInt(1, postNmbr);
            resultSet = pStatement.executeQuery();
            l = createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),);
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

    public Location findLocationByCity(String city, Connection con) throws SQLException {
        Location l = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT osoitenumero, katuosoite, postinumero, postitoimipaikka FROM osoite WHERE postitoimipaikka = ?");
            pStatement.setInt(1, city);
            resultSet = pStatement.executeQuery();
            l = createLocation(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),);
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
            pStatement.setInt(1, x.geNmbr());
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
