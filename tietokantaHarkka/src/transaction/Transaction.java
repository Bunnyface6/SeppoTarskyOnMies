/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.*;
import java.time.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.stream.Stream;
import tietokantaharkka.baseClasses.*;
import tietokantaharkka.controllers.*;
/**
 *
 * @author Jipsu
 */
public class Transaction {
    
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

    public Transaction() {
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
    
    public ArrayList<Article> findAllOldArticles(ArrayList<Article> newList, ArrayList<Article> updateList, Connection con) throws SQLException{
        try{
            con.setAutoCommit(false);
            ArrayList<Article> oldList = aC.findAllArcticles(con);
            con.commit();
            Article rmv;
            boolean found = false;
            //SAATTAA OLLA TOIMIMATON, TESTAA!
            for(Article oldA: oldList){
                if(found)
                    updateList.add(rmv);
                    newList.remove(rmv);
                    oldList.remove(rmv);
                found = false;
                for(Article newA: newList){
                    
                    if(oldA.equals(newA)){
                        
                        found = true;
                        break;
                    }
                }
                if(found)
                    rmv = oldA;
            }
            
            return oldList;
        }
        catch(SQLException e){
            con.rollback();
            return null;
        }
        
    }
    
    public boolean printOldArticles(String path, ArrayList<Article> list){
        try{
               ZoneId zoneId = ZoneId.systemDefault() ;
               LocalDate today = LocalDate.now( zoneId ) ;
               String day = "/history/" + today.toString()+".txt" ;
               Path out = Paths.get(day);
               Files.write(out, list, Charset.defaultCharset());
               return true;
        }
        catch(Exception e){
           
           System.out.println("PRINTING FAILED");
           return false; 
        }
        
    }
    
    public ArrayList<Article> readArticlesFromFile(String path){
        ArrayList<Article> newList = new ArrayList();
        try (Stream<String> stream = Files.lines( Paths.get(path), StandardCharsets.UTF_8))
        {  
            stream.forEach((s) ->{
             
                String[] data = s.split(";");
                Article art = new Article(data[0], Double.parseDouble(data[2]), Integer.parseInt(data[1]), Double.parseDouble(data[3]), 0, 0,data[5], data[4]);
                newList.add(art);
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return newList;
    }
    
    public boolean updateArticlesFromFile(ArrayList<Article> updateList, Connection con) throws SQLException{
        try{
            con.setAutoCommit(false);
            for(Article art : updateList){
                aC.updateArticle(art, con);
            }
            con.commit();
            return true;
        }
        catch(SQLException e){
            
            con.rollback();
            return false;
        }
    }
    
    public boolean newArticlesFromFile(ArrayList<Article> newList, Connection con) throws SQLException{
        try{
            con.setAutoCommit(false);
            for(Article art : newList){
                aC.addNewArticle(art, con);
            }
            con.commit();
            return true;
        }
        catch(SQLException e){
            
            con.rollback();
            return false;
        }
    }
    
    //LIIKUTA ALLA OLEVA METODIKASA JONNEKKIN
    
    public boolean fullUpdateFromFile(String nPath, String oPath, Connection con){
        try{
            ArrayList<Article> newList = readArticlesFromFile(nPath);
            ArrayList<Article> updateList = new ArrayList();
            ArrayList<Article> oldList = findAllOldArticles(newList, updateList, con);
            printOldArticles(oPath, oldList);
            updateArticlesFromFile(updateList, con);
            newArticlesFromFile(updateList, con);
            return true;
        }
        catch(SQLException e){
            System.out.println("PAHASTI PIELEEN");
            return false;
        }
        
    }
}
