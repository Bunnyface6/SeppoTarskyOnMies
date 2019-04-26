package view;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jipsu
 */
public class UI {
    
    private final String ADDCOMMAND = "1";
    private final String SEARCHCOMMAND = "2";
    //private final String HELPCOMMAND = "ohje";
    private final String NOCOMMAND = "k";
    private final String YESCOMMAND = "e";
    private final String QUITCOMMAND = "3";
    private final String ADDPRIVCLIENTCOMMAND = "1";
    private final String ADDCOMPCLIENTCOMMAND = "2";
    private final String ADDWORKSITECOMMAND = "3";
    private final String ADDWORKPERFORMANCECOMMAND = "4";
    private final String ADDARTICLETOWORSITECOMMAND = "5";
    private final String ADDBACKCOMMAND = "11";
    
    private final String SEARHCLIENTCOMMAND = "1";
    private final String SEARCHWORKSITECOMMAND = "2";
    private final String SEARCHARTICLETCOMMAND = "3";
    private final String SEARHINVOICECOMMAND = "4";
    private final String SEARHBACKCOMMAND = "5";
    
    
    private final String HELOMESSAGE = "Tervetuloa Tmi Sähkötärsky:n laskutusjärjestelmään!";
    
    private final String MAINMENUMESSAGE = "1)Lisää 2)Etsi 3)Lopeta";
    
    private final String ADDMENUMESSAGE = "1)Yksityisasiakas 2)Yritysasiakas 3)Työkohde 4)Työsuoritus työhohteeseen 5)Tarvike työkohteeseen\n"
                                          + "6)Tarvike laskulle 7) Tarviketta varastoon 8)Uusi tarvike varastoon 9)Uusi tarviketyyppi\n"
                                          + "10)Uusi työtyyppi 11)Takaisin";
    
    private final String SEARCHMENUMESSAGE = "1)Asiakas 2)Työkohde 3)Tarvike 4)Lasku 5)Takaisin";
    
    private final String WRONGCOMMANDMESSAGE = "Virheellinen komento.";
    
    private final String QUITCONFIRMMESSAGE = "Haluatko varmasti lopettaa? k)Kyllä e)Ei";
    
    public void run() {
        Scanner commandReader = new Scanner(System.in);
        String userCommand = "";
        String tmp1 = "";
        String tmp2 = "";
        String tmp3 = "";
        String tmp4 = "";
        String tmp5 = "";
        boolean uIRunning = true;
        
        System.out.println(HELOMESSAGE);
        
        while (uIRunning) {
            System.out.println(MAINMENUMESSAGE);
            userCommand = commandReader.nextLine();
            
            if (userCommand.equals(ADDCOMMAND)) {
                System.out.println(ADDMENUMESSAGE);
                userCommand = commandReader.nextLine();
                
                if (userCommand.equals(ADDPRIVCLIENTCOMMAND)) {
                    System.out.print("Etunimi: ");
                    tmp1 = commandReader.nextLine();
                    System.out.print("Sukunimi: ");
                    tmp2 = commandReader.nextLine();
                    System.out.print("Katuosoite: ");
                    tmp3 = commandReader.nextLine();
                    // Postinumero täytyy tsekata.
                    System.out.print("Postinumero: ");
                    tmp4 = commandReader.nextLine();
                    System.out.print("Postitoimipaikka: ");
                    tmp5  = commandReader.nextLine();
                    // Tähän metodi jolla uusi privat asiakas luodaan.
                }
                
                else if (userCommand.equals(ADDCOMPCLIENTCOMMAND)) {
                    System.out.print("Nimi: ");
                    tmp1 = commandReader.nextLine();
                    //YTunnus täytyy tsekata
                    System.out.print("YTunnus: ");
                    tmp2 = commandReader.nextLine();
                    System.out.print("Katuosoite: ");
                    tmp3 = commandReader.nextLine();
                    // Postinumero täytyy tsekata.
                    System.out.print("Postinumero: ");
                    tmp4 = commandReader.nextLine();
                    System.out.print("Postitoimipaikka: ");
                    tmp5  = commandReader.nextLine();
                    // Tähän metodi jolla uusi yritys asiakas luodaan.
                }
                
                else if (userCommand.equals(ADDWORKSITECOMMAND)) {
                    System.out.print("Asiakasnumero: ");
                    tmp1 = commandReader.nextLine();
                    System.out.print("Katuosoite: ");
                    tmp2 = commandReader.nextLine();
                    // Postinumero täytyy tsekata.
                    System.out.print("Postinumero: ");
                    tmp3 = commandReader.nextLine();
                    System.out.print("Postitoimipaikka: ");
                    tmp4  = commandReader.nextLine();
                    // Tähän metodi jolla uusi työkohde luodaan.      
                }
                
                else if (userCommand.equals(ADDWORKPERFORMANCECOMMAND)) {
                    System.out.print("Työkohdenumero: ");
                    tmp1 = commandReader.nextLine();
                    System.out.print("Työtyyppi: ");
                    tmp2 = commandReader.nextLine();
                    System.out.print("Tunnit: ");
                    tmp3 = commandReader.nextLine();
                    System.out.print("Alennus: ");
                    tmp4 = commandReader.nextLine();
                    // Tähän metodi, jolla lisätään työsuoritus. INTit tarkistettvava.
                }
                
                else if (userCommand.equals(ADDARTICLETOWORSITECOMMAND)) {
                   
                }
                
                else if (userCommand.equals(ADDBACKCOMMAND)) {
                   
                }
                
                else {
                     System.out.println(WRONGCOMMANDMESSAGE);
                }
                
            }
            
            else if (userCommand.equals(SEARCHCOMMAND)) {
                System.out.println(SEARCHMENUMESSAGE);
                 userCommand = commandReader.nextLine();
                if (userCommand.equals(SEARHCLIENTCOMMAND)) {
                
                }
                
                else if (userCommand.equals(SEARCHWORKSITECOMMAND)) {
                   
                }
                
                else if (userCommand.equals(SEARCHARTICLETCOMMAND)) {
                   
                }
                
                else if (userCommand.equals(SEARHINVOICECOMMAND)) {
                   
                }
                
                else if (userCommand.equals(SEARHBACKCOMMAND)) {
                   
                }
                
                else {
                     System.out.println(WRONGCOMMANDMESSAGE);
                }
            }
            
            else if (userCommand.equals(QUITCOMMAND)) {
                System.out.println(QUITCONFIRMMESSAGE);
                userCommand = commandReader.nextLine();
                if (userCommand.equals(YESCOMMAND)) {
                    uIRunning = false;
                    commandReader.close();
                }
                if (!userCommand.equals(YESCOMMAND) || !userCommand.equals(NOCOMMAND)) {
                    System.out.println(WRONGCOMMANDMESSAGE);
                }
            }
            
            else {
                System.out.println(WRONGCOMMANDMESSAGE);
            }
        }
    }
}
