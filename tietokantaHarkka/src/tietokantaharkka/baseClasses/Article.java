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
public class Article extends ArticleType{
    private String name;
    private double buyIn;
    private int storage;
    private double salePrice;
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
        return "TARVIKE: " + name + ", Kappaleita varastossa: " + storage + ", Ostohinta: " + buyIn + ", Myyntihinta: " + salePrice;
    }
}
