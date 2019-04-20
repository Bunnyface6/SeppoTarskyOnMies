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
public class Location {
    private int nmbr;
    private String address;
    private int postNmbr;
    private String city;

    public Location(int nmbr, String address, int postNmbr, String city, int clientNmbr) {
        this.nmbr = nmbr;
        this.address = address;
        this.postNmbr = postNmbr;
        this.city = city;
    }

    public int getNmbr() {
        return nmbr;
    }

    public void setNmbr(int nmbr) {
        this.nmbr = nmbr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws IllegalArgumentException {
        if(address != null)
            this.address = address;
        else
            throw new IllegalArgumentException();
    }

    public int getPostNmbr() {
        return postNmbr;
    }

    public void setPostNmbr(int postNmbr) {
        this.postNmbr = postNmbr;
    }

    public String getCity() {
        
        return city;
    }

    public void setCity(String city) throws IllegalArgumentException {
        if(city != null)
            this.city = city;
        else
            throw new IllegalArgumentException();
    }
    
}
