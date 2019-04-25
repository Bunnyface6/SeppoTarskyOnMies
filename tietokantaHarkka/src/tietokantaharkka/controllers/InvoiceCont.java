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

    private ArrayList<Invoice> recentInvoices = new ArrayList<Invoice>();
    
    private Invoice lastUsed;
    
    public Invoice createInvoice(Date finalPayDate, int ivNmbr, Date compDate, Date datePaid, int nmbrOfInvoices, int reminderOfNmbr, int clientNmbr, int workPerformanceNmbr) {
        Invoice x = new Invoice(finalPayDate, ivNmbr, compDate, datePaid, nmbrOfInvoices, reminderOfNmbr, clientNmbr, workPerformanceNmbr);
        recentInvoices.add(x);
        lastUsed = x;
        return x;
    }
    
    public void addNewInvoice(Invoice x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO lasku(paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero) VALUES(?, ?, ?, ?, ?, ?, ?)");
            java.sql.Date d = new java.sql.Date(x.getCompDate().getTime());
            pStatement.setDate(1, d);
            d = new java.sql.Date(x.getFinalPayDate().getTime());
            pStatement.setDate(2, d);
            d = new java.sql.Date(x.getDatePaid().getTime());
            pStatement.setDate(3, d);
            pStatement.setInt(4, x.getNmbrOfInvoices());
            pStatement.setInt(5, x.getClientNmbr());
            pStatement.setInt(6, x.getReminderOfNmbr());
            pStatement.setInt(7, x.getWorkPerformanceNmbr());
            pStatement.executeUpdate();
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }    	
    }
    
    public Invoice findInvoiceByNmbr(int nmbr, Connection con) throws SQLException {
        Invoice i = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE laskutunnus = ?"); 
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                i = createInvoice(new java.util.Date(resultSet.getDate(3).getTime()), resultSet.getInt(1), 
                                  new java.util.Date(resultSet.getDate(2).getTime()), new java.util.Date(resultSet.getDate(4).getTime()), 
                                  resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8));
            }
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
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
    
    public Invoice findInvoiceByClientNmbr(int nmbr, Connection con) throws SQLException {
        Invoice i = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE asiakasnumero = ?"); 
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                i = createInvoice(new java.util.Date(resultSet.getDate(3).getTime()), resultSet.getInt(1), 
                                  new java.util.Date(resultSet.getDate(2).getTime()), new java.util.Date(resultSet.getDate(4).getTime()), 
                                  resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8));
            }
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
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
    
    public Invoice findInvoiceByWorkPerformanceNmbr(int nmbr, Connection con) throws SQLException {
        Invoice i = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE tyosuoritusnumero = ?"); 
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                i = createInvoice(new java.util.Date(resultSet.getDate(3).getTime()), resultSet.getInt(1), 
                                  new java.util.Date(resultSet.getDate(2).getTime()), new java.util.Date(resultSet.getDate(4).getTime()), 
                                  resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8));
            }
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
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
    
    public ArrayList<Invoice> findInvoiceByFinalPayDate(Date fBD, Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE erapaiva = ?"); 
            pStatement.setDate(1, new java.sql.Date(fBD.getTime()));
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                i.add(createInvoice(new java.util.Date(resultSet.getDate(3).getTime()), resultSet.getInt(1), 
                                  new java.util.Date(resultSet.getDate(2).getTime()), new java.util.Date(resultSet.getDate(4).getTime()), 
                                  resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
            }
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
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
    
    public ArrayList<Invoice> findInvoiceByDate(Date fBD, Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE paivamaara = ?"); 
            pStatement.setDate(1, new java.sql.Date(fBD.getTime()));
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                i.add(createInvoice(new java.util.Date(resultSet.getDate(3).getTime()), resultSet.getInt(1), 
                                  new java.util.Date(resultSet.getDate(2).getTime()), new java.util.Date(resultSet.getDate(4).getTime()), 
                                  resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
            }
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
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
    
    
    
    public ArrayList<Invoice> findUnpaidInvoices(int whatNumber, Connection con) throws SQLException {
        ArrayList<Invoice> i = new ArrayList<Invoice>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            if (whatNumber > 0) {
                con.setAutoCommit(false);
                if (whatNumber == 1) {
                    pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE erapaiva IS NOT NULL AND maksupaiva IS NULL"); 
                }
                else {
                    pStatement = con.prepareStatement("SELECT laskutunnus, paivamaara, erapaiva, maksupaiva, laskunumero, asiakasnumero, muistutus_laskusta, tyosuoritusnumero FROM lasku WHERE laskunumero = ? AND maksupaiva IS NULL");
                    pStatement.setInt(1, whatNumber);
                }
                resultSet = pStatement.executeQuery();
                while (resultSet.next()) {
                    i.add(createInvoice(new java.util.Date(resultSet.getDate(3).getTime()), resultSet.getInt(1), 
                                      new java.util.Date(resultSet.getDate(2).getTime()), new java.util.Date(resultSet.getDate(4).getTime()), 
                                      resultSet.getInt(5), resultSet.getInt(7), resultSet.getInt(6), resultSet.getInt(8)));
                }
                con.commit();
            }
        }
        catch(SQLException e) {
            con.rollback(); 
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
            
    public Invoice removeInvoice(Invoice x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM lasku WHERE laskutunnus = ?");
            pStatement.setInt(1, x.getIvNmbr());
            pStatement.executeUpdate();
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
            }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return x;    
    }
    
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
}