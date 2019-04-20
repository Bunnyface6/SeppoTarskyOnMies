/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.baseClasses;

/**
 *
 * @author Cassu
 */
public class Client {
    private int nmbr;
    private int locationNmbr;

    public Client(int nmbr, int locationNmbr) {
        this.nmbr = nmbr;
        this.locationNmbr = locationNmbr;
    }

    public int getNmbr() {
        return nmbr;
    }

    public void setNmbr(int nmbr) {
        this.nmbr = nmbr;
    }

    public int getLocationNmbr() {
        return locationNmbr;
    }

    public void setLocationNmbr(int locationNmbr) {
        this.locationNmbr = locationNmbr;
    }

 
}
