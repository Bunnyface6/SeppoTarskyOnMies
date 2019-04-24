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
    private int nmbr;
    private double contractPrice;

    public WorkSite(int locationNmbr, int clientNmbr, int nmbr, double contractPrice) {
        this.locationNmbr = locationNmbr;
        this.clientNmbr = clientNmbr;
        this.nmbr = nmbr;
        this.contractPrice = contractPrice;
    }

    public double getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(double contractPrice) {
        this.contractPrice = contractPrice;
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

    public int getClientNmbr() {
        return clientNmbr;
    }

    public void setClientNmbr(int clientNmbr) {
        this.clientNmbr = clientNmbr;
    }

    @Override
    public String toString() {
        return "TYÖKOHDE: " + "Tunnus: " + nmbr + ", Asiakastunnus: " + clientNmbr + ", Kohdetunnus: " + locationNmbr;
    }
    
    
}
