/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka;

import theController.OverallController;
import view.MainWindow;
import view.testView;

/**
 *
 * @author Cassu
 */
public class TietokantaHarkka {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow view = new MainWindow();
        OverallController controller = new OverallController(view);
    }
    
}
