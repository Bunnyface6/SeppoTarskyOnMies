/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;
import tietokantaharkka.baseClasses.*;

/**
 *
 * @author Cassu
 */
public class testView {
    

    public static void testUI(){
        boolean contProg = true;
        String command;
        Scanner user_input = new Scanner (System.in);
        while(contProg == true){
            
            System.out.println("Anna komento: lisää, poista, etsi tai poistu");
            command = user_input.next();
            if(command.equals("lisaa")){
                addCommand();
            }
            else if(command.equals("poista")){
                removeCommand();
            }/*
            if(command == "etsi"){
                searchCommand();
            }*/
            else if(command.equals("poistu")){
                contProg = false;
            }
            else{
                System.out.println("Virheellinen syöte");
            }
            System.out.println("TÄMÄ?");
            
        }
        user_input.close();
    }
    
    private static void removeCommand(){
        
        int command = 0;
        String para;
        Scanner user_input = new Scanner (System.in);
        System.out.println("Mikä luokka (anna numero):\n1: Article\n2: ArticleType\n3: Client\n4: CompanyClient\n5: Invoice\n6: Location\n7: PrivateClient\n8: WorkSite");
        command = user_input.nextInt();
        System.out.println("Anna tarvittavat syötteet välilyönnein erotettuna (katso classista järjestys)");
        para = user_input.next();
        String[] para3 = para.split(" ");
        int[] para2 = new int[para3.length];
        for(int i = 0; i < para3.length; i++){
           para2[i] = Integer.parseInt(para3[i]);
        }
        
         switch (command){
            case 1: removeArticle(para2[0]);
            break;
            case 2: removeArticleType(para2[0]);
            break;
            case 3: removeClient(para2[0]);
            break;
            case 4: removeCompanyClient(para2[0]);
            break;
            case 5: removeInvoice(para2[0]);
            break;
            case 6: removeLocation(para2[0]);
            break;
            case 7: removePrivateClient(para2[0]);
            break;
            case 8: removeWorkSite(para2[0]);
            break;
        }
    }
    
    private static void addCommand(){
        
        int command = 0;
        String para;
        Scanner user_input = new Scanner (System.in);
        System.out.println("Mikä luokka (anna numero):\n1: Article\n2: ArticleType\n3: Client\n4: CompanyClient\n5: Invoice\n6: Location\n7: PrivateClient\n8: WorkSite");
        command = user_input.nextInt();
        user_input.nextLine();
        System.out.println("Anna tarvittavat syötteet välilyönnein erotettuna (katso classista järjestys)");
        para = user_input.nextLine();
        System.out.println(para);
        String[] para2 = para.split(" ");
        
        switch (command){
            case 1: addArticle(para2);
            break;
            case 2: addArticleType(para2);
            break;
            case 3: addClient(para2);
            break;
            case 4: addCompanyClient(para2);
            break;
            case 5: addInvoice(para2);
            break;
            case 6: addLocation(para2);
            break;
            case 7: addPrivateClient(para2);
            break;
            case 8: addWorkSite(para2);
            break;
        }
    }   
    private static void addArticle(String[] para){
        Article x = new Article(para[0], Double.parseDouble(para[1]), Integer.parseInt(para[2]), Double.parseDouble(para[3]), Integer.parseInt(para[4]), Integer.parseInt(para[5]), para[6], para[7]);
        System.out.println(para[0] + " " + para[1]);
    }
    private static void addArticleType(String[] para){
        ArticleType x = new ArticleType(Integer.parseInt(para[0]), para[1], para[2]);
        System.out.println(para[0] + " " + para[1]);
    }
    private static void addClient(String[] para){
        Client x = new Client(Integer.parseInt(para[0]));
        System.out.println(para[0] + " " + para[1]);
    }
    private static void addCompanyClient(String[] para){
        CompanyClient x = new CompanyClient(para[0], Integer.parseInt(para[1]), Integer.parseInt(para[2]));
        System.out.println(para[0] + " " + para[1]);
    }
    private static void addInvoice(String[] para){
        System.out.println(para[0] + " " + para[1]);
    }
    private static void addLocation(String[] para){
        Location x = new Location(Integer.parseInt(para[0]), para[1], Integer.parseInt(para[2]), para[3], Integer.parseInt(para[4]));
        System.out.println(para[0] + " " + para[1]);
    }
    private static void addPrivateClient(String[] para){
        PrivateClient x = new PrivateClient(para[0], para[1], Integer.parseInt(para[2]));
        System.out.println(para[0] + " " + para[1]);
    }
    private static void addWorkSite(String[] para){
        WorkSite x = new WorkSite(Integer.parseInt(para[0]), Integer.parseInt(para[1]));
        System.out.println(para[0] + " " + para[1]);
    }
    
    private static void removeArticle(int x){
        
        
    
    }
    
    private static void removeArticleType(int x){
        
        
        
    }
    
    private static void removeClient(int x){
        
        
        
    }
    
    private static void removeCompanyClient(int x){
        
        
        
    }
    
    private static void removeInvoice(int x){
        
        
        
    }
    
    private static void removeLocation(int x){
        
        
        
    }
    private static void removePrivateClient(int x){
        
        
        
    }
    
    private static void removeWorkSite(int x){
        
        
        
    }
    
}
