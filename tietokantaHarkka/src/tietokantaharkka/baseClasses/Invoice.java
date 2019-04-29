/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.baseClasses;

import java.text.SimpleDateFormat;
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
    private int reminderOfNmbr;
    private int clientNmbr;
    private int workPerformanceNmbr;
    private int nmbrOfInvoices;

    public Invoice(Date finalPayDate, int ivNmbr, Date compDate, Date datePaid, int nmbrOfInvoices, int reminderOfNmbr, int clientNmbr, int workPerformanceNmbr) {
        this.finalPayDate = finalPayDate;
        this.ivNmbr = ivNmbr;
        this.compDate = compDate;
        this.datePaid = datePaid;
        this.reminderOfNmbr = reminderOfNmbr;
        this.clientNmbr = clientNmbr;
        this.workPerformanceNmbr = workPerformanceNmbr;
        this.nmbrOfInvoices = nmbrOfInvoices;
    }

    public int getClientNmbr() {
        return clientNmbr;
    }

    public void setClientNmbr(int clientNmbr) {
        this.clientNmbr = clientNmbr;
    }

    public int getWorkPerformanceNmbr() {
        return workPerformanceNmbr;
    }

    public void setWorkPerformanceNmbr(int workPerformanceNmbr) {
        this.workPerformanceNmbr = workPerformanceNmbr;
    }

    public Date getFinalPayDate() {
        return finalPayDate;
    }

    public void setFinalPayDate(Date finalPayDate){
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

    public void setCompDate(Date compDate){
        this.compDate = compDate;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid){
            this.datePaid = datePaid;
    }

    public int getReminderOfNmbr() {
        return reminderOfNmbr;
    }

    public void setReminderOfNmbr(int reminderOfNmbr) {
        this.reminderOfNmbr = reminderOfNmbr;
    }
    
    public int getNmbrOfInvoices() {
        return nmbrOfInvoices;
    }

    public void setNmbrOfInvoices(int nmbrOfInvoices) {
        this.nmbrOfInvoices = nmbrOfInvoices;
    }

    @Override
    public String toString() {
        SimpleDateFormat sDF = new SimpleDateFormat("dd-MM-yyyy");
        if(finalPayDate != null && datePaid == null)
            return "LASKU (MAKSAMATON): " + "Laskun numero " + ivNmbr + ", Asiakasnumero: " + clientNmbr + ", Laskun päiväys: " + sDF.format(compDate) + ", Viimeinen maksupäivä: " + sDF.format(finalPayDate);
        else if(finalPayDate != null && datePaid != null)
            return "LASKU (MAKSETTU): " + "Laskun numero " + ivNmbr + ", Asiakasnumero: " + clientNmbr + ", Laskun päiväys: " + sDF.format(compDate) + ", Maksttu päivämäärä: " + sDF.format(datePaid);
        else
            return "LASKU (EI LÄHETETTY): " + "Laskun numero " + ivNmbr + ", Asiakasnumero: " + clientNmbr;
    }
    
    
}
