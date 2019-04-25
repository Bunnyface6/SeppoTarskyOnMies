/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.sql.*;
import tietokantaharkka.baseClasses.*;
import tietokantaharkka.controllers.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Cassu
 */
public class FindMethods {
    
    Connection con;
    ModelList model;
/* ARR[0] VAIHTOEHDOT:
"Lasku", "Työkohde", "Tarvike", "Yritysasiakas", "Kuluttaja asiakas"
*/
/* ARR[1] VAIHTOEHDOT:
"Tunnus", "Päivämäärä", "Työkohdenumero", "Asiakasnumero", "Kaikki maksamattomat" 
"Tunnus", "Nimi", "Varastotilanne"
"Tunnus", "Asiakas", "Osoite", "Laskuttamaton"
"Tunnus", "Nimi", "Maksamattomia laskuja" 
"Tunnus", "Sukunimi", "Maksamattomia laskuja" 
*/
    
    
    public FindMethods(ModelList moo, Connection con2){
        
        this.model = moo;
        this.con = con2;
        
    } 
    
    public void findInDb(String[] arr){
        
        if(arr[0].equals("Lasku")){
             findLasku(arr);
        }
        else if(arr[0].equals("Työkohde")){
             findTyokohde(arr);
        }
        else if(arr[0].equals("Tarvike")){
             findTarvike(arr);
        }
        else if(arr[0].equals("Yritysasiakas")){
             findYritysasiakas(arr);
        }
        else if(arr[0].equals("Kuluttaja-asiakas")){
             findKuluttajaAsiakas(arr);
        }
    }
    
    private void findLasku(String[] arr){
        Invoice inv;
        ArrayList<Invoice> invAL = new ArrayList<Invoice>();
        if(arr[1].equals("Tunnus")){
            
            InvoiceCont cont = new InvoiceCont();
            
            inv = cont.findInvoiceByNmbr(Integer.parseInt(arr[2]), con);
            
            model.add(inv);
            
        }
        else if(arr[1].equals("Päivämäärä")){
            
            InvoiceCont cont = new InvoiceCont();
            
            DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
            Date result = df.parse(arr[2]);
            inv = cont.findInvoiceByDate(result, con);
            
            model.add(inv);
            
        }
        else if(arr[1].equals("Työkohdenumero")){
            
            
            invAL = findInvoiceByWorkSiteNumber(con, Integer.parseInt(arr[2]));
            
            model.add(invAL);
            
        }
        else if(arr[1].equals("Asiakasnumero")){
            
            InvoiceCont cont = new InvoiceCont();
            
            inv = cont.findInvoiceByClientNmbr(Integer.parseInt(arr[2]), con);
            
            model.add(inv);
            
        }
        else if(arr[1].equals("Kaikki maksamattomat")){
            
            InvoiceCont cont = new InvoiceCont();
            
            invAL = cont.findUnpaidInvoices(Integer.parseInt(arr[2]), con);
            
            model.add(invAL);
            
        }
        else if(arr[1].equals("Lähettämättömät laskut")){
            
            InvoiceCont cont = new InvoiceCont();
            
            DateFormat dfe = new SimpleDateFormat("dd-MM-YYYY");
            Date results = dfe.parse(arr[2]);
            
            invAL = cont.findInvoiceByFinalPayDate(results, con);
            
            model.add(invAL);
            
        }
        
    }
    
    private void findTarvike(String[] arr){
        Article a;
        ArrayList<Article> aAL = new ArrayList<Article>();

        if(arr[1].equals("Tunnus")){
            
            ArticleCont cont = new ArticleCont();
            
            a = cont.findArticleByNmbr(Integer.parseInt(arr[2]), con);
            
            model.add(a);
            
        }
        else if(arr[1].equals("Nimi")){
            
            ArticleCont cont = new ArticleCont();
            
            a = cont.findArticleByName(arr[2], con);
            
            model.add(a);
            
        }
        else if(arr[1].equals("Varastotilanne")){
            
            ArticleCont cont = new ArticleCont();
            
            aAL = cont.findArticleByStorage(Integer.parseInt(arr[2]), con);
            
            model.add(aAL);
            
        }
    }
    
    private void findKuluttajaAsiakas(String[] arr){
        PrivateClient pC;
        ArrayList<PrivateClient> pCAL = new ArrayList<PrivateClient>();
        if(arr[1].equals("Tunnus")){
            
            PrivateClientCont cont = new PrivateClientCont();
            
            pC = cont.findPrivateClient(Integer.parseInt(arr[2]), con);
            
            model.add(pC);
            
        }
        else if(arr[1].equals("Sukunimi")){
            
            PrivateClientCont cont = new PrivateClientCont();
            
            pCAL = cont.findPrivateClientByLName(arr[2], con);
            
            model.add(pCAL);
            
        }
        //Ei valmis
        else if(arr[1].equals("Maksamattomia laskuja")){
            
            PrivateClientCont cont = new PrivateClientCont();
            
            pCAL = cont.findPrivateClientByUnpaidInvoice(arr[2], con);
            
            model.add(pC);
            
        }
    }
    
    private void findYritysasiakas(String[] arr){
        CompanyClient cC;
        ArrayList<CompanyClient> cCAL = new ArrayList<CompanyClient>();
        if(arr[1].equals("Tunnus")){
            
            CompanyClientCont cont = new CompanyClientCont();
            
            cC = cont.findCompanyClient(Integer.parseInt(arr[2]), con);
            
            model.add(cC);
            
        }
        else if(arr[1].equals("Nimi")){
            
            CompanyClientCont cont = new CompanyClientCont();
            
            cCAL = cont.findCompanyClientByName(arr[2], con);
            
            model.add(cCAL);
            
        }
        //Ei valmis
        else if(arr[1].equals("Maksamattomia laskuja")){
            
            CompanyClientCont cont = new CompanyClientCont();
            
            cCAL = cont.findCompanyClientByUnpaidInvoices(arr[2], con);
            
            model.add(cCAL);
            
        }
    }
    
    private void findTyokohde(String[] arr){
        WorkSite Ws;
        ArrayList<WorkSite> wSAL = new ArrayList<WorkSite>();
        if(arr[1].equals("Tunnus")){
            
            WorkSiteCont cont = new WorkSiteCont();
            
            wS = cont.findWorkSiteByNmbr(Integer.parseInt(arr[2]), con);
            
            model.add(wS);
            
        }
        else if(arr[1].equals("Asiakas")){
            
            WorkSiteCont cont = new WorkSiteCont();
            
            wSAL = cont.findWorkSiteByClientNmbr(Integer.parseInt(arr[2]), con);
            
            model.add(wSAL);
            
        }
        
        else if(arr[1].equals("Osoite")){
            
            WorkSiteCont cont = new WorkSiteCont();
            
            wSAL = cont.findWorkSiteByAddress(arr[2], con);
            
            model.add(wSAL);
            
        }
    }
    //
    private ArrayList<Invoice> findInvoiceByWorkSiteNumber(Connection coni, int i){
        ArrayList<WorkPerformance> wPAL = new ArrayList<WorkPerformance>();
        WorkPerformance wP;
        WorkPerformanceCont wPC = new WorkPerformanceCont();
        Invoice in;
        InvoiceCont iC = new InvoiceCont();
        ArrayList<Invoice> iAL = new ArrayList<Invoice>();
        
        wPAL = wPC.findWorkPerformanceByWorkSiteNmbr(i, coni);
        Iterator<WorkPerformance> iter = wPAL.iterator();
        while (iter.hasNext()) {
            in = iC.findInvoiceByWorkPerformanceNmbr(iter.next().getNmbr(), coni);
            iAL.add(in);          
        }
        return iAL;
        
        
    }
    
    public ArrayList<ArticleType> findArticleTypes(Connection con) throws SQLException {
        ArrayList<ArticleType> a = null;
        try {
            con.setAutoCommit(false);
            ArticleTypeCont aTC = new ArticleTypeCont();
            a = aTC.findArticleTypes(con);
            con.commit();
            return a;
        }
        catch (SQLException e) {
            con.rollback();
            return a; 
        }
    }
    
}
