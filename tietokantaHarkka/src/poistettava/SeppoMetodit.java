/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poistettava;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import tietokantaharkka.baseClasses.*;
import tietokantaharkka.controllers.*;

/**
 *
 * @author Jipsu
 */
public class SeppoMetodit {
    
    private LocationCont lC;
    private PrivateClientCont pCC;
    private CompanyClientCont cCC;
    private WorkSiteCont wSC;
    private WorkPerformanceCont wPC;
    private PerformedWorkCont pVC;
    private InvoiceCont iC;
    private WorkPriceCont wPrC;
    private ArticleCont aC;
    private ArticleTypeCont aTC;

    public SeppoMetodit() {
        this.lC = new LocationCont();
        this.pCC = new PrivateClientCont();
        this.cCC = new CompanyClientCont();
        this.wSC = new WorkSiteCont();
        this.wPC = new WorkPerformanceCont();
        this.pVC = new PerformedWorkCont();
        this.iC = new InvoiceCont();
        this.wPrC = new WorkPriceCont();
        this.aC = new ArticleCont();
        this.aTC = new ArticleTypeCont();
    }
    
    public boolean addClient(String fName, String lName, String address, int zipCode, String city, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            Location l = new Location(0, address, zipCode, city);
            int lN = lC.addNewLocation(l, con);
            PrivateClient pC = new PrivateClient(fName, lName, 0, lN);
            pCC.addNewPrivateClient(pC, con);
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false;
        }
    }
    
    public boolean addClient(String name, int yID, String address, int zipCode, String city, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            Location l = new Location(0, address, zipCode, city);
            int lN = lC.addNewLocation(l, con);
            CompanyClient cC = new CompanyClient(name, yID, 0, lN);
            cCC.addNewCompanyClient(cC, con);
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false;
        }
    }
    
    public boolean addWorkSiteToClient(Client x, String address, int zipCode, String city, double contractPrice, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            Location l = lC.findLocation(address, zipCode, city, con);
            if (l != null) {
               WorkSite wS = new WorkSite(l.getNmbr(), x.getNmbr(), 0, contractPrice);
               wSC.addNewWorkSite(wS, con);
            }
            else {
                l = new Location(0, address, zipCode, city);
                int lN = lC.addNewLocation(l, con);
                WorkSite wS2 = new WorkSite(lN, x.getNmbr(), 0, contractPrice);
                wSC.addNewWorkSite(wS2, con);
            }
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false;
        }
    }
    
    public boolean addHourstoWorkPerformaceofWorkSite(WorkSite x, String wType, int nOHours, int disc, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            WorkPerformance w = wPC.findWorkPerformanceByWorkSiteNmbr(x.getNmbr(), con);
            if (w != null) {
                PerformedWork p = new PerformedWork(wType, w.getNmbr(), nOHours, disc);
                pVC.addNewPerformedWork(p, con);
            }
            else {
                w = new WorkPerformance(0, x.getNmbr());
                wPC.addNewWorkPerformance(w, con);
                w = wPC.findWorkPerformanceByWorkSiteNmbr(w.getWorkSiteNmbr(), con);
                PerformedWork p2 = new PerformedWork(wType, w.getNmbr(), nOHours, disc);
                pVC.addNewPerformedWork(p2, con);
                Invoice i = new Invoice(null, 0, null, null, 1, 0, x.getClientNmbr(), w.getNmbr());
                iC.addNewInvoice(i, con);
            }
            con.commit();
            return true;
        }
        catch(SQLException e) {
            con.rollback();
            return false;
        }
    }
    // Kesken
    public boolean addArticleToWorksite(WorkSite x, Article a, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            WorkPerformance w
            con.commit();
            return true;
        }
        catch(SQLException e) {
            con.rollback();
            return false;
        }
    }
    
    public boolean createReminderOfUnpaidInvoices(Date today, Date fpDate, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            ArrayList<Invoice> unpaidInvoices = iC.findUnpaidInvoices(1, con);
            for (int i = 0; i < unpaidInvoices.size(); i++) {
                Invoice inv = unpaidInvoices.get(i);
                if (inv.getFinalPayDate().before(today)) {
                    Invoice reminder = new Invoice(fpDate, 0, today, null, 2, inv.getIvNmbr(), inv.getClientNmbr(), inv.getWorkPerformanceNmbr());
                    iC.addNewInvoice(reminder, con);
                    inv.setDatePaid(today);
                    iC.updateDatePaid(inv, con);
                }
            }
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
    
    public boolean createSecondReminderOfUnpaidInvoice(Date today, Date fpDate, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            ArrayList<Invoice> unpaidInvoices = iC.findUnpaidInvoices(2, con);
            for (int i = 0; i < unpaidInvoices.size(); i++) {
                Invoice inv = unpaidInvoices.get(i);
                if (inv.getFinalPayDate().before(today)) {
                    Invoice reminder = new Invoice(fpDate, 0, today, null, 3, inv.getIvNmbr(), inv.getClientNmbr(), inv.getWorkPerformanceNmbr());
                    iC.addNewInvoice(reminder, con);
                    inv.setDatePaid(today);
                    iC.updateDatePaid(inv, con);
                }
            }
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
    
    public boolean addWorkTypeAndPrice(String type, double price, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            WorkPrice w = new WorkPrice(type, price);
            wPrC.addNewWorkPrice(w, con);
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
    
    public boolean addArticle(String name, double buyIn, int storage, double salePrice, int typeNmbr, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            Article a = new Article(name, buyIn, storage, salePrice, typeNmbr, 0, null, null);
            aC.addNewArticle(a, con);
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
    
    public boolean addArticleType(String type, String unit, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            ArticleType aT = new ArticleType(0, unit, type);
            aTC.addNewArticleType(aT, con);
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
    
    public boolean updateStorage(Article x, int newAmount, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            x.setStorage(newAmount);
            aC.updateArticle(x, con);
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
}
