/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

import helpers.InvoiceGenerator;
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
 * Tapahtuma-luokka.
 * @author Jipsu
 */
public class Transaction {
    
    /** Base-luokkien controllerit. */
    private LocationCont lC;
    private PrivateClientCont pCC;
    private CompanyClientCont cCC;
    private ClientCont cC;
    private WorkSiteCont wSC;
    private WorkPerformanceCont wPC;
    private PerformedWorkCont perfWC;
    private InvoiceCont iC;
    private WorkPriceCont wPrC;
    private ArticleCont aC;
    private ArticleTypeCont aTC;
    private SoldArticleCont sAC;
    private InvoiceGenerator iG;
    
    public Transaction() {
        this.lC = new LocationCont();
        this.pCC = new PrivateClientCont();
        this.cCC = new CompanyClientCont();
        this.wSC = new WorkSiteCont();
        this.wPC = new WorkPerformanceCont();
        this.perfWC = new PerformedWorkCont();
        this.iC = new InvoiceCont();
        this.wPrC = new WorkPriceCont();
        this.aC = new ArticleCont();
        this.aTC = new ArticleTypeCont();
        this.cC = new ClientCont();
        this.sAC = new SoldArticleCont();
        this.iG = new InvoiceGenerator();
    }
    
    /**
     * Asettaa tietokantaan yksityisasikkaan tiedot ja osoitteen. Jos osoite on olemassa
     * uutta ei tehdä vaan käytetään vanhaa.
     * 
     * @param fName etunimi
     * @param lName sukunimi
     * @param address katuosoite
     * @param zipCode postinumero
     * @param city kaupunki
     * @param con yhteys-olio
     * @return totuusarvon tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus aiheuttaa virheen
     */
    public boolean addClient(String fName, String lName, String address, int zipCode, String city, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            Location l = lC.findLocation(address, zipCode, city, con);
            if (l == null) {
                l = new Location(0, address, zipCode, city); 
                int lN = lC.addNewLocation(l, con);
                PrivateClient pC = new PrivateClient(fName, lName, 0, lN);
                pCC.addNewPrivateClient(pC, con);
            }
            else {
               PrivateClient pC2 = new PrivateClient(fName, lName, 0, l.getNmbr());
               pCC.addNewPrivateClient(pC2, con);
            }
            con.commit();
            return true;
        }
        catch (SQLException e) {
            con.rollback();
            return false;
        }
    }
    
    /**
     * Asettaa tietokantaan yritysasikkaan tiedot ja osoitteen. Jos osoite on olemassa
     * uutta ei tehdä vaan käytetään vanhaa.
     * 
     * @param name nimi
     * @param yID yritys-tunnus
     * @param address katuosoite
     * @param zipCode postinumero
     * @param city kaupunki
     * @param con yhteys-olio
     * @return totuusarvon tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus aiheuttaa virheen
     */
    public boolean addClient(String name, int yID, String address, int zipCode, String city, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
            Location l = lC.findLocation(address, zipCode, city, con);
            if (l == null) {
                l = new Location(0, address, zipCode, city);
                int lN = lC.addNewLocation(l, con);
                CompanyClient cC = new CompanyClient(name, yID, 0, lN);
                cCC.addNewCompanyClient(cC, con);
            }
            else {
                CompanyClient cC2 = new CompanyClient(name, yID, 0, l.getNmbr());
                cCC.addNewCompanyClient(cC2, con);
            }
            con.commit();
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            con.rollback();
            return false;
        }
    }
    
    /**
     * Lisää tietokantaan asiakkaalle työkohteen ja osoitteen sille. Ositetta ei lisätä, jos
     * se on jo olemaasa, vaan hyödynnetään vanhaa.
     * 
     * @param clientNmbr asiakasnumero
     * @param address katuosoite
     * @param zipCode postinumero
     * @param city postitoimipaikka
     * @param contractPrice urakkahinta
     * @param con yhteys-olio
     * @return totuusarvon tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus aiheuttaa virheen
     */
    public boolean addWorkSiteToClient(int clientNmbr, String address, int zipCode, String city, double contractPrice, Connection con) throws SQLException {
        try {
            boolean oK = false;
            con.setAutoCommit(false);
            Client x = cC.findClientByNmbr(clientNmbr, con);
            if (x != null) {        
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
                oK = true;
            }
            con.commit();
            return oK;
        }
        catch (SQLException e) {
            con.rollback();
            return false;
        }
    }
    
    /**
     * Lisää asiakkaalle tunnit ja tavarat työkohteeseen sekä luo laskupohjan, jos keskeneräinen sellainen ei osoita työkohteeseen.
     * Muuten tunnit ja tavarat kuuluvat keskeneräiseen laskupohjaan.
     * 
     * @param workSiteNmbrHoursAndDisc taulukko, joka sisältää työkohdenumeron, tehdyt tunnit ja niiden alennukset
     * @param articlesAndDisc lista tarvikkeista, niiden määristä ja alennuksista
     * @param con yhteys-olio
     * @return totuusarvon tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus aihettaa virheen
     */
    public boolean addHoursAndArticles(int[] workSiteNmbrHoursAndDisc, ArrayList<Integer> articlesAndDisc, Connection con) throws SQLException {
        try {
            con.setAutoCommit(false);
           
            ArrayList<WorkPerformance> wP = wPC.findWorkPerformanceByWorkSiteNmbr(workSiteNmbrHoursAndDisc[0], con);
            WorkPerformance p = null;
            Invoice inv;
            PerformedWork pWork1 = null;
            PerformedWork pWork2 = null;
            PerformedWork pWork3 = null;
            WorkSite wS = null;
            boolean invoiceNotCreated = true;
            int key = 0;
            int perfNum = 0;
            int j = 0;
            for (int i = 0; i < wP.size() && invoiceNotCreated; i++) {
                if(!(iC.invoiceIsCreated(wP.get(i).getNmbr(), con))) {
                    p = wP.get(i);
                    invoiceNotCreated = false;
                }
            }
            if (p == null) {
                p = new WorkPerformance(0, workSiteNmbrHoursAndDisc[0]);
                key = wPC.addNewWorkPerformance(p, con);
                pWork1 = new PerformedWork("Työ", key, workSiteNmbrHoursAndDisc[1], workSiteNmbrHoursAndDisc[2]);
                pWork2 = new PerformedWork("Suunnittelutyö", key, workSiteNmbrHoursAndDisc[3], workSiteNmbrHoursAndDisc[4]);
                pWork3 = new PerformedWork("Aputyö", key, workSiteNmbrHoursAndDisc[5], workSiteNmbrHoursAndDisc[6]);
                wS = wSC.findWorkSiteByNmbr(workSiteNmbrHoursAndDisc[0], con);
                inv = new Invoice(null, 0, null, null, 1, 0, wS.getClientNmbr(), key);
                iC.addNewInvoice(inv, con);
                perfNum = key;
            }
            else {
                
                pWork1 = new PerformedWork("Työ", p.getNmbr(), workSiteNmbrHoursAndDisc[1], workSiteNmbrHoursAndDisc[2]);
                pWork2 = new PerformedWork("Suunnittelutyö", p.getNmbr(), workSiteNmbrHoursAndDisc[3], workSiteNmbrHoursAndDisc[4]);
                pWork3 = new PerformedWork("Aputyö", p.getNmbr(), workSiteNmbrHoursAndDisc[5], workSiteNmbrHoursAndDisc[6]);
                perfNum = p.getNmbr();
            }
            
            perfWC.addNewPerformedWork(pWork1, con);
            perfWC.addNewPerformedWork(pWork2, con);
            perfWC.addNewPerformedWork(pWork3, con);
            
            inv = iC.chooseLatest(iC.findInvoiceByWorkPerformanceNmbr(perfNum, con));
            
            while (j < articlesAndDisc.size()) {
                
                SoldArticle sA = new SoldArticle(inv.getIvNmbr(), articlesAndDisc.get(j), articlesAndDisc.get(j + 2), articlesAndDisc.get(j + 1));
                j = j + 3;
                try {
                    sAC.addNewSoldArticle(sA, con);
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());    
                }
            }
            con.commit();
            return true;
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            con.rollback();
            return false;
        }
    }
    
    /**
     * Luo laskun tietokantaa ja asettaa laskulle tiedot tarvikkeista.
     * 
     * @param clientNmbr asiakasnumero
     * @param aList lista tarvikkeista, niiden määristä ja alennuksista.
     * @param today laskun päiväys
     * @param fPDate eräpäivä
     * @param con yhteys-olio
     * @return totuusarvon tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus epäonnistuu
     */
    public boolean addArticlesToInvoice(int clientNmbr, ArrayList<Integer> aList, Date today, Date fPDate, Connection con) throws SQLException {
        int iN;
        int j = 0;
        try {
            con.setAutoCommit(false);
            Invoice inv = new Invoice(fPDate, 0, today, null, 1, 0, clientNmbr, 0);
            iN = iC.addNewInvoice(inv, con);
            while (j < aList.size())  {
                SoldArticle sA = new SoldArticle(iN, aList.get(j), aList.get(j + 2), aList.get(j + 1));
                j = j + 3;
                try {
                    sAC.addNewSoldArticle(sA, con);
                }
                catch (IllegalArgumentException e) {
                   System.out.println(e.getMessage());
                }
            }
            con.commit();
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            con.rollback();
            return false; 
        }
    }
    
    /**
     * Tekee laskusta valmiin(esitykelpoisen) asettamalla sille päiväyksen ja eräpäivän.
     * 
     * @param invNmbr laskunumero
     * @param today laskun päiväys
     * @param fPDate eräpäivä
     * @param con yhteys-olio
     * @return totuusarvo tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus epäonnistuu
     */
    public boolean createInvoice(int invNmbr, Date today, Date fPDate, Connection con) throws SQLException {
        boolean oK = false;
        try {
            con.setAutoCommit(false);
            Invoice i = iC.findInvoiceByNmbr(invNmbr, con);

            if (i != null && i.getFinalPayDate() == null) {
                i.setCompDate(today);
                i.setFinalPayDate(fPDate);
                oK = iC.updateCompDateFinalPayDate(i, con);
            }
            con.commit();
            return oK;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            con.rollback();
            return false; 
        }
    }
    
    /**
     * Luo esityksen laskusta, joka on luotu, mutta ei maksettu.
     * 
     * @param invNumber laskunumero
     * @param con yhteys-olio
     * @return merkkijonoesitys laskusta
     * @throws SQLException jos tapahtuman peruutus epäonnistuu 
     */
    public String printInvoice(int invNumber, Connection con) throws SQLException {
        String invoice = null;
        try {
           con.setAutoCommit(false);
           Invoice i = iC.findInvoiceByNmbr(invNumber, con);
           if (i != null && i.getDatePaid() == null && i.getFinalPayDate() != null) {
               invoice = iG.generateInvoice(i, con);
           }
           con.commit();
        }
        catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        }
        return invoice;
    }
    
    /**
     * Tekee laskusta maksetun asettamalla sille maksupäivän.
     * 
     * @param invNmbr laskunumero
     * @param dPaid maksupäivä
     * @param con yhteys-olio
     * @return totuusarvo tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus epäonnistuu
     */
    public boolean setInvoicePaid(int invNmbr, Date dPaid, Connection con) throws SQLException {
        boolean oK = false;
        try {
            con.setAutoCommit(false);
            Invoice i = iC.findInvoiceByNmbr(invNmbr, con);
            if (i != null && i.getDatePaid() == null && i.getCompDate() != null && i.getFinalPayDate() != null) {
                i.setDatePaid(dPaid);
                oK = iC.updateDatePaid(i, con);
            }
            con.commit();
            return oK;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
    
    /**
     * Luo muistutuksen kaikista maksamattomista laskuista. Parametrina saatua päiväystä verrataan laskujen eräpäiviin.
     * 
     * @param today tämä päivä(laskun päiväys)
     * @param fpDate eräpäivä
     * @param con yhteys-olio
     * @return totuusarvo tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus epäonnistuu
     */    
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
    
    /**
     * Luo toisen muistutuksen kaikista maksamattomista laskuista. Parametrina saatua päiväystä verrataan laskujen eräpäiviin.
     * 
     * @param today tämä päivä(laskun päiväys)
     * @param fpDate eräpäivä
     * @param con yhteys-olio
     * @return totuusarvo tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus epäonnistuu
     */    
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
    
    /**
     * Luo hinta-arvio-merkkijonoesityksen parametreina saatujen tietojen ja tietokannassa olevien töiden 
     * ja tarvikkeiden hintojen ja tietojen perusteella.
     * 
     * @param hours työtunnit tyypeittäin
     * @param articles tarvikkeet määrineen
     * @param con yhteys-olio
     * @return merkkijono esitys hinta-arviosta
     * @throws SQLException 
     */
    public String createAssessmentOfPrices(int[] hours, ArrayList<Integer> articles, Connection con) throws SQLException {
        String assessmentOfPrices = null;
        ArrayList<SoldArticle> soldA = new ArrayList<SoldArticle>();
        int j = 0;
        try {
            con.setAutoCommit(false);
            while (j < articles.size()) {
                SoldArticle sA = new SoldArticle(0, articles.get(j), 0, articles.get(j + 1));
                soldA.add(sA);
                j = j + 2;
            }
            assessmentOfPrices = iG.printEstimate(hours[0], hours[2], hours[1], soldA, con);
            con.commit();
        }
        catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        }
        return assessmentOfPrices;
    }
    
    /**
     * Lisää tarvikkeen parametrien tietojen avulla tarvike-tauluun.
     * 
     * @param name nimi
     * @param buyIn sisaanostohinta
     * @param salePrice myyntihinta
     * @param storage varastotilanne
     * @param type tyyppi
     * @param con yhteys-olio
     * @return totuusarvo tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuman peruutus epäonnistuu
     */    
    public boolean addArticle(String name, double buyIn, double salePrice, int storage, String type, Connection con) throws SQLException {
        boolean oK = false;
        try {
            con.setAutoCommit(false);
            ArticleType x = aTC.findArticleTypeByTypeName(type, con);
            if (x != null) {
                Article a = new Article(name, buyIn, storage, salePrice, x.getNmbr(), 0, x.getUnit(), x.getTypeName());
                aC.addNewArticle(a, con);
                oK = true;
            }
            con.commit();
            return oK;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
    
    /**
     * Päivittää tarvikkeen varastotilannetta tietokannan tarvike-taulussa.
     * 
     * @param articleNmbr tarvikenumero
     * @param newAmount uusi määrä
     * @param con yhteys-olio
     * @return totuusarvo tapahtuman onnistumisesta
     * @throws SQLException jos tapahtuma peruutus epäonnistuu
     */    
    public boolean updateStorage(int articleNmbr, int newAmount, Connection con) throws SQLException {
        boolean oK = false;
        try {
            con.setAutoCommit(false);
            Article x = aC.findArticleByNmbr(articleNmbr, con);
            if (x != null && newAmount > 0) {
                x.setStorage(newAmount);
                aC.updateArticle(x, con);
                oK = true;
            }
            con.commit();
            return oK;
        }
        catch (SQLException e) {
            con.rollback();
            return false; 
        }
    }
    
    /**
     * Vertailee vanhoja ja tulevia uusia tarvikkeita ja jakaa ne päivitettäviin, uusiin tuleviin ja poistettaviin. 
     * 
     * @param newList uudet tarvikkeet
     * @param updateList tietokantaan päivitettävät tarvikkeet
     * @param con yhteys
     * @return tietokannasta poistettvat tarvikkeet
     * @throws SQLException jos metodin aikana tapahtuu virhe
     */
    public ArrayList<Article> findAllOldArticles(ArrayList<Article> newList, ArrayList<Article> updateList, Connection con) throws SQLException{

        ArrayList<Article> oldList = aC.findAllArcticles(con);
        Article rmv = oldList.get(0);
        boolean found = false;
        
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
    
    /**
     * Kirjoittaa vanhat tarvikkeet tiedostoon.
     * 
     * @param list lista tiedostoon kirjoitettavista tarvikkeista
     * @return jos kirjoituksessa tapahtuu virhe
     */
    public boolean printOldArticles(ArrayList<Article> list){
        try{
               ZoneId zoneId = ZoneId.systemDefault() ;
               LocalDate today = LocalDate.now( zoneId ) ;
               String day = today.toString()+".txt" ;
               Path out = Paths.get(day);
               if(Files.notExists(out)) {
                   Files.createFile(out);
               }
               Files.write(out, list, Charset.defaultCharset());
               return true;
        }
        catch(Exception e){
           
           System.out.println("Print failure: " + e.getMessage());
           return false; 
        }
        
    }
    
    /**
     * Hakee tiedostosta uudet tarvikkeet
     * 
     * @param path polku jossa uusien tarvikkeiden tiedosto on.
     * @return uusien tarvikkeiden lista
     */
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
    
    /**
     * fullUpdateFromFile-apumetodi
     * @param updateList päivitettävä lista
     * @param con yhteys
     * @return totuusarvon metodin suorittamisesta
     * @throws SQLException 
     */
    public boolean updateArticlesFromFile(ArrayList<Article> updateList, Connection con) throws SQLException{

        for(Article art : updateList){
            aC.updateArticle(art, con);
        }
        return true;
    }
    
    /**
     * fullUpdateFromFile-apumetodi
     * @param newList uusi tarvikelista
     * @param con yhteys
     * @return totuusarvon metodin suorittamisesta
     * @throws SQLException 
     */
    public boolean newArticlesFromFile(ArrayList<Article> newList, Connection con) throws SQLException{

        for(Article art : newList){
            aC.addNewArticle(art, con);
        }
        return true;

    }
    
    /**
     * Vaihtaa tarvike-taulun tarvikkeet vastaamaan tekstitiedostossa olevaa tarvike listaa.
     * Vanhat tarvikkeet kirjoitetaan history-tiedostoon.
     * 
     * @param nPath tiedoston nimi
     * @param con yhteys
     * @return totuusarvon onnistumisesta
     * @throws SQLException jos tapahtuman peruutus ei onnistu.
     */
    public boolean fullUpdateFromFile(String nPath, Connection con) throws SQLException{
        try{
            con.setAutoCommit(false);
            ArrayList<Article> newList = readArticlesFromFile(nPath);
            ArrayList<Article> updateList = new ArrayList();
            ArrayList<Article> oldList = findAllOldArticles(newList, updateList, con);
            printOldArticles(oldList);
            updateArticlesFromFile(updateList, con);
            newArticlesFromFile(updateList, con);
            con.commit();
            return true;
        }
        catch(SQLException e){
            con.rollback();
            return false;
        }
    }
    
    /**
     * Palauttaa tarviketyypit(nimet) tietokannasta.
     * 
     * @param con yhteys-olio
     * @return merkkijonoesityksen tarviketyypeistä.
     * @throws SQLException jos tapahtuma aihettaa virheen
     */
    public String getArticleTypes(Connection con) throws SQLException {
        String aTypes = "";
        try {
            con.setAutoCommit(false);
            ArrayList<ArticleType> a = aTC.findArticleTypes(con);
            if(!a.isEmpty()) {
                aTypes = "Tarviketyypit:";
                for (int i = 0; i < a.size(); i++) {
                    ArticleType aT = a.get(i);
                    aTypes = aTypes + " " + aT.getTypeName();
                }
            }
            con.commit();
        }
        catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        }
        return aTypes;
    }
    
    /**
     * Hakee tietokannasta ravikkeet ja tekee niistä merkkijono esitykset.
     * 
     * @param con yhteys-olio
     * @return lista tarvikkeista merkkijonoesityksinä
     * @throws SQLException jos tapahtuma aiheuttaa virheen.
     */
    public ArrayList<String> getAllArticles(Connection con) throws SQLException {
        ArrayList<String> aList = new ArrayList<String>();
        try {
            con.setAutoCommit(false);
            ArrayList<Article> a = aC.findAllArcticles(con);
            if(!a.isEmpty()) {
                for (int i = 0; i < a.size(); i++) {
                    Article article = a.get(i);
                    String s = article.showString2();
                    aList.add(s);
                }
            }
            con.commit();
        }    
        catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        }
        return aList;        
    }
    
    /**
     * Hakee tietokannasta tietoja työkohteestä ja palauttaa merkkijonoesitykset niistä.
     * 
     * @param clientNmbr asiakasnumero
     * @param con yhteys-olio
     * @return listan merkkijonoesityksiä työkohdetiedoista
     * @throws SQLException jos tapahtuma aiheuttaa virheen
     */
    public ArrayList<String> getWorkSiteInfo(int clientNmbr, Connection con) throws SQLException {
        ArrayList<String> wSInfo = new ArrayList<String>();
        try {
            con.setAutoCommit(false);
            ArrayList<WorkSite> w = wSC.findWorkSiteByClientNmbr(clientNmbr, con);
            if (!w.isEmpty()) {
                for (int i = 0; i < w.size(); i++) {
                    WorkSite wS = w.get(i);
                    Location l = lC.findLocationByNmbr(wS.getLocationNmbr(), con);
                    String s = wS.showString() + " " + l.showString();
                    wSInfo.add(s);
                }
            }
            con.commit();
        }
        catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        }
        return wSInfo;
    }
    
    /**
     * Hakee tietokannasta tietoja yksityisasiakkaista ja palauttaa merkkijonoesitykset niistä.
     * 
     * @param fName etunimi
     * @param lName sukunimi
     * @param con yhteys-olio
     * @return listan merkkijonoesityksiä yksityisasiakkaista
     * @throws SQLException jos tapahtuma aiheuttaa virheen
     */
    public ArrayList<String> getClientInfo(String fName, String lName, Connection con) throws SQLException {
        ArrayList<String> cInfo = new ArrayList<String>();
        try {
            con.setAutoCommit(false);
            ArrayList<PrivateClient> c = pCC.findPrivateClientByName(lName, fName, con);
            if (!c.isEmpty()) {
                for (int i = 0; i < c.size(); i++) {
                    PrivateClient pC = c.get(i);
                    Location l = lC.findLocationByNmbr(pC.getLocationNmbr(), con);
                    String s = pC.showString() + " " + l.showString();
                    cInfo.add(s);
                }
            }
            con.commit();
        }
        catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        }
        return cInfo;
    }
    
    /**
     * Hakee tietokannasta tietoja yritysasiakkaista ja palauttaa merkkijonoesitykset niistä.
     * 
     * @param name nimi
     * @param con yhteys-olio
     * @return listan merkkijonoesityksiä yritysasiakkaista
     * @throws SQLException jos tapahtuma aiheuttaa virheen
     */
    public ArrayList<String> getClientInfo(String name, Connection con) throws SQLException {
        ArrayList<String> cInfo = new ArrayList<String>();
        try {
            con.setAutoCommit(false);
            ArrayList<CompanyClient> c = cCC.findCompanyClientByName(name, con);
            if (!c.isEmpty()) {
                for (int i = 0; i < c.size(); i++) {
                    CompanyClient cC = c.get(i);
                    Location l = lC.findLocationByNmbr(cC.getLocationNmbr(), con);
                    String s = cC.showString() + " " + l.showString();
                    cInfo.add(s);
                }
            }
            con.commit();
        }
        catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        }
        return cInfo;
    }
    
    /**
     * Hakee tietokannasta laskut ja palauttaa merkkijonoesityksen niistä.
     * 
     * @param con yhteys-olio
     * @return merkkijonoesitys laskuista
     * @throws SQLException jos tapahtuma aihettaa virheen
     */
    public ArrayList<String> getInvoiceInfos(Connection con) throws SQLException {
        ArrayList<String> iInfo = new ArrayList<String>();
        try {
            con.setAutoCommit(false);
            ArrayList<Invoice> invs = iC.findInvoices(con);
            for (int i = 0; i < invs.size(); i++) {
                Invoice inv = invs.get(i);
                String s = inv.toString();
                iInfo.add(s);
            }
            con.commit();
        }
        catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        }
        return iInfo;
    }
}
