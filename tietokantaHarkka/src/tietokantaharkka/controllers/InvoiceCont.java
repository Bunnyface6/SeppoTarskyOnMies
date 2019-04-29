/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tietokantaharkka.controllers;
import java.sql.*;
import tietokantaharkka.baseClasses.Invoice;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jipsu
 */
public class InvoiceCont {
    /**
     * Viimeisimmät laskut
     */
    private ArrayList<Invoice> recentInvoices = new ArrayList<Invoice>();
    /**
     * Viimeksi käytetty lasku
     */
    private Invoice lastUsed;
    
    /**
     * Luo laskuolion
     * @param finalPayDate
     * @param ivNmbr
     * @param compDate
     * @param datePaid
     * @param nmbrOfInvoices
     * @param reminderOfNmbr
     * @param clientNmbr
     * @param workPerformanceNmbr
     * @return
     */
    public Invoice createInvoice(Date finalPayDate, int ivNmbr, Date compDate, Date datePaid, int nmbrOfInvoices, int reminderOfNmbr, int clientNmbr, int workPerformanceNmbr) {
        Invoice x = new Invoice(finalPayDate, ivNmbr, compDate, datePaid, nmbrOfInvoices, reminderOfNmbr, clientNmbr, workPerformanceNmbr);
        recentInvoices.add(x);
        lastUsed = x;
        return x;
    }
    
    /**
     * Lisää laskun tiedot databaseen
     * @param x
     * @param con
     * @return laskunnumero
     * @throws SQLException jos lisäyksessä virhe
     */
    public int addNewInvoice(Invoice x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        java.sql.Date d1 = null;
        java.sql.Date d2 = null;
        java.sql.Date d3 = null;
        int invN = 0;
        try {
            pStatement = con.prepareStatement("INSERT INTO lasku(paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero) VALUES(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            if(x.getCompDate() != null)
                d1 = new java.sql.Date(x.getCompDate().getTime());
            pStatement.setDate(1, d1);
            if(x.getFinalPayDate() != null)
                d2 = new java.sql.Date(x.getFinalPayDate().getTime());
            pStatement.setDate(2, d2);
            if(x.getDatePaid() != null)
                d3 = new java.sql.Date(x.getDatePaid().getTime());
            pStatement.setDate(3, d3);
            pStatement.setInt(4, x.getNmbrOfInvoices());
            pStatement.setInt(5, x.getClientNmbr());
            pStatement.setInt(6, x.getReminderOfNmbr());
            if (x.getWorkPerformanceNmbr() == 0) {
                pStatement.setNull(7, java.sql.Types.INTEGER);
            }
            else {
                pStatement.setInt(7, x.getWorkPerformanceNmbr());
            }
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            resultSet.next();
            invN = resultSet.getInt(1);
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return invN;           
    }
    
    /**
     * Etsii laskun databasesta laskunnumerolla
     * @param nmbr
     * @param con
     * @return laskuolio
     * @throws SQLException jos etsinnässä virhe
     */
    public Invoice findInvoiceByNmbr(int nmbr, Connection con) throws SQLException {
        Invoice i = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE laskutunnus = ?"); 
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                Date fPD = null;
                Date d = null;
                Date pD = null;
                if (resultSet.getDate(3) != null) {
                    fPD = new java.util.Date(resultSet.getDate(3).getTime());    
                }
                if (resultSet.getDate(2) != null) {
                    d = new java.util.Date(resultSet.getDate(2).getTime());
                }
                if (resultSet.getDate(4) != null) {
                    pD = new java.util.Date(resultSet.getDate(4).getTime());
                }
                i = createInvoice(fPD, resultSet.getInt(1), d, pD, resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8));
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return i;
    }
    
    /**
     * Etsii laskun asiakasnumerolla
     * @param nmbr
     * @param con
     * @return arraylistin laskuja
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Invoice> findInvoiceByClientNmbr(int nmbr, Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE asiakasnumero = ?"); 
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Date fPD = null;
                Date d = null;
                Date pD = null;
                if (resultSet.getDate(3) != null) {
                    fPD = new java.util.Date(resultSet.getDate(3).getTime());    
                }
                if (resultSet.getDate(2) != null) {
                    d = new java.util.Date(resultSet.getDate(2).getTime());
                }
                if (resultSet.getDate(4) != null) {
                    pD = new java.util.Date(resultSet.getDate(4).getTime());
                }
                i.add(createInvoice(fPD, resultSet.getInt(1), d, pD, resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return i;
    }
    
    /**
     * Etsii laskun tyosuoritusnumerolla
     * @param nmbr
     * @param con
     * @return arraylistin laskuja
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Invoice> findInvoiceByWorkPerformanceNmbr(int nmbr, Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE tyosuoritusnumero = ?"); 
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Date fPD = null;
                Date d = null;
                Date pD = null;
                if (resultSet.getDate(3) != null) {
                    fPD = new java.util.Date(resultSet.getDate(3).getTime());    
                }
                if (resultSet.getDate(2) != null) {
                    d = new java.util.Date(resultSet.getDate(2).getTime());
                }
                if (resultSet.getDate(4) != null) {
                    pD = new java.util.Date(resultSet.getDate(4).getTime());
                }
                i.add(createInvoice(fPD, resultSet.getInt(1), d, pD, resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return i;
    }
    
    /**
     * Etsii laskun maksupäivällä
     * @param fBD
     * @param con
     * @return arraylistin laskuja
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Invoice> findInvoiceByFinalPayDate(Date fBD, Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE erapaiva = ?"); 
            pStatement.setDate(1, new java.sql.Date(fBD.getTime()));
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Date fPD = null;
                Date d = null;
                Date pD = null;
                if (resultSet.getDate(3) != null) {
                    fPD = new java.util.Date(resultSet.getDate(3).getTime());    
                }
                if (resultSet.getDate(2) != null) {
                    d = new java.util.Date(resultSet.getDate(2).getTime());
                }
                if (resultSet.getDate(4) != null) {
                    pD = new java.util.Date(resultSet.getDate(4).getTime());
                }
                i.add(createInvoice(fPD, resultSet.getInt(1), d, pD, resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return i;
    }
    
    /**
     * Etsii laskun päivämäärällä
     * @param fBD
     * @param con
     * @return arraylistin laskuja
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Invoice> findInvoiceByDate(Date fBD, Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE paivamaara = ?"); 
            pStatement.setDate(1, new java.sql.Date(fBD.getTime()));
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Date fPD = null;
                Date d = null;
                Date pD = null;
                if (resultSet.getDate(3) != null) {
                    fPD = new java.util.Date(resultSet.getDate(3).getTime());    
                }
                if (resultSet.getDate(2) != null) {
                    d = new java.util.Date(resultSet.getDate(2).getTime());
                }
                if (resultSet.getDate(4) != null) {
                    pD = new java.util.Date(resultSet.getDate(4).getTime());
                }
                i.add(createInvoice(fPD, resultSet.getInt(1), d, pD, resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return i;
    }
    
    /**
     * Etsii laskuja
     * @param con
     * @return arraylistin laskuja
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Invoice> findInvoices(Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku"); 
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Date fPD = null;
                Date d = null;
                Date pD = null;
                if (resultSet.getDate(3) != null) {
                    fPD = new java.util.Date(resultSet.getDate(3).getTime());    
                }
                if (resultSet.getDate(2) != null) {
                    d = new java.util.Date(resultSet.getDate(2).getTime());
                }
                if (resultSet.getDate(4) != null) {
                    pD = new java.util.Date(resultSet.getDate(4).getTime());
                }
                i.add(createInvoice(fPD, resultSet.getInt(1), d, pD, resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return i;
    }
        
    /**
     * Etsii maksamattomia laskuja
     * @param whatNumber
     * @param con
     * @return arraylistin laskuolioita
     * @throws SQLException jos etsinnässä virhe
     */
    public ArrayList<Invoice> findUnpaidInvoices(int whatNumber, Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            if (whatNumber > 0) {
                if (whatNumber == 1) {
                    pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE erapaiva IS NOT NULL AND maksupaiva IS NULL"); 
                }
                else {
                    pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE laskunumero = ? AND maksupaiva IS NULL");
                    pStatement.setInt(1, whatNumber);
                }
                resultSet = pStatement.executeQuery();
                while (resultSet.next()) {
                    Date fPD = null;
                    Date d = null;
                    Date pD = null;
                    if (resultSet.getDate(3) != null) {
                        fPD = new java.util.Date(resultSet.getDate(3).getTime());    
                    }
                    if (resultSet.getDate(2) != null) {
                        d = new java.util.Date(resultSet.getDate(2).getTime());
                    }
                    if (resultSet.getDate(4) != null) {
                        pD = new java.util.Date(resultSet.getDate(4).getTime());
                    }
                    i.add(createInvoice(fPD, resultSet.getInt(1), d, pD, resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
                }
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return i;
    }
            
    /**
     * Poistaa laskun databasesta
     * @param x
     * @param con
     * @return laskuolio
     * @throws SQLException jos poistossa virhe
     */
    public Invoice removeInvoice(Invoice x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM lasku WHERE laskutunnus = ?");
            pStatement.setInt(1, x.getIvNmbr());
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return x;    
    }
    
    /**
     * Päivittää laskun päivämäärän
     * @param x
     * @param con
     * @return true/false
     * @throws SQLException jos päivityksessä virhe
     */
    public boolean updateCompDateFinalPayDate(Invoice x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        boolean oK = false;
        try {
            pStatement = con.prepareStatement("UPDATE lasku SET paivamaara = ?, erapaiva = ? WHERE laskutunnus = ?");
            pStatement.setDate(1, new java.sql.Date(x.getCompDate().getTime()));
            pStatement.setDate(2, new java.sql.Date(x.getFinalPayDate().getTime()));
            pStatement.setInt(3, x.getIvNmbr());
            int rV = pStatement.executeUpdate();
            if (rV == 1) {
                oK = true;
            }
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return oK;        
    }
    
    /**
     * Päivittää maksupäivän
     * @param x
     * @param con
     * @return true/false
     * @throws SQLException jos päivityksessä virhe
     */
    public boolean updateDatePaid(Invoice x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        boolean oK = false;
        try {
            pStatement = con.prepareStatement("UPDATE lasku SET maksupaiva = ? WHERE laskutunnus = ?");
            pStatement.setDate(1, new java.sql.Date(x.getDatePaid().getTime()));
            pStatement.setInt(2, x.getIvNmbr());
            int rV = pStatement.executeUpdate();
            if (rV == 1) {
                oK = true;
            }
        }
        catch(SQLException e) {
            throw new SQLException(e.getMessage());
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return oK;        
    }
     
    /**
     * Tarkistaa luodaanko lasku
     * @param workPerformanceNmbr
     * @param con
     * @return true/false
     * @throws SQLException jos etsinnässä virhe
     */
    public boolean invoiceIsCreated(int workPerformanceNmbr, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT laskutunnus FROM lasku WHERE tyosuoritusnumero = ? AND erapaiva IS NOT NULL"); 
            pStatement.setInt(1, workPerformanceNmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            else {
                return false;
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
    }
    
    /**
     * Hakee uusimman laskun listalta suurimman laskunumeron perusteella.
     * 
     * @param x lista laskuista
     * @return listan uusin lasku
     */
    public Invoice chooseLatest(ArrayList<Invoice> x){
        Invoice rtn = x.get(0);
        for(Invoice y : x){
            if(y.getIvNmbr() > rtn.getIvNmbr()){
                rtn = y;
            }
        }
        return rtn;
    }
}