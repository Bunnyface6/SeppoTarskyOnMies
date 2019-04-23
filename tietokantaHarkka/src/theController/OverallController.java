/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theController;

import helpers.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JList;
import view.*;
import java.sql.*;

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
    
    
    class findListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            findDialog curDialog = new findDialog(win, true);
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
            
        }
        
    }
    class findDialogListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            
        }
        
    }

}
