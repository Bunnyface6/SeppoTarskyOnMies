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
public class PerformedWork {
    
    String workType;
    int workPerformanceNmbr;
    int numOfHours;
    int discountPer;

    public PerformedWork(String workType, int workPerformanceNmbr, int numOfHours, int discountPer) {
        this.workType = workType;
        this.workPerformanceNmbr = workPerformanceNmbr;
        this.numOfHours = numOfHours;
        this.discountPer = discountPer;
    }

    public String getWorkType() {
        return workType;
    }

    public int getWorkPerformanceNmbr() {
        return workPerformanceNmbr;
    }

    public int getNumOfHours() {
        return numOfHours;
    }

    public int getDiscountPer() {
        return discountPer;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public void setWorkPerformanceNmbr(int workPerformanceNmbr) {
        this.workPerformanceNmbr = workPerformanceNmbr;
    }

    public void setNumOfHours(int numOfHours) {
        this.numOfHours = numOfHours;
    }

    public void setDiscountPer(int discountPer) {
        this.discountPer = discountPer;
    }
}