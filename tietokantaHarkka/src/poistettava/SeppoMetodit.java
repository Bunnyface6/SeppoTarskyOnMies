/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poistettava;

import java.sql.*;
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

    public SeppoMetodit() {
        this.lC = new LocationCont();
        this.pCC = new PrivateClientCont();
        this.cCC = new CompanyClientCont();
        this.wSC = new WorkSiteCont();
        this.wPC = new WorkPerformanceCont();
        this.pVC = new PerformedWorkCont();
        this.iC = new InvoiceCont();
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
}
