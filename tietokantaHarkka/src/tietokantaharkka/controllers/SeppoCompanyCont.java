/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;
import java.sql.*;
import tietokantaharkka.baseClasses.SeppoCompany;
import java.util.ArrayList;
import java.util.Date;
/**
 * Sepon tietoihin kontrolleri
 * @author nico
 */
public class SeppoCompanyCont {
    
    /**
     * luo seppo-olion (hakee tiedot)
     * @param con
     * @return sepon tiedot -olion
     * @throws SQLException jos virhe
     */
    public SeppoCompany seppoInfo(Connection con) throws SQLException {
        SeppoCompany seppo = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            
            pStatement = con.prepareStatement("SELECT * FROM sepon_firma"); 
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                seppo = new SeppoCompany(resultSet.getString(1),
                                         resultSet.getInt(2),
                                         resultSet.getString(3),
                                         resultSet.getDouble(4),
                                         resultSet.getDouble(5),
                                         resultSet.getDouble(6),
                                         resultSet.getInt(7),
                                         resultSet.getInt(8),
                                         resultSet.getDouble(9),
                                         resultSet.getDouble(10));
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
        return seppo;
    };
    
    /**
     * muokkaa sepon osoitetta
     * @param newAddress
     * @param con
     * @throws SQLException jos virhe
     */
    public void modifyAddress(String newAddress, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        SeppoCompany seppo = seppoInfo(con);
        try {
            
            pStatement = con.prepareStatement("UPDATE sepon_firma SET osoite = '?' WHERE osoite = '?'");
            pStatement.setString(1, newAddress);
            pStatement.setString(2, seppo.getAddress());
            pStatement.executeUpdate();
            
        }
        
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    };

    /**
     * muokkaa työ-alvia
     * @param newWVAT
     * @param con
     * @throws SQLException jos virhe
     */
    public void modifyWorkVAT(double newWVAT, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        SeppoCompany seppo = seppoInfo(con);
        try {
            
            pStatement = con.prepareStatement("UPDATE sepon_firma SET tyoalv = ? WHERE tyoalv = ?");
            pStatement.setDouble(1, newWVAT);
            pStatement.setDouble(2, seppo.getWorkVAT());
            pStatement.executeUpdate();
            
        }
        
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    };

    /**
     * muokkaa tarvike-alvia
     * @param newAVAT
     * @param con
     * @throws SQLException jos virhe
     */
    public void modifyArticleVAT(double newAVAT, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        SeppoCompany seppo = seppoInfo(con);
        try {
            
            pStatement = con.prepareStatement("UPDATE sepon_firma SET tarvikealv = ? WHERE tarvikealv = ?");
            pStatement.setDouble(1, newAVAT);
            pStatement.setDouble(2, seppo.getArticleVAT());
            pStatement.executeUpdate();
            
        }
        
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    };

    /**
     * muokkaa kirja-alvia
     * @param newBVAT
     * @param con
     * @throws SQLException jos virhe
     */
    public void modifyBookVAT(double newBVAT, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        SeppoCompany seppo = seppoInfo(con);
        try {
            
            pStatement = con.prepareStatement("UPDATE sepon_firma SET kirjaalv = ? WHERE kirjaalv = ?");
            pStatement.setDouble(1, newBVAT);
            pStatement.setDouble(2, seppo.getBookVAT());
            pStatement.executeUpdate();
            
        }
        
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    };

    /**
     * muokkaa muistutuskuluja yrityksille
     * @param newCompanyFee
     * @param con
     * @throws SQLException jos virhe
     */
    public void modifyCompanyFee(int newCompanyFee, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        SeppoCompany seppo = seppoInfo(con);
        try {
            
            pStatement = con.prepareStatement("UPDATE sepon_firma SET yrityskulu = ? WHERE yrityskulu = ?");
            pStatement.setInt(1, newCompanyFee);
            pStatement.setInt(2, seppo.getCompanyFee());
            pStatement.executeUpdate();
            
        }
        
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    };

    /**
     * muokkaa muikkarikuluja kuluttajille
     * @param newConsumerFee
     * @param con
     * @throws SQLException jos virhe
     */
    public void modifyConsumerFee(int newConsumerFee, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        SeppoCompany seppo = seppoInfo(con);
        try {
            
            pStatement = con.prepareStatement("UPDATE sepon_firma SET kuluttajakulu = ? WHERE kuluttajakulu = ?");
            pStatement.setInt(1, newConsumerFee);
            pStatement.setInt(2, seppo.getConsumerFee());
            pStatement.executeUpdate();
            
        }
        
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    };

    /**
     * muokkaa viivästyskorkoa kuluttajille
     * @param newConsumerInterest
     * @param con
     * @throws SQLException jos virhe
     */
    public void modifyConsumerInterest(double newConsumerInterest, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        SeppoCompany seppo = seppoInfo(con);
        try {
            
            pStatement = con.prepareStatement("UPDATE sepon_firma SET kuluttajakorko = ? WHERE kuluttajakorko = ?");
            pStatement.setDouble(1, newConsumerInterest);
            pStatement.setDouble(2, seppo.getConsumerInterest());
            pStatement.executeUpdate();
            
        }
        
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    };

    /**
     * muokkaa viivästyskorkoa yrityksille
     * @param newCompanyInterest
     * @param con
     * @throws SQLException jos virhe
     */
    public void modifyCompanyInterest(double newCompanyInterest, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        SeppoCompany seppo = seppoInfo(con);
        try {
            
            pStatement = con.prepareStatement("UPDATE sepon_firma SET yrityskorko = ? WHERE yrityskorko = ?");
            pStatement.setDouble(1, newCompanyInterest);
            pStatement.setDouble(2, seppo.getCompanyInterest());
            pStatement.executeUpdate();
           
        }
      
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    };

    /**
     *
     */
    public void modifySeppo(){};
}
