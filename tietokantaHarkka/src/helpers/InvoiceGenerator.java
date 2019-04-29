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
        String partOne = "";
        String partTwo = "";
        String partThree = "";
        double totalPrice = 0;
        double contractPrice = 0;
        boolean agreement = false;
        
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
           
           contractPrice = wS.getContractPrice();
           
           if(contractPrice != 0){
               agreement = true;
           }
           
           if(hours != null){
               double total = 0;
               StringBuilder sb = new StringBuilder("TEHDYT TUNNIT:\n\n");
               for(Storage stor : hours){
                   
                   sb.append(stor.name + " ");
                   sb.append(" Tuntimäärä " + stor.hours);
                   if(!agreement)
                       sb.append(" Tuntiveloitus " + stor.hPrice );
                   if(stor.reduction != 0)
                       sb.append(" Alennus " + stor.reduction + "%");
                   if(!agreement){
                       sb.append(" Kokonaishinta " + stor.total);
                       total = total + stor.total;
                   }
                   sb.append("\n");
               }
               if(!agreement){
                    sb.append("\nKOKONAISHINTA TYÖSTÄ: " + total);
               }
               totalPrice = totalPrice + total;
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
                totalPrice = totalPrice + total;
                partThree = partThree + "\n\n\t\t\tTyötarvikkeet yhteensä: " + total + " €";
           }
           
           if(agreement)
               totalPrice = contractPrice;
           partThree = partThree + "\n\n\nYHTEENSÄ: " + totalPrice + "€";
           finString = partOne + partTwo + partThree;

           return finString;
       }
       catch(SQLException e){
           System.out.println("Caught exception " + e.toString());
           return null;
       }
       catch(NullPointerException e){
           System.out.println(e.getMessage());
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
        catch(NullPointerException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
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

            sb.append("TYÖ YHTEENSÄ: " + total + "€");

            if(sA != null){
                    sb.append("\n\nLASKUTETTAVAT TYÖTARVIKKEET:");
                    for(SoldArticle x : sA){

                         art = artCont.findArticleByNmbr(x.getArticleNmbr(), con);

                         artType = artTypeCont.findArticleTypeByNmbr(art.getNmbr2(), con);

                         double price = art.getSalePrice() * x.getNmbrOfSold();

                         sb.append("\n\t" + art.getName() + "\t" + x.getNmbrOfSold() + " " + artType.getUnit() + "\t" + art.getSalePrice() + " €" + "\tYht. " + price);
                         total += price;

                    }
                    sb.append("\n\n\t\t\tYhteensä: " + total + " €");
               }
               return sb.toString();
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
            this.name = x;
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