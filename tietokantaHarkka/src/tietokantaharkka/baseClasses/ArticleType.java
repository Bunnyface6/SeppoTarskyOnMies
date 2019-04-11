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
public class ArticleType {
    private int nmbr;
    private String unit;
    private String typeName;

    public ArticleType(int nmbr, String unit, String typeName) {
        this.nmbr = nmbr;
        setUnit(unit);
        setTypeName(typeName);
    }

    public int getNmbr() {
        return nmbr;
    }

    public void setNmbr(int nmbr) {
        this.nmbr = nmbr;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) throws IllegalArgumentException{
        if(unit != null && unit.length() > 0){
             this.unit = unit;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        if(unit != null && unit.length() > 0){
            this.typeName = typeName;
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
    
