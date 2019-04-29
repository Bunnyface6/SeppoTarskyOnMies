/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import static java.nio.file.Files.list;
import java.nio.file.*;
import static java.rmi.Naming.list;
import tietokantaharkka.baseClasses.*;
import tietokantaharkka.controllers.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
/**
 * Laskun-luoja
 * @author Cassu
 */
public class InvoiceGenerator {
    
    /**
     * Luo stringin annetusta invoisesta joka näyttää jotakuinkin laskulta
     *
     * @param invoice Lasku joka halutaan tulostaa
     * @param con Yhteys
     * @return String joka näyttää laskulta
     */
    public String generateInvoice(Invoice invoice, Connection con){
        
        String finString;
        String partOne = "";
        String partTwo = "";
        String partThree = "";
        double totalPrice = 0;
        double contractPrice = 0;
        boolean agreement = false;
        DecimalFormat df2 = new DecimalFormat("#.##");
        String name;
        
        Client client;
        CompanyClient cC;
        PrivateClient pC;
        Location clientLoc;
        ArrayList<SoldArticle> sA;
        Article art;
        ArticleType artType;
        WorkSite wS;
        Location wSLoc;
        WorkPerformance wP;
        SeppoCompany sC;
        
        SoldArticleCont soldCont = new SoldArticleCont();
        ClientCont clientCont = new ClientCont();
        CompanyClientCont compCont = new CompanyClientCont();
        PrivateClientCont privCont = new PrivateClientCont();
        ArticleCont artCont = new ArticleCont();
        LocationCont locCont = new LocationCont();
        ArticleTypeCont artTypeCont = new ArticleTypeCont();
        WorkSiteCont workSiteCont = new WorkSiteCont();
        WorkPerformanceCont wPCont = new WorkPerformanceCont();
        SeppoCompanyCont sCC = new SeppoCompanyCont();
        InvoiceCont iVC = new InvoiceCont();

        
       try{
            client = clientCont.findClientByNmbr(invoice.getClientNmbr(), con);

            if(compCont.findCompanyClient(client.getNmbr(), con) != null){
                cC = compCont.findCompanyClient(client.getNmbr(), con);
                partOne = 
                    "LASKU \n\n" +
                    cC.getName() + "\n" +
                    cC.getyIdentifier()
                ;
                name = cC.getName();
            }
            else{
                pC = privCont.findPrivateClient(client.getNmbr(), con);
                partOne =
                    "LASKU \n\n" +
                    pC.getfName() + " " + pC.getlName();
                name = pC.getfName()+ pC.getlName();
            }
            clientLoc = locCont.findLocationByNmbr(client.getLocationNmbr(), con);
            if(invoice.getNmbrOfInvoices() > 1){
                Invoice invoice2 = iVC.findInvoiceByNmbr(invoice.getReminderOfNmbr(), con);
                sA = soldCont.findSoldArticlesOfInvoice(invoice2.getIvNmbr(), con);
            }
            else{
                sA = soldCont.findSoldArticlesOfInvoice(invoice.getIvNmbr(), con);
            }
            wP = wPCont.findWorkPerformanceByNmbr(invoice.getWorkPerformanceNmbr(), con);

            wS = workSiteCont.findWorkSiteByNmbr(wP.getWorkSiteNmbr(), con);

            wSLoc = locCont.findLocationByNmbr(wS.getLocationNmbr(), con);
            
            sC = sCC.seppoInfo(con);
            
            partOne = partOne + "\n" + clientLoc.toString() + "\n\nTYÖMAA:\n" + wSLoc.toString();
            
           ArrayList<Storage> hours = calculate(wP, sC, con);
           
           contractPrice = wS.getContractPrice();
           if(contractPrice != 0){
               agreement = true;
           }

           if(hours != null){
               double total = 0;
               double total2 = 0;
               StringBuilder sb = new StringBuilder("\n\nTEHDYT TUNNIT:\n\n");
               for(Storage stor : hours){
                   stor.calcVat();
                   sb.append("\t " + stor.name.toUpperCase() + ": ");
                   sb.append(" Tuntimäärä " + stor.hours);
                   if(!agreement)
                       sb.append(" Tuntiveloitus " + stor.hPrice );
                   if(stor.reduction != 0)
                       sb.append(" Alennus " + stor.reduction + "%");
                   if(!agreement){
                       sb.append(" Kokonaishinta " + df2.format(stor.total) + "€");
                       total = (double)total + stor.total;
                       sb.append(" josta veroton osuus " + df2.format(stor.vat) + "€");
                       total2 += stor.vat;
                   }
                   sb.append("\n");
               }
               if(!agreement){
                    sb.append("\nKOKONAISHINTA TYÖSTÄ: " + df2.format(total) + " €\n" + "Josta kotitalousvähennyskelpoinen osuus on: " + df2.format(total2) + " €");
               }
               totalPrice = totalPrice + total;
               partTwo = sb.toString();
           }
           
           if(sA != null){
               double total = 0;
                partThree = "\n\nLASKUTETTAVAT TYÖTARVIKKEET:";
                
                for(SoldArticle x : sA){
                    System.out.println(x.getArticleNmbr());
                     art = artCont.findArticleByNmbr(x.getArticleNmbr(), con);
                     
                        if(art != null){
                     
                     
                        System.out.println(art.getNmbr());
                        artType = artTypeCont.findArticleTypeByNmbr(art.getNmbr(), con);
                            if(artType != null){
                        
                                double price = art.getSalePrice() * x.getNmbrOfSold();

                                partThree = partThree + "\n\t" + art.getName() + "\t" + x.getNmbrOfSold() + " " + artType.getUnit() + "\t" + art.getSalePrice() + " €" + "\tYht. " + price + " €";
                                                                total += price;
                                partThree = partThree + " josta veroton osuus: ";
                                if(art.getTypeName() != "Kirja"){
                                    price = price / (1 + ((double)sC.getArticleVAT() / 100));
                                }
                                else{
                                    price = price / (1 + ((double)sC.getBookVAT() / 100));
                                }
                            }
                        }

                }
               
                totalPrice = totalPrice + total;
                partThree = partThree + "\n\n\t\t\tTyötarvikkeet yhteensä: " + total + " €";
           }
           if(agreement)
               totalPrice = contractPrice;
           if(invoice.getNmbrOfInvoices() == 2){
               partThree = partThree + "\n\n Lisäksi koska kyseessä muistuslasku laskusta " + invoice.getReminderOfNmbr() +" lisätään laskuun "+ sC.getConsumerFee() + " € laskutuslisä";
               totalPrice += sC.getConsumerFee();
           }
           if(invoice.getNmbrOfInvoices() == 3){
               partThree = partThree + "\n\n Lisäksi koska kyseessä karhuamislasku laskusta " + invoice.getReminderOfNmbr() + " lisätään laskuun "+ sC.getConsumerFee() + " € laskutuslisä sekä korko " + sC.getConsumerInterest() + "%";
               totalPrice += sC.getConsumerFee();
               totalPrice = totalPrice * (1 + (sC.getConsumerInterest() / 100));
           }
           partThree = partThree + "\n\n\nYHTEENSÄ: " + df2.format(totalPrice) + " €";

           finString = partOne + partTwo + partThree;
           printInvoice(finString, name, "invoices");
           return finString;
       }
       catch(SQLException e){
           System.out.println("Caught exception " + e.toString());
           return null;
       }
       catch(NullPointerException e){
           e.printStackTrace();
           return null;
       }
    }

    /**
     * Laskee tunnit yhteen sekä niiden euromäärät
     *
     * @param wP Työsuoritus joka laskulle kuuluu
     * @param sC Sepon yhtiön tiedot
     * @param con Yhteys
     * @return Palauttaa ArrayListin joka on täytetty storage-olioilla
     * @throws SQLException
     */
    public ArrayList<Storage> calculate(WorkPerformance wP, SeppoCompany sC, Connection con) throws SQLException{
        
        ArrayList<Storage> rtn = new ArrayList<Storage>();
        PerformedWorkCont pWC = new PerformedWorkCont();
        WorkPriceCont wPC = new WorkPriceCont();
        ArrayList<PerformedWork> pW;
        pW = pWC.findPerformedWorkByWorkPerformanceNmbr(wP.getNmbr(), con);
        if(pW != null){

            for(PerformedWork x : pW){

                Storage y = new Storage(x.getWorkType(), sC);
                if(rtn.contains(y)){
                    y = rtn.remove(rtn.indexOf(y));
                }
                y.hours = y.hours + x.getNumOfHours();
                y.reduction = x.getDiscountPer();
                rtn.add(y);
            }

            for(Storage x : rtn){
                WorkPrice y = wPC.findWorkPriceByWorkType(x.name, con);
                x.hPrice = y.getPrice();
                x.calculateTotal();
            }
            return rtn;
        }
        else
            return null;

    }
    
    /**
     * Palauttaa string-olion joka näyttää hinta-arviolta
     * 
     * @param planWork Suunnittelutuntien määrä
     * @param work Tavallisen työn määrä
     * @param helpWork Aputyön määrä
     * @param sA Myytävät tarvikkeet arviossa
     * @param con Yhteys
     * @return Hinta-arvion näköinen string
     */
    public String printEstimate(int planWork, int work, int helpWork, ArrayList<SoldArticle> sA, Connection con){
        double total = 0;
        
        ArticleCont artCont = new ArticleCont();
        ArticleTypeCont artTypeCont = new ArticleTypeCont();
        WorkPriceCont wPC = new WorkPriceCont();
        
        Article art;
        ArticleType artType;
        
        StringBuilder sb = new StringBuilder();

        try{
            WorkPrice planWorkPrice = wPC.findWorkPriceByWorkType("Suunnittelutyö", con);
            WorkPrice workPrice = wPC.findWorkPriceByWorkType("Työ", con);
            WorkPrice helpWorkPrice = wPC.findWorkPriceByWorkType("Aputyö", con);


            sb.append("HINTA-ARVIO:\n");
            sb.append("\n\t");
            sb.append("SUUNNITTELUTYÖ: " + planWork + " Tuntia; Hinta: " + (planWork * planWorkPrice.getPrice()) + "€");
            total = planWork * planWorkPrice.getPrice();
            sb.append("\n\t");
            sb.append("TYÖ: " + work + " Tuntia; Hinta: " + (work * workPrice.getPrice()) + "€");
            total = work * workPrice.getPrice();
            sb.append("\n\t");
            sb.append("SUUNNITTELUTYÖ: " + helpWork + " Tuntia; Hinta: " + (helpWork * helpWorkPrice.getPrice()) + "€");
            total = helpWork * helpWorkPrice.getPrice();

            sb.append("\n\nTYÖ YHTEENSÄ: " + total + "€");

            if(sA != null){
                    sb.append("\n\nLASKUTETTAVAT TYÖTARVIKKEET:");
                    for(SoldArticle x : sA){

                         art = artCont.findArticleByNmbr(x.getArticleNmbr(), con);

                         artType = artTypeCont.findArticleTypeByNmbr(art.getNmbr(), con);

                         double price = art.getSalePrice() * x.getNmbrOfSold();

                         sb.append("\n\t" + art.getName() + "\t" + x.getNmbrOfSold() + " " + artType.getUnit() + "\t" + art.getSalePrice() + " €" + "\tYht. " + price);
                         total += price;

                    }
                    sb.append("\n\n\t\t\tYhteensä: " + total + " €");
               }
               printInvoice(sb.toString(), "Arvio", "estimates");
               return sb.toString();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Tulostaa annetun string-olion annettuun kansioon.
     * @param x Mitä halutaan tulostaa
     * @param name Tiedoston nimi
     * @param location Polku jonne luodaan
     * @return palauttaa totuusarvon onnistuiko
     */
    public boolean printInvoice(String x, String name, String location){
        
        try{   
               String separator = System.getProperty("line.separator");
               x.replace("\\n", separator);
               ZoneId zoneId = ZoneId.systemDefault() ;
               LocalDate today = LocalDate.now( zoneId ) ;
               String day = location + "/" + name + today.toString()+".txt" ;
               Path out = Paths.get(day);
               if(Files.notExists(out)) {
                   Files.createFile(out);
               }
               Files.write(out, x.getBytes());
               return true;
        }
        catch(Exception e){
           
           System.out.println("Print failure: " + e.getMessage());
           return false; 
        }
        
    }
    /*
    * Luokka joka tallentaa tunneista kertyvät summat
    */
    class Storage{
        String name;
        int hours;
        int reduction;
        double hPrice;
        double total;
        double vat;
        SeppoCompany sC;
        public Storage(String x, SeppoCompany y){
            this.name = x;
            this.hours = 0;
            this.reduction = 0;
            this.hPrice = 0;
            this.total = 0;
            this.sC = y;
        }
        public boolean equals(Storage x){
            if(name.equals(x.name) && this.reduction == x.reduction)
                return true;
            else
                return false;
        }
        public void calculateTotal(){
            this.total = (double)hours * hPrice / (1 + ((double)reduction / 100));
        }
        public void calcVat(){
            
            this.vat = total / (1 + ((double)sC.getWorkVAT() / 100));
            
        }
    }
    
    
}