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
 * UI-luuokka.
 * @author Jipsu
 */
public class UI {
    
    /* Tapahtumat */
    Transaction transaction;
    
    /* Tietokannan yhteys */
    dBConnection con;
    
    /** Vakioituja komentoja ja viestejä. */
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
    private final String SETINVOICEPAIDCOMMAND = "5";
    private final String MAKEOFFERCOMMAND = "6";
    private final String INVOICEBACKCOMMAND = "7";
    private final String HELOMESSAGE = "Tervetuloa Tmi Sähkötärsky:n laskutusjärjestelmään!";
    private final String MAINMENUMESSAGE = "1)Lisää 2)Etsi 3)Laskutus 4)Lopeta";
    private final String ADDMENUMESSAGE = "1)Yksityisasiakas 2)Yritysasiakas 3)Työkohde 4)Tunnit ja tarvikkeet työkohteeseen 5)Tarvike laskulle\n"
                                          + "6)Tarvikeen varastosaldo 7)Uusi tarvike varastoon 8)Uusi tarvikelista 9)Takaisin";
    private final String SEARCHMENUMESSAGE = "1)Yksityisasiakas 2)Yritysasiakas 3)Työkohde 4)Tarvikeet 5)Laskupohjat 6)Takaisin";
    private final String INVOICINGMENUMESSAGE = "1)Luo lasku 2)Luo muitutuslaskut maksamattomista 3)Luo karhulaskut maksamattomista 4)Tulosta lasku 5)Kuittaa lasku maksetuksi\n" 
                                                + "6)Tee hinta-arvio 7)Takaisin";
    private final String WANTTOADDARTICLE = "Lisätäänkö tarvikeita? k)Kyllä e)Ei t)Näytä tarvikelista";
    private final String WRONGCOMMANDMESSAGE = "Virheellinen komento.";
    private final String NOTNUMBERMESSAGE = "Syöte virheellinen. Anna numero-muotoinen syöte ilman välilyöntejä.";
    private final String NOTDATEMESSAGE = "Syöte virheellinen. Anna oikean muotoinen pävämäärä.";
    private final String EMPTYINPUTMESSAGE = "Tyhjä syöte ei kelpaa.";
    private final String OKMESSAGE = "Toiminto onnistui.";
    private final String NOTOKMESSAGE = "Toiminto epäonnistui.";
    private final String NOTOKMESSAGE2 = "Toiminto ei onnistunut.";
    private final String NORESULTS = "Ei tuloksia.";
    private final String QUITCONFIRMMESSAGE = "Haluatko varmasti lopettaa? k)Kyllä e)Ei";
    
    /**
     * Käynnistää käyttöliittymä-silmukan.
     */
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
            
            // Jos valitaan Lisää-vaihtoehto.
            if (userCommand.equals(ADDCOMMAND)) {
                while (inAddMenu) {    
                    try {
                        System.out.println(ADDMENUMESSAGE);
                        userCommand = commandReader.nextLine();
                        
                        // Jos valitaan yksityisasiakkaan lisäys.
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
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan yritysasiakkaan lisäys.
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
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan työkohteen lisäys.
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
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan tuntien ja tarvikkeiden lisäys työkohteeseen.
                        else if (userCommand.equals(ADDHOURSANDARTICLESCOMMAND)) {
                            tmp12 = new int[7];
                            System.out.print("Työkohdenumero: ");
                            tmp1 = commandReader.nextLine();
                            tmp12[0] = Integer.parseInt(tmp1);
                            System.out.print("Asennustyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[1] = Integer.parseInt(tmp1);
                            System.out.print("Asennustyö(alennusprosentti: kaikista asennustöistä1): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[2] = Integer.parseInt(tmp1);
                            System.out.print("Suunnittelutyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[3] = Integer.parseInt(tmp1);
                            System.out.print("Suunnittelutyö(alennusprosentti: Kaikista suunnittelutöistä): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[4] = Integer.parseInt(tmp1);
                            System.out.print("Aputyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[5] = Integer.parseInt(tmp1);
                            System.out.print("Aputyö(alennusprosentti: kaikista aputöistä)): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[6] = Integer.parseInt(tmp1);
                            list2 = new ArrayList<Integer>();
                            do {
                                System.out.println(WANTTOADDARTICLE);
                                userCommand = commandReader.nextLine();
                                if (userCommand.equals(YESCOMMAND)) {
                                    System.out.print("Tarvikenumero: ");
                                    tmp1 = commandReader.nextLine();
                                    list2.add(Integer.parseInt(tmp1));
                                    System.out.print("Määrä: ");
                                    tmp1 = commandReader.nextLine();
                                    list2.add(Integer.parseInt(tmp1));
                                    System.out.print("Alennusprosentti(Koskee kaikkia samanlaisia tarvikkeita): ");
                                    tmp1 = commandReader.nextLine();
                                    list2.add(Integer.parseInt(tmp1));
                                }
                                else if (userCommand.equals(NOCOMMAND)){
                                    inAdding = false;
                                    oK = transaction.addHoursAndArticles(tmp12, list2, con.getConnection());
                                    if (oK) {
                                        System.out.println(OKMESSAGE);
                                        oK = false;
                                    }
                                    else {
                                        System.out.println(NOTOKMESSAGE);
                                    }
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
                        }
                        
                        // Jos valitaan tarvikkeen lisäys laskulle(tarvikkeiden myynti, ilman työkohdetta).
                        else if (userCommand.equals(ADDARTICLETOINVOICECOMMAND)) {
                            list2 = new ArrayList<Integer>();
                            System.out.print("Asiakasnumero: ");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            list = transaction.getAllArticles(con.getConnection());
                            printList(list);
                            System.out.println("\nValitse ylläolevista.");
                            do {
                                System.out.print("Tarvikenumero: ");
                                tmp1 = commandReader.nextLine();
                                list2.add(Integer.parseInt(tmp1));
                                System.out.print("Määrä: ");
                                tmp1 = commandReader.nextLine();
                                list2.add(Integer.parseInt(tmp1));
                                System.out.print("Alennusprosentti: ");
                                tmp1 = commandReader.nextLine();
                                list2.add(Integer.parseInt(tmp1));
                                System.out.println(WANTTOADDARTICLE);
                                userCommand = commandReader.nextLine();
                                if (userCommand.equals(NOCOMMAND)){
                                    inAdding = false;
                                }
                                if (userCommand.equals(ARTLISTCOMMAND)){
                                    printList(list);     
                                }
                                if (!userCommand.equals(YESCOMMAND) && !userCommand.equals(ARTLISTCOMMAND) && !userCommand.equals(NOCOMMAND)) {
                                    System.out.print(WRONGCOMMANDMESSAGE);
                                }
                            }
                            while (inAdding);
                            inAdding = true;
                            System.out.print("Anna päivämäärä laskulle(dd/mm/yyyy) :");
                            tmp1 = commandReader.nextLine();
                            tmp9 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp1);
                            System.out.print("Anna eräpäivä laskulle(dd/mm/yyyy): ");
                            tmp2 = commandReader.nextLine();
                            tmp10 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp2);
                            oK = transaction.addArticlesToInvoice(tmp6, list2, tmp9, tmp10, con.getConnection());
                            if (oK) {
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan tarvikkeen varastosaldon lisäys.
                        else if (userCommand.equals(ADDARTICLETOSTORAGECOMMAND)) {
                            System.out.print("Tarvikenumero: ");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            System.out.print("Lisättävä määrä: ");
                            tmp2 = commandReader.nextLine();
                            tmp7 = Integer.parseInt(tmp2);
                            oK = transaction.updateStorage(tmp6, tmp7, con.getConnection());
                            if (oK) {
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan uuden tarvikkeen lisäys varastoon.
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
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan tarvikelistan lisäys(tavikkeiden lisäys tiedostosta ja vanhojen kirjoittaminen tiedostoon).
                        else if (userCommand.equals(ADDARTICLELISTCOMMAND)) {
                            System.out.print("Anna uuden tarvikelistan tiedoston nimi: ");
                            tmp1 = commandReader.nextLine();
                            oK = transaction.fullUpdateFromFile(tmp1, con.getConnection());
                            if (oK) {
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan takaisin.
                        else if (userCommand.equals(ADDBACKCOMMAND)) {
                            inAddMenu = false;
                        }
                        
                        // Muuten, komento on virheellinen.
                        else {
                            System.out.println(WRONGCOMMANDMESSAGE);
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println(NOTNUMBERMESSAGE);
                    }
                    catch (SQLException e) {
                        System.out.println(NOTOKMESSAGE2);
                    }
                    catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                        System.out.println(EMPTYINPUTMESSAGE);
                    }
                    catch (ParseException e) {
                       System.out.println(NOTDATEMESSAGE);
                    }
                }
            }
            
            // Jos valitaan etsi.            
            else if (userCommand.equals(SEARCHCOMMAND)) {
                while (inSearchMenu) {     
                    try {
                        System.out.println(SEARCHMENUMESSAGE);
                        userCommand = commandReader.nextLine();
                        
                        // Jos valitaan etsi yksityisasiakas.  
                        if (userCommand.equals(SEARCHPRIVCLIENTCOMMAND)) {
                            System.out.println("Etunimi:");
                            tmp1 = commandReader.nextLine();
                            System.out.println("Sukunimi:");
                            tmp2 = commandReader.nextLine();
                            list = transaction.getClientInfo(tmp1, tmp2, con.getConnection());
                            printList(list);
                        }
                        
                        // Jos valitaan etsi yriysasiakas.  
                        else if (userCommand.equals(SEARCHCOMPLIENTCOMMAND)) {
                            System.out.println("Nimi:");
                            tmp1 = commandReader.nextLine();
                            list = transaction.getClientInfo(tmp1, con.getConnection());
                            printList(list);
                        }
                        
                        // Jos valitaan etsi työkohde.  
                        else if (userCommand.equals(SEARCHWORKSITECOMMAND)) {
                            System.out.println("Asiakasnumero:");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            list = transaction.getWorkSiteInfo(tmp6, con.getConnection());
                            printList(list);
                        }
                        
                        // Jos valitaan etsi tarvikkeet.  
                        else if (userCommand.equals(SEARCHARTICLESCOMMAND)) {
                            list = transaction.getAllArticles(con.getConnection());
                            printList(list);     
                        }
                        
                        // Jos valitaan etsi laskupohjay.  
                        else if (userCommand.equals(SEARHINVOICECOMMAND)) {
                            list = transaction.getInvoiceInfos(con.getConnection());
                            printList(list);
                        }
                        
                        // Jos valitaan takaisin.  
                        else if (userCommand.equals(SEARHBACKCOMMAND)) {
                            inSearchMenu = false;
                        }
                        
                        // Muuten, komento on virheellinen.  
                        else {
                            System.out.println(WRONGCOMMANDMESSAGE);
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println(NOTNUMBERMESSAGE);
                    }
                    catch (SQLException e) {
                        System.out.println(NOTOKMESSAGE2);
                    }
                    catch (NullPointerException e) {
                        System.out.println(EMPTYINPUTMESSAGE);
                    }
                }
            }
            
            // Jos valitaan laskutus.
            else if (userCommand.equals(INVOICINGCOMMAND)) {
                while (inInvoicingMenu) {
                    try {
                        System.out.println(INVOICINGMENUMESSAGE);
                        userCommand = commandReader.nextLine();
                        
                        // Jos valitaan luo lasku.
                        if (userCommand.equals(CREATEINVOICECOMMAND)) {
                            System.out.print("Anna laskutunnus: ");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            System.out.print("Anna päivämäärä(dd/mm/yyyy) :");
                            tmp1 = commandReader.nextLine();
                            tmp9 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp1);
                            System.out.print("Anna eräpäivä(dd/mm/yyyy): ");
                            tmp1 = commandReader.nextLine();
                            tmp10 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp1);
                            oK = transaction.createInvoice(tmp6, tmp9, tmp10, con.getConnection());
                            if (oK) {
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        } 
                        
                        // Jos valitaan luo muistutuslaskut maksamattomista laskuista.
                        else if (userCommand.equals(CREATE2NDINVOICECOMMAND)) {
                            System.out.print("Anna tämänhetkinen päivä, johon eräpäiviä verrataan(dd/mm/yyyy) :");
                            tmp1 = commandReader.nextLine();
                            tmp9 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp1);
                            System.out.print("Anna eräpäivä laskulle(dd/mm/yyyy): ");
                            tmp2 = commandReader.nextLine();
                            tmp10 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp2);
                            oK = transaction.createReminderOfUnpaidInvoices(tmp10, tmp9, con.getConnection());
                            if (oK) {
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan luo karhulaskut maksamattomista muistutuslaskuista.
                        else if (userCommand.equals(CREATE3THINVOICECOMMAND)) {
                            System.out.print("Anna tämänhetkinen päivä, johon eräpäiviä verrataan(dd/mm/yyyy) :");
                            tmp1 = commandReader.nextLine();
                            tmp9 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp1);
                            System.out.print("Anna eräpäivä laskulle(dd/mm/yyyy): ");
                            tmp2 = commandReader.nextLine();
                            tmp10 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp2);
                            oK = transaction.createSecondReminderOfUnpaidInvoice(tmp9, tmp10, con.getConnection());
                            if (oK) {
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan tulosta lasku. 
                        else if (userCommand.equals(PRINTINVOICECOMMAND)) {
                            System.out.print("Anna laskunumero: ");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            tmp1 = transaction.printInvoice(tmp6, con.getConnection());
                            if (tmp1 != null) {
                                System.out.println(tmp1);
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan kuittaa lasku maksetuksi.
                        else if (userCommand.equals(SETINVOICEPAIDCOMMAND)) {
                            System.out.print("Anna laskutunnus: ");
                            tmp1 = commandReader.nextLine();
                            tmp6 = Integer.parseInt(tmp1);
                            System.out.print("Anna maksupäivämäärä(dd/mm/yyyy) :");
                            tmp1 = commandReader.nextLine();
                            tmp9 = new SimpleDateFormat("dd/MM/yyyy").parse(tmp1);
                            oK = transaction.setInvoicePaid(tmp6, tmp9, con.getConnection());
                            if (oK) {
                                System.out.println(OKMESSAGE);
                                oK = false;
                            }
                            else {
                                System.out.println(NOTOKMESSAGE + ": Tarkista, että laskutunnus liittyy luotuun maksamattomaan laskuun, jossa päivämäärä ja eräpäivä.");
                            }
                        }
                        
                        // Jos valitaan tee hinta-arvio.
                        else if (userCommand.equals(MAKEOFFERCOMMAND)) {
                            tmp12 = new int[3];
                            System.out.print("Asennustyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[0] = Integer.parseInt(tmp1);
                            System.out.print("Suunnittelutyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[1] = Integer.parseInt(tmp1);
                            System.out.print("Aputyö(tunnit): ");
                            tmp1 = commandReader.nextLine();
                            tmp12[2] = Integer.parseInt(tmp1);
                            list2 = new ArrayList<Integer>();
                            do {
                                System.out.print(WANTTOADDARTICLE);
                                userCommand = commandReader.nextLine();
                                if (userCommand.equals(YESCOMMAND)) {
                                    System.out.print("Tarvikenumero: ");
                                    tmp1 = commandReader.nextLine();
                                    list2.add(Integer.parseInt(tmp1));
                                    System.out.print("Määrä: ");
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
                            tmp1 = transaction.createAssessmentOfPrices(tmp12, list2, con.getConnection());
                            if (tmp1 != null) {
                                System.out.println(tmp1);
                            }
                            else {
                                System.out.println(NOTOKMESSAGE);
                            }
                        }
                        
                        // Jos valitaan takaisin.
                        else if (userCommand.equals(INVOICEBACKCOMMAND)) {
                            inInvoicingMenu = false;
                        }
                        
                        // Muuten, komento on virheellinen.
                        else {
                            System.out.println(WRONGCOMMANDMESSAGE);
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println(NOTNUMBERMESSAGE);
                    }
                    catch (SQLException e) {
                        System.out.println(NOTOKMESSAGE2);
                    }
                    catch (NullPointerException e) {
                        System.out.println(EMPTYINPUTMESSAGE);
                    }
                    catch (ParseException e) {
                       System.out.println(NOTDATEMESSAGE);
                    }
                }
            }
            
            // Jos valitaan lopeta.
            else if (userCommand.equals(QUITCOMMAND)) {
                System.out.println(QUITCONFIRMMESSAGE);
                userCommand = commandReader.nextLine();
                // Jos valitaan kyllä.
                if (userCommand.equals(YESCOMMAND)) {
                    uIRunning = false;
                    commandReader.close();
                    // Muutettava vielä toiseen metodiin.
                    if (con != null) {
                        con.disconnect();
                    }
                }
                // Jos komento on virheellinen.
                if (!userCommand.equals(YESCOMMAND) && !userCommand.equals(NOCOMMAND)) {
                    System.out.println(WRONGCOMMANDMESSAGE);
                }
            }
            
            // Muuten, komento on virheellinen.
            else {
                System.out.println(WRONGCOMMANDMESSAGE);
            }
        }
    }
    
    /**
     * Tulostaa parametrina saadun String-olio-listan. 
     * 
     * @param list lista String tyyppisistä olioista.
     */
    public void printList(ArrayList<String> list) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                System.out.println(s);
            }       
        }
        else {
            System.out.println(NORESULTS);
        }
    }    
}
