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
    private int workSiteNmbr;

    public WorkPerformance(int nmbr, int workSiteNmbr) {
        this.nmbr = nmbr;
        this.workSiteNmbr = workSiteNmbr;
    }

    public int getNmbr() {
        return nmbr;
    }

    public void setNmbr(int nmbr) {
        this.nmbr = nmbr;
    }
    
    public int getWorkSiteNmbr() {
        return workSiteNmbr;
    }

    public void setWorkSiteNmbr(int workSiteNmbr) {
        this.workSiteNmbr = workSiteNmbr;
    }
}
