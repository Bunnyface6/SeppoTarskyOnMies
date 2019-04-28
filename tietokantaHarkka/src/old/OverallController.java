/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old;

import old.ModelList;
import old.FindMethods;
import dBConnection.dBConnection;
import helpers.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JList;
import view.*;
import java.sql.*;
import javax.swing.SwingUtilities;
import transaction.Transaction;

/**
 *
 * @author Cassu
 */
public class OverallController {
    /*
    private Connection con;
    private JList list;
    private MainWindow win;
    private ModelList model;
    private dBConnection connection;
    
    public OverallController(MainWindow y, dBConnection connect){
        connection = connect;
        this.con = connection.createConnection();
        win = y;
        win.addWindowListener(new mainWinL());
        list = win.getList();
        model = new ModelList();
        win.setListeners(new findListener(), new addListener(), new removeListener(), new editListener(), new closeConL());
        win.show();
    }
    
    class mainWinL implements WindowListener{
        
        @Override 
        public void windowDeactivated(WindowEvent e){
            System.out.println("WHATS THIS");
        }

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            connection.disconnect();
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }
        
        
    }
    // NÄMÄ KUULUVAT PÄÄIKKUNAAN
    class findListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            FindDialog curDialog = new FindDialog(win, true);
            curDialog.setListener(new findDialogListener());
            curDialog.show();
        }
        
    }
    
    class removeListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
        }
        
    }
    
    class editListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
        }
        
    }
    
    class addListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            AddMainDialog dial = new AddMainDialog(win, true);
            dial.addListeners(new addContinueButton());
            dial.show();
        }
    }

    // TÄSTÄ ALASPÄIN KUULUVAT DIALOGEIHIN
    class findDialogListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            Component comp = (Component) e.getSource();
            FindDialog dial = (FindDialog) SwingUtilities.getRoot(comp);
            FindMethods finder = new FindMethods(model, con);
            finder.findInDb(dial.getParameters());
        }
    }
    
    class closeConL implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                connection.disconnect();
            }
        }
    class addContinueButton implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            Component comp = (Component) e.getSource();
            AddMainDialog dial = (AddMainDialog) SwingUtilities.getRoot(comp);
            String str = (String)dial.getValues();
            //Kuluttaja asiakas, Yritysasiakas, Tarvike
            if(str.equals("Kuluttaja-asiakas")){
                
                AddCClientView ndial = new AddCClientView(win, true);
                dial.dispose();
                ndial.addListeners(new addCClientB());
                ndial.show();
                
            }
            if(str.equals("Yritysasiakas")){
                AddPClientView ndial = new AddPClientView(win, true);
                dial.dispose();
                ndial.addListeners(new addPClientB());
                ndial.show();
                
            }
            if(str.equals("Tarvike")){
                AddCClientView ndial = new AddCClientView(win, true);
                dial.dispose();
                ndial.addListeners(new addArticleB());
                ndial.show();
            }
        }
        class addPClientB implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                Component comp = (Component)e.getSource();
                AddPClientView dial = (AddPClientView)SwingUtilities.getRoot(comp);
                String[] result = dial.getParam();
                Transaction ta = new Transaction();
                try{
                    System.out.println(ta.addClient(result[0], result[1], result[4], Integer.parseInt(result[3]), result[2], con));
                }
                catch(SQLException f){
                    System.out.println(f.getMessage());
                    System.out.println("AAAARG");
                }
            }
        }
        class addCClientB implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                Component comp = (Component) e.getSource();
                AddCClientView dial = (AddCClientView)SwingUtilities.getRoot(comp);
                String[] result = dial.getParam();
                
            }
            
        }
        class addArticleB implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                Component comp = (Component) e.getSource();
                AddCClientView dial = (AddCClientView)SwingUtilities.getRoot(comp);
                String[] result = dial.getParam();
                
            } 
        }
        
    }
    /*
    class addLocationButtonFromWS implements ActionListener{
        
        @Override
         public void actionPerformed(ActionEvent e){
            Component comp = (Component) e.getSource();
            
            AddWorkSiteView dial = (AddWorkSiteView)SwingUtilities.getRoot(comp);
            
            AddLocationView locDial = new AddLocationView(win, true);
            
            locDial.setComponent(dial.getTextBox());
            
            locDial.show();
         }
    }
    *//*
    class saveLocationButton implements ActionListener{
        
         @Override
         public void actionPerformed(ActionEvent e){
             
             Component comp = (Component) e.getSource();
             
             AddLocationView dial = (AddLocationView)SwingUtilities.getRoot(comp);
             
             
             
         }
    }
*/
}
