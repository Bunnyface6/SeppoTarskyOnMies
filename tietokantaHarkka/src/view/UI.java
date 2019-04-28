package view;
import dBConnection.dBConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private final String INVOICINGCOMMAND = "3";
    private final String NOCOMMAND = "e";
    private final String YESCOMMAND = "k";
    private final String ARTLISTCOMMAND = "t";
    private final String QUITCOMMAND = "4";
    private final String ADDPRIVCLIENTCOMMAND = "1";
    private final String ADDCOMPCLIENTCOMMAND = "2";
    private final String ADDWORKSITECOMMAND = "3";
    private final String ADDHOURSANDARTICLESCOMMAND = "4";
    private final String ADDARTICLETOINVOICECOMMAND = "5";
    private final String ADDARTICLETOSTORAGECOMMAND = "6";
    private final String ADDNEWARTICLECOMMAND = "7";
    private final String ADDARTICLELISTCOMMAND = "8";
    private final String ADDBACKCOMMAND = "9";
    private final String SEARCHPRIVCLIENTCOMMAND = "1";
    private final String SEARCHCOMPLIENTCOMMAND = "2";
    private final String SEARCHWORKSITECOMMAND = "3";
    private final String SEARCHARTICLESCOMMAND = "4";
    private final String SEARHINVOICECOMMAND = "5";
    private final String SEARHBACKCOMMAND = "6";
    private final String CREATEINVOICECOMMAND = "1";
    private final String CREATE2NDINVOICECOMMAND = "2";
    private final String CREATE3THINVOICECOMMAND = "3";
    private final String PRINTINVOICECOMMAND = "4";
    private final String INVOICEBACKCOMMAND = "5";
    private final String HELOMESSAGE = "Tervetuloa Tmi Sähkötärsky:n laskutusjärjestelmään!";
    private final String MAINMENUMESSAGE = "1)Lisää 2)Etsi 3)Laskutus 4)Lopeta";
    private final String ADDMENUMESSAGE = "1)Yksityisasiakas 2)Yritysasiakas 3)Työkohde 4)Tunnit ja tarvikkeet työkohteeseen 5)Tarvike laskulle\n"
                                          + "6)Tarviketta varastoon 7)Uusi tarvike varastoon 8)Uusi tarvikelista 9)Takaisin";
    private final String SEARCHMENUMESSAGE = "1)Yksityisasiakas 2)Yritysasiakas 3)Työkohde 4)Tarvikeet 5)Lasku 6)Takaisin";
    private final String INVOICINGMENUMESSAGE = "1)Luo lasku 2)Luo muitutuslaskut maksamattomista 3)Luo karhulaskut maksamattomista 4)Näytä lasku 5)Takaisin";
    private final String WANTTOADDARTICLE = "Lisätäänkö tarvikeita? k)Kyllä e)Ei t)Näytä tarvikelista";
    private final String WRONGCOMMANDMESSAGE = "Virheellinen komento.";
    private final String NOTNUMBERMESSAGE = "Syöte virheellinen. Anna numero-muotoinen syöte ilman välilyöntejä.";
    private final String NOTDATEMESSAGE = "Syöte virheellinen. Anna oikean muotoinen pävämäärä.";
    private final String EMPTYINPUTMESSAGE = "Tyhjä syöte ei kelpaa.";
    private final String ADDOKMESSAGE = "Toiminto onnistui.";
    private final String ADDNOTOKMESSAGE = "Toiminto epäonnistui.";
    private final String NORESULTS = "Ei tuloksia.";
    private final String QUITCONFIRMMESSAGE = "Haluatko varmasti lopettaa? k)Kyllä e)Ei";
    
    public void run() {
        transaction = new Transaction();
        con = new dBConnection();
        con.go();
        con.createConnection();
        Scanner commandReader = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        String userCommand = "";
        String tmp1 = "";
        String tmp2 = "";
        String tmp3 = "";
        String tmp4 = "";
        String tmp5 = "";
        int tmp6;
        int tmp7;
        int[] tmp12;
        double tmp8;
        double tmp11;
        Date tmp9;
        Date tmp10;
        boolean uIRunning = true;
        boolean inAddMenu;
        boolean inSearchMenu;
        boolean inInvoicingMenu;
        boolean inAdding = true;
        boolean oK = false;        
        System.out.println(HELOMESSAGE);
        
        while (uIRunning) {
            inAddMenu = true;
            inSearchMenu = true;
            inInvoicingMenu = true;
            System.out.println(MAINMENUMESSAGE);
            userCommand = commandReader.nextLine();
            
            
            if (userCommand.equals(ADDCOMMAND)) {
                while (inAddMenu) {    
                    try {
                        System.out.println(ADDMENUMESSAGE);
                        userCommand = commandReader.nextLine();
                
                        if (userCommand.equals(ADDPRIVCLIENTCOMMAND)) {
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
                            oK = transaction.addClient(tmp1, tmp2, tmp3, tmp6, tmp5, con.getConnection());
                            if (oK) {
                                System.out.println(ADDOKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(ADDNOTOKMESSAGE);
                            }
                        }
                
                        else if (userCommand.equals(ADDCOMPCLIENTCOMMAND)) {
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
                            oK = transaction.addClient(tmp1, tmp6, tmp3, tmp7, tmp5, con.getConnection());
                            if (oK) {
                                System.out.println(ADDOKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(ADDNOTOKMESSAGE);
                            }
                        }
                
                        else if (userCommand.equals(ADDWORKSITECOMMAND)) {
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
                            oK = transaction.addWorkSiteToClient(tmp6, tmp2, tmp7, tmp4, tmp8, con.getConnection());
                            if (oK) {
                                System.out.println(ADDOKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(ADDNOTOKMESSAGE);
                            }
                        }
                
                        else if (userCommand.equals(ADDHOURSANDARTICLESCOMMAND)) {
                            tmp12 = new int[7];
                            System.out.print("Työkohdenumero: ");
                            tmp1 = commandReader.nextLine();
                            tmp12[0] = Integer.parseInt(tmp1);
                            System.out.print("Asennustyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[1] = Integer.parseInt(tmp1);
                            System.out.print("Asennustyö(alennusprosentti): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[2] = Integer.parseInt(tmp1);
                            System.out.print("Suunnittelutyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[3] = Integer.parseInt(tmp1);
                            System.out.print("Suunnittelutyö(alennusprosentti): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[4] = Integer.parseInt(tmp1);
                            System.out.print("Aputyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[5] = Integer.parseInt(tmp1);
                            System.out.print("Aputyö(alennusprosentti): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[6] = Integer.parseInt(tmp1);
                            do {
                                System.out.print(WANTTOADDARTICLE);
                                list2 = new ArrayList<Integer>();
                                userCommand = commandReader.nextLine();
                                if (userCommand.equals(YESCOMMAND)) {
                                    System.out.print("Tarvikenumero: ");
                                    tmp1 = commandReader.nextLine();
                                    list2.add(Integer.parseInt(tmp1));
                                    System.out.print("Määrä: ");
                                    tmp1 = commandReader.nextLine();
                                    list2.add(Integer.parseInt(tmp1));
                                    System.out.print("Alennusprosentti: ");
                                    tmp1 = commandReader.nextLine();
                                    list2.add(Integer.parseInt(tmp1));
                                }
                                else if (userCommand.equals(NOCOMMAND)){
                                    inAdding = false;
                                }
                                else if (userCommand.equals(ARTLISTCOMMAND)){
                                    list = transaction.getAllArticles(con.getConnection());
                                    printList(list);     
                                }
                                else {
                                    System.out.print(WRONGCOMMANDMESSAGE);
                                }
                            }
                            while (inAdding);
                            inAdding = true;
                            transaction.addHoursAndArticles(tmp12, list2, con.getConnection());
                        }
                
                        else if (userCommand.equals(ADDARTICLETOINVOICECOMMAND)) {
                   
                        }
                        
                        else if (userCommand.equals(ADDARTICLETOSTORAGECOMMAND)) {
                            System.out.print("Tarvikenumero: ");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            System.out.print("Lisättävä määrä: ");
                            tmp2 = commandReader.nextLine();
                            tmp7 = Integer.parseInt(tmp2);
                            oK = transaction.updateStorage(tmp6, tmp7, con.getConnection());
                            if (oK) {
                                System.out.println(ADDOKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(ADDNOTOKMESSAGE);
                            }
                        }
                        
                        else if (userCommand.equals(ADDNEWARTICLECOMMAND)) {
                            System.out.print("Tarvikeen nimi: ");
                            tmp1 = commandReader.nextLine();
                            System.out.print("Sisäänostohinta: ");
                            tmp3 = commandReader.nextLine();
                            tmp8 = Double.parseDouble(tmp3);
                            System.out.print("Myyntihinta: ");
                            tmp4 = commandReader.nextLine();
                            tmp11 = Double.parseDouble(tmp4);
                            System.out.print("Varastomäärä: ");
                            tmp5 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp5);
                            System.out.println(transaction.getArticleTypes(con.getConnection()));
                            System.out.print("Tarviketyyppi(Valitse ylläolevista): ");
                            tmp2 = commandReader.nextLine();
                            oK = transaction.addArticle(tmp1, tmp8, tmp11, tmp6, tmp2, con.getConnection());
                            if (oK) {
                                System.out.println(ADDOKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(ADDNOTOKMESSAGE);
                            }
                        }
                        
                        else if (userCommand.equals(ADDARTICLELISTCOMMAND)) {
                   
                        }
                
                        else if (userCommand.equals(ADDBACKCOMMAND)) {
                            inAddMenu = false;
                        }
                  
                        else {
                            System.out.println(WRONGCOMMANDMESSAGE);
                        }
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
            }
                        
            else if (userCommand.equals(SEARCHCOMMAND)) {
                while (inSearchMenu) {     
                    try {
                        System.out.println(SEARCHMENUMESSAGE);
                        userCommand = commandReader.nextLine();
                    
                        if (userCommand.equals(SEARCHPRIVCLIENTCOMMAND)) {
                            System.out.println("Etunimi:");
                            tmp1 = commandReader.nextLine();
                            System.out.println("Sukunimi:");
                            tmp2 = commandReader.nextLine();
                            list = transaction.getClientInfo(tmp1, tmp2, con.getConnection());
                            printList(list);
                        }
                        
                        else if (userCommand.equals(SEARCHCOMPLIENTCOMMAND)) {
                            System.out.println("Nimi:");
                            tmp1 = commandReader.nextLine();
                            list = transaction.getClientInfo(tmp1, con.getConnection());
                            printList(list);
                        }
                
                        else if (userCommand.equals(SEARCHWORKSITECOMMAND)) {
                            System.out.println("Asiakasnumero:");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            list = transaction.getWorkSiteInfo(tmp6, con.getConnection());
                            printList(list);
                        }
                
                        else if (userCommand.equals(SEARCHARTICLESCOMMAND)) {
                            list = transaction.getAllArticles(con.getConnection());
                            printList(list);     
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
            }
            
            else if (userCommand.equals(INVOICINGCOMMAND)) {
                while (inInvoicingMenu) {
                    try {
                        System.out.println(INVOICINGMENUMESSAGE);
                        userCommand = commandReader.nextLine();
                
                        if (userCommand.equals(CREATEINVOICECOMMAND)) {
                        
                        }    
                        else if (userCommand.equals(CREATE2NDINVOICECOMMAND)) {
                            System.out.print("Anna päivämäärä(dd/mm/yyyy) :");
                            tmp1 = commandReader.nextLine();
                            tmp9 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp1);
                            System.out.print("Anna eräpäivä(dd/mm/yyyy): ");
                            tmp2 = commandReader.nextLine();
                            tmp10 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp2);
                            oK = transaction.createReminderOfUnpaidInvoices(tmp10, tmp9, con.getConnection());
                            if (oK) {
                                System.out.println(ADDOKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(ADDNOTOKMESSAGE);
                            }
                        }
                
                        else if (userCommand.equals(CREATE3THINVOICECOMMAND)) {
                            System.out.print("Anna päivämäärä(dd/mm/yyyy) :");
                            tmp1 = commandReader.nextLine();
                            tmp9 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp1);
                            System.out.print("Anna eräpäivä(dd/mm/yyyy): ");
                            tmp2 = commandReader.nextLine();
                            tmp10 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp2);
                            oK = transaction.createSecondReminderOfUnpaidInvoice(tmp9, tmp10, con.getConnection());
                            if (oK) {
                                System.out.println(ADDOKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(ADDNOTOKMESSAGE);
                            }
                        }
                
                        else if (userCommand.equals(PRINTINVOICECOMMAND)) {
                
                        }
                
                        else if (userCommand.equals(INVOICEBACKCOMMAND)) {
                            inInvoicingMenu = false;
                        }
                
                        else {
                            System.out.println(WRONGCOMMANDMESSAGE);
                        }
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
                    catch (ParseException e) {
                       System.out.println(NOTDATEMESSAGE);
                    }
                }
            }
            
            else if (userCommand.equals(QUITCOMMAND)) {
                System.out.println(QUITCONFIRMMESSAGE);
                userCommand = commandReader.nextLine();
                if (userCommand.equals(YESCOMMAND)) {
                    uIRunning = false;
                    commandReader.close();
                    // Muutettava vielä toiseen metodiin.
                    if (con != null) {
                        con.disconnect();
                    }
                }
                if (!userCommand.equals(YESCOMMAND) && !userCommand.equals(NOCOMMAND)) {
                    System.out.println(WRONGCOMMANDMESSAGE);
                }
            }
            
            else {
                System.out.println(WRONGCOMMANDMESSAGE);
            }
        }
    }
    
    public void printList(ArrayList<String> list) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                System.out.println(s);
            }       
        }
        else {
            System.out.print(NORESULTS);
        }
    }    
}
