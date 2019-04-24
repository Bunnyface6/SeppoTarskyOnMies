/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBConnection;

import java.sql.*;

/**
 *
 * @author Jipsu
 */
public class dBConnection {
    
    private final String PROTOCOL = "jdbc:postgresql:";
    private final String SERVER = "dbstud2.sis.uta.fi";
    private final int PORTNUM = 5432;
    private final String DATABASE = "tiko2019r28"; 
    private final String USERNAME = "na428043";
    private final String PASSWORD = "Velimatti1";
    private Connection con;
    
    public Connection createConnection() {
        try {
            con = DriverManager.getConnection(PROTOCOL + "//" + SERVER + ":" + PORTNUM + "/" + DATABASE, USERNAME, PASSWORD);
            return con;
	}
	catch (SQLException e) {
            return null;     
	}
    }
    
    public boolean closeConnection() {
        try {
	    if (con != null) {
	        con.close();
                return true;
            }
            else {
                return false;
            }
        }
        catch(SQLException e) {
	    return false;
	}			
    }
}
