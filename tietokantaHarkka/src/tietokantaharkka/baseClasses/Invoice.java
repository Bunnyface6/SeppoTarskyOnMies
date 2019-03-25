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

    public Invoice(Date finalPayDate, int ivNmbr, Date compDate, Date datePaid, int reminderNmbr) {
        this.finalPayDate = finalPayDate;
        this.ivNmbr = ivNmbr;
        this.compDate = compDate;
        this.datePaid = datePaid;
        this.reminderNmbr = reminderNmbr;
    }

    public Date getFinalPayDate() {
        return finalPayDate;
    }

    public void setFinalPayDate(Date finalPayDate) {
        this.finalPayDate = finalPayDate;
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

    public void setCompDate(Date compDate) {
        this.compDate = compDate;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public int getReminderNmbr() {
        return reminderNmbr;
    }

    public void setReminderNmbr(int reminderNmbr) {
        this.reminderNmbr = reminderNmbr;
    }

}
