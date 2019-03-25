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
public class WorkSite {
    private int locationNmbr;
    private int clientNmbr;

    public WorkSite(int locationNmbr, int clientNmbr) {
        this.locationNmbr = locationNmbr;
        this.clientNmbr = clientNmbr;
    }

    public int getLocationNmbr() {
        return locationNmbr;
    }

    public void setLocationNmbr(int locationNmbr) {
        this.locationNmbr = locationNmbr;
    }

    public int getClientNmbr() {
        return clientNmbr;
    }

    public void setClientNmbr(int clientNmbr) {
        this.clientNmbr = clientNmbr;
    }
    
    
}
