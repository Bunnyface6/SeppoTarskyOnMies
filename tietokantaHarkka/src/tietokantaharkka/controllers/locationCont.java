/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.Location;

/**
 *
 * @author Cassu
 */
public class locationCont {
    
    // Ei varmuutta tarvitaanko ArrayListia, tehty varalle.
    private ArrayList<Location> recentLocations = new ArrayList<Location>();
    private Location lastUsed;
    
    public Location createLocation(int nmbr,String address,int postNmbr,String city,int clientNmbr){
       Location x = new Location(nmbr, address, postNmbr, city, clientNmbr);
       recentLocations.add(x);
       lastUsed = x;
       return x;
        //TODO Luo uusi locaatio
    }
    
    public void addNewLocation(int clientNmbr, Location x){
        //TODO Lisää uusi locaatio databaseen
    }
    
    public Location findLocation(int nmbr){
        //TODO Hae db.stä sekä palauta
    }
    
    public Location removeLocation(Location x){
        
    }
    
}
