/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;


import java.sql.*;
import tietokantaharkka.baseClasses.*;
import tietokantaharkka.controllers.*;
/**
 *
 * @author Cassu
 */
public class FindMethods {
    
    Connection con;
    ModelList model;
/* ARR[0] VAIHTOEHDOT:
"Lasku", "Työkohde", "Tarvike", "Yritysasiakas", "Kuluttaja asiakas"
*/
/* ARR[1] VAIHTOEHDOT:
"Tunnus", "Päivämäärä", "Työkohdenumero", "Asiakasnumero", "Kaikki maksamattomat" 
"Tunnus", "Nimi", "Varastotilanne"
"Tunnus", "Asiakas", "Osoite", "Laskuttamaton"
"Tunnus", "Nimi", "Maksamattomia laskuja" 
"Tunnus", "Sukunimi", "Maksamattomia laskuja" 
*/
    
    
    public FindMethods(ModelList moo, Connection con2){
        
        this.model = moo;
        this.con = con2;
        
    } 
    
    public void findInDb(String[] arr){
        
        if(arr[0].equals("Lasku")){
             findLasku(arr);
        }
    }
    
    private void findLasku(String[] arr){
        Invoice inv;
        if(arr[1].equals("Tunnus")){
            
            InvoiceCont cont = new InvoiceCont();
            
            inv = cont.findInvoiceByNmbr(Integer.parseInt(arr[2]), con);
            
            model.add(inv);
            
        }
        
    }
    
}
