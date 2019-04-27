/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import tietokantaharkka.baseClasses.*;
import tietokantaharkka.controllers.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author Cassu
 */
public class InvoiceGenerator {
    
    public String generateInvoice(Invoice invoice, Connection con){
        
        String finString;
        String partOne;
        String partTwo = "";
        String partThree = "";
        
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
        
        SoldArticleCont soldCont = new SoldArticleCont();
        ClientCont clientCont = new ClientCont();
        CompanyClientCont compCont = new CompanyClientCont();
        PrivateClientCont privCont = new PrivateClientCont();
        ArticleCont artCont = new ArticleCont();
        LocationCont locCont = new LocationCont();
        ArticleTypeCont artTypeCont = new ArticleTypeCont();
        WorkSiteCont workSiteCont = new WorkSiteCont();
        WorkPerformanceCont wPCont = new WorkPerformanceCont();
        

        
       try{
                client = clientCont.findClientByNmbr(invoice.getClientNmbr(), con);

                if(compCont.findCompanyClient(client.getNmbr(), con) != null){
                    cC = compCont.findCompanyClient(client.getNmbr(), con);
                    partOne = 
                        "LASKU \n\n" +
                        cC.getName() + "\n" +
                        cC.getyIdentifier()
                    ;
                }
                else{
                    pC = privCont.findPrivateClient(client.getNmbr(), con);
                    partOne =
                        "LASKU \n\n" +
                        pC.getfName() + " " + pC.getlName();
                }

                clientLoc = locCont.findLocationByNmbr(client.getLocationNmbr(), con);

                sA = soldCont.findSoldArticlesOfInvoice(invoice.getIvNmbr(), con);

                wP = wPCont.findWorkPerformanceByNmbr(invoice.getWorkPerformanceNmbr(), con);

                wS = workSiteCont.findWorkSiteByNmbr(wP.getWorkSiteNmbr(), con);

                wSLoc = locCont.findLocationByNmbr(wS.getLocationNmbr(), con);

           ArrayList<Storage> hours = calculate(wP, con);
           if(hours != null){
               double total = 0;
               StringBuilder sb = new StringBuilder("TEHDYT TUNNIT:\n\n");
               for(Storage stor : hours){
                   
                   sb.append(stor.name + " ");
                   sb.append(" Tuntimäärä " + stor.hours);
                   sb.append(" Tuntiveloitus " + stor.hPrice );
                   sb.append(" Alennus " + stor.reduction + "%");
                   sb.append(" Kokonaishinta " + stor.total);
                   total = total + stor.total;
                   sb.append("\n");
               }
               sb.append("\nKOKONAISHINTA TYÖSTÄ: " + total);
               partTwo = sb.toString();
           }
           if(sA != null){
               double total = 0;
                partThree = "\n\nLASKUTETTAVAT TYÖTARVIKKEET:";
                for(SoldArticle x : sA){
                    
                     art = artCont.findArticleByNmbr(x.getArticleNmbr(), con);

                     artType = artTypeCont.findArticleTypeByNmbr(art.getNmbr2(), con);

                     double price = art.getSalePrice() * x.getNmbrOfSold();

                     partThree = partThree + "\n\t" + art.getName() + "\t" + x.getNmbrOfSold() + " " + artType.getUnit() + "\t" + art.getSalePrice() + " €" + "\tYht. " + price;
                     total += price;

                }
                partThree = partThree + "\n\n\t\t\tYhteensä: " + total + " €";
           }
           finString = partOne + partTwo + partThree;

           return finString;
       }
       catch(SQLException e){
           System.out.println("Caught exception " + e.toString());
           return null;
       }
    }
    
    public ArrayList<Storage> calculate(WorkPerformance wP, Connection con){

        ArrayList<Storage> rtn = new ArrayList<Storage>();
        PerformedWorkCont pWC = new PerformedWorkCont();
        WorkPriceCont wPC = new WorkPriceCont();
        ArrayList<PerformedWork> pW;
        try{
            pW = pWC.findPerformedWorkByWorkPerformanceNmbr(wP.getNmbr(), con);
            if(pW != null){
                for(PerformedWork x : pW){
                    Storage y = new Storage(x.getWorkType());
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
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    class Storage{
        String name;
        int hours;
        int reduction;
        double hPrice;
        double total;
        public Storage(String x){
            this.hours = 0;
            this.reduction = 0;
            this.hPrice = 0;
            this.total = 0;
        }
        public boolean equals(Storage x){
            if(name.equals(x.name) && this.reduction == x.reduction)
                return true;
            else
                return false;
        }
        public void calculateTotal(){
            this.total = hours * hPrice * (reduction / 100);
        }
    }    
}

