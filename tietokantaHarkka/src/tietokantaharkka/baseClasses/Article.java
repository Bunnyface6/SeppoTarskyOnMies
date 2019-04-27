/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.baseClasses;

import java.util.stream.IntStream;

/**
 * Tarvike-luokka.
 * @author Cassu
 */
public class Article extends ArticleType implements CharSequence{
    
     /** Nimi */
    private String name;
    
     /** Sisäänostohinta */
    private double buyIn;
    
     /** Varastotilanne */
    private int storage;
    
     /** Myyntihinta */
    private double salePrice;
    
     /** Tarvikenumero */
    private int nmbr2;

    public Article(String name, double buyIn, int storage, double salePrice, int nmbr, int nmbr2, String unit, String typeName) {
        super(nmbr, unit, typeName);
        this.setName(name);
        this.buyIn = buyIn;
        this.storage = storage;
        this.salePrice = salePrice;
        this.nmbr2 = nmbr2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException{
        if(name != null && name.length() > 0){
            this.name = name;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public double getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(double buyIn) {
        this.buyIn = buyIn;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getNmbr2() {
        return nmbr2;
    }

    public void setNmbr2(int nmbr2) {
        this.nmbr2 = nmbr2;
    }
    
    @Override
    public String toString() {
        return name +";"+ storage +";"+ buyIn +";"+ salePrice +";"+ this.getTypeName()+";"+ this.getUnit();
    }
    
    public String showString(){
        return "TARVIKE: " + name + ", Kappaleita varastossa: " + storage + this.getUnit() + ", Ostohinta: " + buyIn + ", Myyntihinta: " + salePrice + "Kategoria: " + this.getTypeName();
    }
    
    public boolean equals(Article x){ 
       
        if(this.getName().equals(x.getName())){
            return true;
        }
        else
            return false;
        
    }
            @Override
        public CharSequence subSequence(int start, int end) {
            return toString().subSequence(start, end);
        }

    @Override
    public int length() {
        return toString().length();
    }

    @Override
    public char charAt(int index) {
        return toString().charAt(index);
    }

    @Override
    public IntStream chars() {
        return CharSequence.super.chars(); //To change body of generated methods, choose Tools | Templates.
    }
}
