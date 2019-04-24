/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.baseClasses;

import java.util.Date;

/**
 *
 * @author Jipsu
 */
public class DatesOfInvoice{
    
    Date date;
    Date finalPayDate;

    public DatesOfInvoice(Date date, Date finalPayDate) {
        this.date = date;
        this.finalPayDate = finalPayDate;
    }

    public Date getDate() {
        return date;
    }

    public Date getFinalPayDate() {
        return finalPayDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFinalPayDate(Date finalPayDate) {
        this.finalPayDate = finalPayDate;
    }
}
