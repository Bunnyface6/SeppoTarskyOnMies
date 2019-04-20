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
public class WorkPerformance {
    private int nmbr;
    private int assistanceWork;
    private int desingWork;
    private int work;
    private int discountPer;
    private int workSiteNmbr;

    public WorkPerformance(int nmbr, int assistanceWork, int desingWork, int work, int discountPer, int workSiteNmbr) {
        this.nmbr = nmbr;
        this.assistanceWork = assistanceWork;
        this.desingWork = desingWork;
        this.work = work;
        this.discountPer = discountPer;
        this.workSiteNmbr = workSiteNmbr;
    }

    public int getNmbr() {
        return nmbr;
    }

    public void setNmbr(int nmbr) {
        this.nmbr = nmbr;
    }

    public int getAssistanceWork() {
        return assistanceWork;
    }

    public void setAssistanceWork(int assistanceWork) {
        this.assistanceWork = assistanceWork;
    }

    public int getDesingWork() {
        return desingWork;
    }

    public void setDesingWork(int desingWork) {
        this.desingWork = desingWork;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public int getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(int discountPer) {
        this.discountPer = discountPer;
    }

    public int getWorkSiteNmbr() {
        return workSiteNmbr;
    }

    public void setWorkSiteNmbr(int workSiteNmbr) {
        this.workSiteNmbr = workSiteNmbr;
    }
    
    
}
