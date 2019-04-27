/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;
import java.sql.*;
import tietokantaharkka.baseClasses.ArticleType;
import java.util.ArrayList;

/**
 * Tyyppiyksikkö controlleri.
 * @author Jipsu
 */
public class ArticleTypeCont {
    
    /** Viimeisimmät tyyppiyksiköt */
    private ArrayList<ArticleType> recentArticleTypes = new ArrayList<ArticleType>();
    
    /** Viimeisin tyyppiyksikkö */
    private ArticleType lastUsed;
    
    /**
     * Luo uuden tyyppiyksikön. Asettaa luodun olion viimeisimpien 
     * olioiden listalle ja viimeksimmän olion viitteeseen.
     * 
     * @param nmbr tyyppiyksikkönumero
     * @param unit yksikkö
     * @param typeName tyyppi
     * @return luotu tyyppiyksikkö olio
     */
    public ArticleType createArticleType(int nmbr, String unit, String typeName) {
        ArticleType x = new ArticleType(nmbr, unit, typeName);
        recentArticleTypes.add(x);
        lastUsed = x;
        return x;
    }
    
    /**
     * Lisää tietokannan tyyppiyksikkö-tauluun uuden tyyppiyksikön parametrina saadun olion attribuuttien arvojen perusteella.
     * 
     * @param x viite tyyppiyksikkö-olioon
     * @param con viite tietokannan yhteys olioon
     * @throws SQLException jos lisäyksessä tapahtuu virhe
     */
    public void addNewArticleType(ArticleType x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("INSERT INTO tyyppiyksikko(tyyppiyksikkonumero, tyyppi, yksikko) VALUES(?, ?, ?)");
            pStatement.setInt(1, x.getNmbr());
            pStatement.setString(2, x.getTypeName());
            pStatement.setString(3, x.getUnit());
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
    }
    
    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tyyppiyksikkönumeron omaavan tyyppiyksikön tietoja tyyppiyksikkö-taulusta.
     * 
     * @param nmbr tyyppiyksikkönumero
     * @param con viite tietokannan yhteys olioon
     * @return tyyppiyksikkö-olio, jolla tietokannan tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */		
    public ArticleType findArticleTypeByNmbr(int nmbr, Connection con) throws SQLException{
        ArticleType aT = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyyppiyksikkonumero, tyyppi, yksikko FROM tyyppiyksikko WHERE tyyppiyksikkonumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                aT = createArticleType(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2));
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
        return aT;
    }


    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tyyppiyksikön yksikön omaavien tyyppiyksiköiden tietoja ttyyppiyksikkö-taulusta.
     * 
     * @param unit tyyppiyksikön yksikkö
     * @param con viite tietokannan yhteys olioon
     * @return lista tyyppiyksikkö-olioista, joilla tietokannan tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public ArrayList<ArticleType> findArticleTypeByUnit(String unit, Connection con) throws SQLException{
        ArrayList<ArticleType> aT = new ArrayList<ArticleType>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyyppiyksikkonumero, tyyppi, yksikko FROM tyyppiyksikko WHERE yksikko = ?");
            pStatement.setString(1, unit);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                aT.add(createArticleType(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2)));
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
        return aT;
    }
    
    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tyyppiyksikön tietoja tyyppiyksikkö-taulusta.
     * 
     * @param typeName tyyppiyksikön tyyppi
     * @param con viite tietokannan yhteys olioon
     * @return lista tyyppiyksikkö-olioista, joilla tietokannan tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public ArticleType findArticleTypeByTypeName(String typeName, Connection con) throws SQLException{
        ArticleType aT = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyyppiyksikkonumero, tyyppi, yksikko FROM tyyppiyksikko WHERE tyyppi = ?");
            pStatement.setString(1, typeName);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                aT = createArticleType(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2));
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
        return aT;
    }
    
    /**
     * Hakee tietokannasta kaikki tyyppiyksiköt.
     * 
     * @param con viite tietokannan yhteys olioon
     * @return lista tyyppiyksikkö-olioista, joilla tietokannan tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public ArrayList<ArticleType> findArticleTypes(Connection con) throws SQLException{
        ArrayList<ArticleType> aT = new ArrayList<ArticleType>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tyyppiyksikkonumero, tyyppi, yksikko FROM tyyppiyksikko;");
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                aT.add(createArticleType(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2)));
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
        return aT;
    }
    
    /**
     * Poistaa tietokannasta parametrina saadun tyyppiyksikkö-olion mukaisen tyyppiyksikön.
     * 
     * @param x tyyppiyksikkö-olio
     * @param con tietokannan yhteys-olio
     * @return poistettu tyyppiyksikkö-olio
     * @throws SQLException jos poisto epäonnistuu
     */
    public ArticleType removeArticleType(ArticleType x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM tyyppiyksikko WHERE tyyppiyksikkonumero = ?");
            pStatement.setInt(1, x.getNmbr());
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }
        return x;    
    }
}
