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
public class PrivateClient extends Client{
    private String fName;
    private String lName;

    public PrivateClient(String fName, String lName, int nmbr, int locationNmbr) {
        super(nmbr, locationNmbr);
        this.fName = fName;
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) throws IllegalArgumentException {
        if(fName != null && fName.length() > 0)
            this.fName = fName;
        else
            throw new IllegalArgumentException();
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Override
    public String toString() {
        return "KULUTTAJA-ASIAKAS: " + "Asiakastunnus: " + this.getNmbr() + "Etunimi: " + fName + ", Sukunimi: " + lName;
    }
    
    public String showString() {
        return "Asiakasnumero: " + this.getNmbr() + " Nimi: " + fName + " " + lName;
    }
}
