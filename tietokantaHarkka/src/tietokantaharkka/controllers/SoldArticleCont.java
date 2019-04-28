/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.SoldArticle;
import java.sql.*;

/**
 *
 * @author Jipsu
 */
public class SoldArticleCont {
    
    private ArrayList<SoldArticle> recentSoldArticles = new ArrayList<SoldArticle>();
    
    private SoldArticle lastUsed;
    
    public SoldArticle createSoldArticle(int invoiceNmbr, int articleNmbr, int discountPer, int nmbrOfSold) {
        SoldArticle x = new SoldArticle(invoiceNmbr, articleNmbr, discountPer, nmbrOfSold);
        recentSoldArticles.add(x);
        lastUsed = x;
        return x;
    }
    
    public void addNewSoldArticle(SoldArticle x, Connection con) throws SQLException, IllegalArgumentException {
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            //Vähentää tarvittavan määrän tarvikkeita varastosta.
            pStatement = con.prepareStatement("SELECT varastotilanne FROM tarvike WHERE tarvikenumero = ?");
            pStatement.setInt(1, x.getArticleNmbr());
            resultSet = pStatement.executeQuery();
            pStatement.clearParameters();
            if (!resultSet.next()) {
                throw new IllegalArgumentException("Tarviketta " + x.getArticleNmbr() + " ei ole varastossa!");
            }
            int numberOfItems = resultSet.getInt(1);
            numberOfItems = numberOfItems - x.getNmbrOfSold();
            if (numberOfItems < 0) {
                throw new IllegalArgumentException("Tarviketta " + x.getArticleNmbr() + " liian vähän varastossa!");
            }
            pStatement = con.prepareStatement("UPDATE tarvike SET varastotilanne = ? WHERE tarvikenumero = ?");
            pStatement.setInt(1, numberOfItems);
            pStatement.setInt(2, x.getArticleNmbr());
            pStatement.executeUpdate();
            pStatement.clearParameters();
            if (findSoldArticleOfInvoice(x.getInvoiceNmbr(), x.getArticleNmbr(), con) == null) {
                pStatement = con.prepareStatement("INSERT INTO myyty_tarvike VALUES(?, ?, ?, ?)");
                pStatement.setInt(1, x.getInvoiceNmbr());
                pStatement.setInt(2, x.getArticleNmbr());
                pStatement.setInt(3, x.getDiscountPer());
                pStatement.setInt(4, x.getNmbrOfSold());
                pStatement.executeUpdate();
            }
            else {
                pStatement = con.prepareStatement("UPDATE myyty_tarvike SET kappalemaara = kappalemaara + ?, alennusprosentti = ? WHERE laskutunnus = ? AND tarvikenumero = ?");
                pStatement.setInt(1, x.getNmbrOfSold());
                pStatement.setInt(2, x.getDiscountPer());
                pStatement.setInt(3, x.getInvoiceNmbr());
                pStatement.setInt(4, x.getArticleNmbr());
                pStatement.executeUpdate();
            }
            con.commit();
        }
        catch(SQLException e) {
            con.rollback(); 
        }
        finally {
            if (pStatement != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
        }
    }
    public ArrayList<SoldArticle> findSoldArticles(int aNmbr, Connection con) throws SQLException {
        ArrayList<SoldArticle> sA = new ArrayList<SoldArticle>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT laskutunnus, tarvikenumero, alennusprosentti, kappalemaara FROM myyty_tarvike WHERE tarvikenumero = ?");
            pStatement.setInt(1, aNmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                SoldArticle a = createSoldArticle(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4));
                sA.add(a);
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
        return sA;
    }
    
    public ArrayList<SoldArticle> findSoldArticlesOfInvoice(int aNmbr, Connection con) throws SQLException {
        ArrayList<SoldArticle> sA = new ArrayList<SoldArticle>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT laskutunnus, tarvikenumero, alennusprosentti, kappalemaara FROM myyty_tarvike WHERE laskutunnus = ?");
            pStatement.setInt(1, aNmbr);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                SoldArticle a = createSoldArticle(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4));
                sA.add(a);
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
        return sA;
    }
    
    public SoldArticle findSoldArticleOfInvoice(int iNmbr, int aNmbr, Connection con) throws SQLException {
        SoldArticle sA = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT laskutunnus, tarvikenumero, alennusprosentti, kappalemaara FROM myyty_tarvike WHERE laskutunnus = ? AND tarvikenumero = ?");
            pStatement.setInt(1, iNmbr);
            pStatement.setInt(2, aNmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
               sA = createSoldArticle(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4)); 
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
        return sA;
    }
    
    public SoldArticle removeSoldArticle(SoldArticle x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM myyty_tarvike WHERE laskutunnus = ? AND tarvikenumero = ?");
            pStatement.setInt(1, x.getInvoiceNmbr());
            pStatement.setInt(2, x.getArticleNmbr());
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
    
    public boolean updateDiscount(SoldArticle x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        boolean oK = false;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("UPDATE myyty_tarvike SET alennusprosentti = ? WHERE laskutunnus = ? AND tarvikenumero = ?");
            pStatement.setInt(1, x.getDiscountPer());
            pStatement.setInt(2, x.getInvoiceNmbr());
            pStatement.setInt(3, x.getArticleNmbr());
            int rV = pStatement.executeUpdate();
            if (rV == 1) {
                oK = true;
            }
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
        return oK;        
    }
}