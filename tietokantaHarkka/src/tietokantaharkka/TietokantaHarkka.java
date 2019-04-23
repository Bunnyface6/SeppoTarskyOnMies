/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka;

import dBConnection.dBConnection;
import theController.OverallController;
import view.MainWindow;

/**
 *
 * @author Cassu
 */
public class TietokantaHarkka {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        dBConnection connect = new dBConnection();
        MainWindow view = new MainWindow();
        OverallController controller = new OverallController(view, connect.createConnection());
    }
    
}
