/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.baseClasses;

/**
 *
 * @author Jipsu
 */
public class WorkPrice {
    
    String workType;
    double price;

    public WorkPrice(String workType, double price) {
        this.workType = workType;
        this.price = price;
    }

    public String getWorkType() {
        return workType;
    }

    public double getPrice() {
        return price;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
