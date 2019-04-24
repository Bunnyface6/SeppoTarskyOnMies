/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theController;

import view.FindDialog;
import helpers.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import view.*;
import java.sql.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author Cassu
 */
public class OverallController {
    
    private Connection con;
    private JList list;
    private MainWindow win;
    private ModelList model;
    
    public OverallController(MainWindow y, Connection con2){
        
        this.con = con2;
        win = y;
        list = win.getList();
        model = new ModelList();
        win.setListeners(new findListener(), new addListener(), new removeListener(), new editListener());
        win.show();
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
    class addContinueButton implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            Component comp = (Component) e.getSource();
            AddMainDialog dial = (AddMainDialog) SwingUtilities.getRoot(comp);
            String str = (String)dial.getValues();
            //Kuluttaja asiakas, Yritysasiakas, Tarvike
            if(str.equals("Kuluttaja-asiakas")){
                
                AddPClientView ndial = new AddPClientView(win, true);
                dial.dispose();
                ndial.addListeners(new addCClientB());
                ndial.show();
                
            }
            if(str.equals("Yritysasiakas")){
                AddCClientView ndial = new AddCClientView(win, true);
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
                Component comp = (Component) e.getSource();
                AddPClientView dial = (AddPClientView)SwingUtilities.getRoot(comp);
                String[] result = dial.getParam();
                
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
