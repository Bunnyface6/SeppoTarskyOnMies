/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import tietokantaharkka.baseClasses.*;
import tietokantaharkka.controllers.*;
import java.sql.*;
/**
 *
 * @author Cassu
 */
public class InvoiceGenerator {
    
    public String generateInvoice(Invoice invoice, Connection con){
        
        String finString;
        String partOne;
        String partTwo;
        String partThree;
        
        Client client;
        CompanyClient cC;
        PrivateClient pC;
        Location clientLoc;
        SoldArticle sA[];
        Article art;
        ArticleType artType;
        WorkSite wS;
        Location wSLoc;
        WorkPerformance wP[];
        
        SoldArticleCont soldCont = new SoldArticleCont();
        ClientCont clientCont = new ClientCont();
        CompanyClientCont compCont = new CompanyClientCont();
        PrivateClientCont privCont = new PrivateClientCont();
        ArticleCont artCont = new ArticleCont();
        locationCont locCont = new locationCont();
        ArticleTypeCont artTypeCont = new ArticleTypeCont();
        WorkSiteCont workSiteCont = new WorkSiteCont();
        WorkPerformanceCont  wPCont = new WorkPerformanceCont();
        

        
       try{
            client = clientCont.findClient(invoice.getClientNmbr());
           
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
            
            sA[] = soldCont.findSoldArticle(invoice.getIvNmbr(), con);
            
            art = artCont.findArticle(sA.getArticleNmbr(), con);
            
            artType = artTypeCont.findArticleType(art.getNmbr2(), con);
            
            wS = workSiteCont.findWorkSite(invoice.getWorkSiteNmbr(), con);
            
            wSLoc = clientLoc = locCont.findLocationByNmbr(wS.getLocationNmbr(), con);
            
            wP[] = wPcont.findWorkPerformance(wS.getNmbr(), con);
            
       }
       catch(SQLException e){
           System.out.println("Caught exception " + e.toString());

       }
       int hours[] = calculateHours(wP);
       // MUISTA LISÄTÄ TUNTITAKSA SEKÄ TUNTIHINTA YHTEENSÄ
       partTwo =
               "\n\nTEHDYT TUNNIT:" +
               "\n\tAputunnit: " + hours[0] +
               "\n\tSuunnittelutunnit: " + hours[1] +
               "\n\tNormaalit työtunnit: " + hours[2]
               ;
       
       partThree = "\n\nLASKUTETTAVAT TYÄTARVIKKEET:";
       
       double totalPrice = 0;
       
       for(SoldArticle x : sA){
           try{
            art = artCont.findArticleByNmbr(x.getArticleNmbr(), con);

            artType = artTypeCont.findArticleTypeByNmbr(art.getNmbr2(), con);
            
            double price = art.getSalePrice() * x.getNmbrOfSold();
            
            partThree = partThree + "\n\t" + art.getName() + "\t" + x.getNmbrOfSold() + " " + artType.getUnit() + "\t" + art.getSalePrice() + " €" + "\tYht. " + price;
            totalPrice += price;
            }
            catch(SQLException e){
                System.out.println("SQL VIRHE " + e.toString());
                break;
            }
       }
       
       partThree = partThree + "\n\n\t\t\tYhteensä: " + totalPrice + " €";
       
       finString = partOne + partTwo + partThree;

       return finString;
    }
    
    
    
    public int[] calculateHours(WorkPerformance[] wP){
        
        int x[] = {0, 0, 0};
        
        for(WorkPerformance i : wP ){
            
            x[0] =+ i.getAssistanceWork();
            x[1] =+ i.getDesingWork();
            x[2] =+ i.getWork();
            
        }
        
        return x;
    }
}
