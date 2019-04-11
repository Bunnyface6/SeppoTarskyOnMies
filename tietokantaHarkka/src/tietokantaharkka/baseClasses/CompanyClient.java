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
public class CompanyClient extends Client{
    private String name;
    private int yIdentifier;

    public CompanyClient(String name, int yIdentifier, int nmbr) {
        super(nmbr);
        this.setName(name);
        this.yIdentifier = yIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if(name != null && name.length() > 0){
            this.name = name;
        }
        else
            throw new IllegalArgumentException();
    }

    public int getyIdentifier() {
        return yIdentifier;
    }

    public void setyIdentifier(int yIdentifier) {
        this.yIdentifier = yIdentifier;
    }
    
    
}
