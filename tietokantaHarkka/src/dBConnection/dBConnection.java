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
    private static int lport;
    private static String rhost;
    private static int rport;
    private Session session;
    
    /**
     *
     * @return
     */
    public Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println(PROTOCOL + "//" + SERVER + ":" + PORTNUM + "/" + DATABASE);
            con = DriverManager.getConnection(PROTOCOL + "//" + "localhost" + ":" + lport + "/" + DATABASE, USERNAME, PASSWORD);
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
     *
     * @return
     */
    public Connection getConnection() {
        return con;
    }
    
    /**
     *
     * @return
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
     */
    public void go(){
        String user = "ja427770";
        String password = "Predator669";
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
       System.out.println("Suljetaan");
       try{
        con.close();
       }
       catch(Exception e){
           System.out.println("JOTAIN MENI PIELEEN");
       }
       session.disconnect();
       System.out.println("Suljetaan");
   }
}
