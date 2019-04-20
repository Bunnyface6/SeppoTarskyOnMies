/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.baseClasses;

import java.util.Date;

/**
 *
 * @author Cassu
 */
public class Invoice {
    private Date finalPayDate;
    private int ivNmbr;
    private Date compDate;
    private Date datePaid;
    private int reminderNmbr;
    private int clientNmbr;
    private int workSiteNmbr;

    public Invoice(Date finalPayDate, int ivNmbr, Date compDate, Date datePaid, int reminderNmbr, int clientNmbr, int workSiteNmbr) {
        this.finalPayDate = finalPayDate;
        this.ivNmbr = ivNmbr;
        this.compDate = compDate;
        this.datePaid = datePaid;
        this.reminderNmbr = reminderNmbr;
        this.clientNmbr = clientNmbr;
        this.workSiteNmbr = workSiteNmbr;
    }

    public int getClientNmbr() {
        return clientNmbr;
    }

    public void setClientNmbr(int clientNmbr) {
        this.clientNmbr = clientNmbr;
    }

    public int getWorkSiteNmbr() {
        return workSiteNmbr;
    }

    public void setWorkSiteNmbr(int workSiteNmbr) {
        this.workSiteNmbr = workSiteNmbr;
    }


    public Date getFinalPayDate() {
        
        return finalPayDate;
    }

    public void setFinalPayDate(Date finalPayDate) throws IllegalArgumentException {
        if(finalPayDate != null){
            this.finalPayDate = finalPayDate;
        }
        else
            throw new IllegalArgumentException();
    }

    public int getIvNmbr() {
        return ivNmbr;
    }

    public void setIvNmbr(int ivNmbr) {
        this.ivNmbr = ivNmbr;
    }

    public Date getCompDate() {
        return compDate;
    }

    public void setCompDate(Date compDate) throws IllegalArgumentException{
        if(compDate != null){
            this.compDate = compDate;
        }
        else
            throw new IllegalArgumentException();
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) throws IllegalArgumentException{
        if(datePaid != null)
            this.datePaid = datePaid;
        else
            throw new IllegalArgumentException();
    }

    public int getReminderNmbr() {
        return reminderNmbr;
    }

    public void setReminderNmbr(int reminderNmbr) {
        this.reminderNmbr = reminderNmbr;
    }

}
