package view;
import dBConnection.dBConnection;
import java.sql.SQLException;
import java.util.Scanner;
import transaction.Transaction;

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
    
    Transaction transaction;
    dBConnection con;
    
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
    private final String SEARCHARTICLECOMMAND = "3";
    private final String SEARHINVOICECOMMAND = "4";
    private final String SEARHBACKCOMMAND = "5";
    
    private final String HELOMESSAGE = "Tervetuloa Tmi Sähkötärsky:n laskutusjärjestelmään!";
    private final String MAINMENUMESSAGE = "1)Lisää 2)Etsi 3)Lopeta";
    private final String ADDMENUMESSAGE = "1)Yksityisasiakas 2)Yritysasiakas 3)Työkohde 4)Työsuoritus työhohteeseen 5)Tarvike työkohteeseen\n"
                                          + "6)Tarvike laskulle 7) Tarviketta varastoon 8)Uusi tarvike varastoon 9)Uusi tarviketyyppi\n"
                                          + "10)Uusi työtyyppi 11)Takaisin";
    
    private final String SEARCHMENUMESSAGE = "1)Asiakas 2)Työkohde 3)Tarvike 4)Lasku 5)Takaisin";
    private final String WRONGCOMMANDMESSAGE = "Virheellinen komento.";
    private final String NOTNUMBERMESSAGE = "Syöte virheellinen. Anna numero-muotoinen syöte ilman välilyöntejä.";
    private final String EMPTYINPUTMESSAGE = "Tyhjä syöte ei kelpaa.";
    private final String QUITCONFIRMMESSAGE = "Haluatko varmasti lopettaa? k)Kyllä e)Ei";
    
    public void run() {
        transaction = new Transaction();
        con = new dBConnection();
        con.createConnection();
        Scanner commandReader = new Scanner(System.in);
        String userCommand = "";
        String tmp1 = "";
        String tmp2 = "";
        String tmp3 = "";
        String tmp4 = "";
        String tmp5 = "";
        int tmp6;
        int tmp7;
        double tmp8;
        boolean uIRunning = true;
        boolean inAddMenu;
        boolean inSearchMenu;
        
        System.out.println(HELOMESSAGE);
        
        while (uIRunning) {
            inAddMenu = true;
            inSearchMenu = true;
            System.out.println(MAINMENUMESSAGE);
            userCommand = commandReader.nextLine();
            
            
            if (userCommand.equals(ADDCOMMAND)) {
                while (inAddMenu) {    
                    System.out.println(ADDMENUMESSAGE);
                    userCommand = commandReader.nextLine();
                
                    if (userCommand.equals(ADDPRIVCLIENTCOMMAND)) {
                        try {
                            System.out.print("Etunimi: ");
                            tmp1 = commandReader.nextLine();
                            System.out.print("Sukunimi: ");
                            tmp2 = commandReader.nextLine();
                            System.out.print("Katuosoite: ");
                            tmp3 = commandReader.nextLine();
                            System.out.print("Postinumero: ");
                            tmp4 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp4);
                            System.out.print("Postitoimipaikka: ");
                            tmp5  = commandReader.nextLine();
                            transaction.addClient(tmp1, tmp2, tmp3, tmp6, tmp5, con.getConnection());
                        }
                        catch (NumberFormatException e) {
                            System.out.println(NOTNUMBERMESSAGE);
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        catch (NullPointerException e) {
                            System.out.println(EMPTYINPUTMESSAGE);
                        }
                    }
                
                    else if (userCommand.equals(ADDCOMPCLIENTCOMMAND)) {
                        try {
                            System.out.print("Nimi: ");
                            tmp1 = commandReader.nextLine();
                            System.out.print("YTunnus: ");
                            tmp2 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp2);
                            System.out.print("Katuosoite: ");
                            tmp3 = commandReader.nextLine();
                            System.out.print("Postinumero: ");
                            tmp4 = commandReader.nextLine();
                            tmp7 = Integer.parseInt(tmp4);
                            System.out.print("Postitoimipaikka: ");
                            tmp5  = commandReader.nextLine();
                            transaction.addClient(tmp1, tmp6, tmp3, tmp7, tmp5, con.getConnection());
                        }
                        catch (NumberFormatException e) {
                            System.out.println(NOTNUMBERMESSAGE);
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        catch (NullPointerException e) {
                            System.out.println(EMPTYINPUTMESSAGE);
                        }
                    }
                
                    else if (userCommand.equals(ADDWORKSITECOMMAND)) {
                        try {
                            System.out.print("Asiakasnumero: ");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            System.out.print("Katuosoite: ");
                            tmp2 = commandReader.nextLine();
                            System.out.print("Postinumero: ");
                            tmp3 = commandReader.nextLine();
                            tmp7 = Integer.parseInt(tmp3);
                            System.out.print("Postitoimipaikka: ");
                            tmp4  = commandReader.nextLine();
                            System.out.print("Urakkahinta(0, jos kohde ei ole urakka): ");
                            tmp5  = commandReader.nextLine();
                            tmp8 = Double.parseDouble(tmp5);
                            transaction.addWorkSiteToClient(tmp6, tmp2, tmp7, tmp4, tmp8, con.getConnection());
                        }
                        catch (NumberFormatException e) {
                            System.out.println(NOTNUMBERMESSAGE);
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        catch (NullPointerException e) {
                            System.out.println(EMPTYINPUTMESSAGE);
                        }
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
                        inAddMenu = false;
                    }
                  
                    else {
                        System.out.println(WRONGCOMMANDMESSAGE);
                    }
                
                }
            }
                        
            else if (userCommand.equals(SEARCHCOMMAND)) {
                while (inSearchMenu) {     
                    System.out.println(SEARCHMENUMESSAGE);
                    userCommand = commandReader.nextLine();
                    
                    if (userCommand.equals(SEARHCLIENTCOMMAND)) {
                        System.out.println("Asiakasnumero:");
                        tmp1 = commandReader.nextLine();
                
                    }
                
                    else if (userCommand.equals(SEARCHWORKSITECOMMAND)) {
                        System.out.println("Työkohdenumero:");
                        tmp1 = commandReader.nextLine();
                    }
                
                    else if (userCommand.equals(SEARCHARTICLECOMMAND)) {
                   
                    }
                
                    else if (userCommand.equals(SEARHINVOICECOMMAND)) {
                   
                    }
                
                    else if (userCommand.equals(SEARHBACKCOMMAND)) {
                        inSearchMenu = false;
                    }
                
                    else {
                        System.out.println(WRONGCOMMANDMESSAGE);
                    }
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
