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

    public PrivateClient(String fName, String lName, int nmbr) {
        super(nmbr);
        this.fName = fName;
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

}
