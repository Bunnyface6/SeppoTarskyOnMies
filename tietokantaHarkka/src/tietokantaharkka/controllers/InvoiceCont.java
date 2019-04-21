/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tietokantaharkka.controllers;
import java.sql.*;
import tietokantaharkka.baseClasses.Invoice;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jipsu
 */
public class InvoiceCont {

    private ArrayList<Invoice> recentInvoices = new ArrayList<Invoice>();
    
    private Invoice lastUsed;
    
    public Invoice createInvoice(Date finalPayDate, int ivNmbr, Date compDate, Date datePaid, int reminderNmbr, int clientNmbr, int workPerformanceNmbr) {
        Invoice x = new Invoice(finalPayDate, ivNmbr, compDate, datePaid, reminderNmbr, clientNmbr, workPerformanceNmbr);
        recentInvoices.add(x);
        lastUsed = x;
        return x;
    }
}