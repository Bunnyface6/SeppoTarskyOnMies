/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dBConnection;

import com.jcraft.jsch.*;
import java.sql.*;
import java.util.Properties;

/**
 * Luokka, jolla yhdistetään tietokantaan.
 * @author Jipsu
 */
public class dBConnection {
    
    /** Yhdistämiseen tarvittavat vakioidut tiedot */
    private final String PROTOCOL = "jdbc:postgresql:";
    private final String SERVER = "dbstud2.sis.uta.fi";
    private final int PORTNUM = 5432;
    private final String DATABASE = "tiko2019r28"; 
    private final String USERNAME = "na428043";
    private final String PASSWORD = "Velimatti1";
    private Connection con;
    /*private static int lport;
    private static String rhost;
    private static int rport;
    private Session session;
    */
    
    /**
     * Luo yhteyden tietokantaan.
     * 
     * @return yhteys-olio
     */
    public Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println(PROTOCOL + "//" + SERVER + ":" + PORTNUM + "/" + DATABASE);
            con = DriverManager.getConnection(PROTOCOL + "//" + "dbstud2.sis.uta.fi" + ":" + PORTNUM + "/" + DATABASE, USERNAME, PASSWORD);
            return con;
	}
	catch (SQLException e) {
            System.out.println(e);
            return null;     
	}
        catch (ClassNotFoundException f){
             System.out.println(f);
            return null;
        }
    }
    
    /**
     * Palauttaa yhteys-olion.
     * 
     * @return yhteys-olio
     */
    public Connection getConnection() {
        return con;
    }
    
    /**
     * Sulkee yhteyden tietokantaan.
     * 
     * @return totuusavo yhteyden sulkemisen onnistumisesta.
     */
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
    
    /**
     *
     
    public void go(){
        String user = "ja427770";
        String password = "";
        String host = "shell.sis.uta.fi";
        int port=22;
        try
            {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            lport = 4321;
            rhost = "dbstud2.sis.uta.fi";
            rport = 5432;
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
            }
        catch(Exception e){System.err.print(e);}
    }
    /**
     *
     */
    public void disconnect(){
       try{
        con.close();
       }
       catch(Exception e){
           System.out.println("Yhteyden sulkeminen epäonnistui");
       }
        System.out.println("Suljetaan yhteys");
   }
}
