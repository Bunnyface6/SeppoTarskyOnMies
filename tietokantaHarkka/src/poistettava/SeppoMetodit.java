/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sM;

import java.sql.*;

/**
 *
 * @author Jipsu
 */
public class SeppoMetodit {
    
    public boolean addClient(String fName, String lName, String address, int zipCode, String city, Connection con) {
        try {
            con.setAutoCommit(false);
            LocationCont lC = new LocationCont();
            Location l = new Location(0, address, zipCode, city);
            int lN = lC.addNewLocation(l);
            PrivateClientCont pVC = new PrivateClientCont();
            PrivateClient pV = new PrivateClient(fName, lName, 0, lN);
            pVC.addNewPrivateClient(pV);
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false;
        }
    }
}
