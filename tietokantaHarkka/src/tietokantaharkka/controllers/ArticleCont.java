/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.Article;
import java.sql.*;
import tietokantaharkka.baseClasses.ArticleType;

/**
 * Tarvike controlleri.
 * @author Jipsu
 */
public class ArticleCont {
    
     /** Viimeisimmät tarvikkeet */
    private ArrayList<Article> recentClients = new ArrayList<Article>();
    
    /** Viimeisin tarvike */
    private Article lastUsed;
    
    /**
     * Luo uuden tarvikkeen. Asettaa luodun olion viimeisimpien 
     * olioiden listalle ja viimeksimmän olion viitteeseen.
     * 
     * @param name nimi
     * @param buyIn sisäänostohinta
     * @param storage varastotilanne
     * @param salePrice myyntihinta
     * @param nmbr tyyppiyksikkonumero
     * @param nmbr2 tarvikenumero
     * @param unit yksikkö
     * @param typeName tyyppi
     * @return luotu tarvike-olio
     */
    public Article createArticle(String name, double buyIn, int storage, double salePrice, int nmbr, int nmbr2, String unit, String typeName){
        Article x = new Article(name, buyIn, storage, salePrice, nmbr, nmbr2, unit, typeName);
	recentClients.add(x);
	lastUsed = x;
	return x;
    }
    
    /**
     * Lisää tietokannan tarvike-tauluun uuden tarvikkeen parametrina saadun olion attribuuttien arvojen perusteella.
     * 
     * @param x viite tarvike olioon
     * @param con viite tietokannan yhteys olioon
     * @throws SQLException jos lisäyksessä tapahtuu virhe
     */
    public void addNewArticle(Article x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("INSERT INTO tarvike(sisaanostohinta, nimi, varastotilanne, myyntihinta, tyyppiyksikkonumero) VALUES(?, ?, ?, ?, ?)");
            pStatement.setDouble(1, x.getBuyIn());
            pStatement.setString(2, x.getName());
            pStatement.setInt(3, x.getStorage());
            pStatement.setDouble(4, x.getSalePrice());
            if(x.getNmbr() != 0){
                pStatement.setInt(5, x.getNmbr());
            }
            else{
                ArticleTypeCont aTC = new ArticleTypeCont();
                ArticleType ArtT = aTC.findArticleTypeByTypeName(x.getTypeName(), con);
                pStatement.setInt(5, ArtT.getNmbr());
            }
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
     * tarvikenumeron omaavan tarvikkeen tietoja tarvike-taulusta ja tyyppiyksikkö-taulusta.
     * 
     * @param nmbr tarvikenumero
     * @param con viite tietokannan yhteys olioon
     * @return tarvike-olio, jolla tietokannan tarvike-taulun ja tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */		
    public Article findArticleByNmbr(int nmbr, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND tarvikenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }

    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tarvikeen nimen omaavan tarvikkeen tietoja tarvike-taulusta ja tyyppiyksikkö-taulusta.
     * 
     * @param name tarvikeen nimi
     * @param con viite tietokannan yhteys olioon
     * @return tarvike-olio, jolla tietokannan tarvike-taulun ja tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public Article findArticleByName(String name, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tarvikenumero, sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.nimi = ?");
            pStatement.setString(1, name);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }

    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tarvikeen sisäänostohinnan omaavien tarvikkeiden tietoja tarvike-taulusta ja tyyppiyksikkö-taulusta.
     * 
     * @param buyIn tarvikeen sisäänostohinta
     * @param con viite tietokannan yhteys olioon
     * @return lista tarvike-olioista, joilla tietokannan tarvike-taulun ja tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public ArrayList<Article> findArticleByBuyIn(double buyIn, Connection con) throws SQLException {
        ArrayList<Article> a = new ArrayList<Article>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND sisaanostohinta = ?");
            pStatement.setDouble(1, buyIn);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                a.add(createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7)));
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
        return a;
    }

    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tarvikeen maaran omaavien tarvikkeiden tietoja tarvike-taulusta ja tyyppiyksikkö-taulusta.
     * 
     * @param storage tarvikeen maara
     * @param con viite tietokannan yhteys olioon
     * @return lista tarvike-olioista, joilla tietokannan tarvike-taulun ja tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public ArrayList<Article> findArticleByStorage(int storage, Connection con) throws SQLException {
        ArrayList<Article> a = new ArrayList<Article>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND varastotilanne = ?");
            pStatement.setInt(1, storage);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                a.add(createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7)));
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
        return a;
    }

    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tarvikeen myyntihinnan omaavien tarvikkeiden tietoja tarvike-taulusta ja tyyppiyksikkö-taulusta.
     * 
     * @param salePrice tarvikeen myyntihinta
     * @param con viite tietokannan yhteys olioon
     * @return lista tarvike-olioista, joilla tietokannan tarvike-taulun ja tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public ArrayList<Article> findArticleBySalePrice(double salePrice, Connection con) throws SQLException {
        ArrayList<Article> a = new ArrayList<Article>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND myyntihinta = ?");
            pStatement.setDouble(1, salePrice);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                a.add(createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7)));
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
        return a;
    }

    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tarvikeen yksikön omaavien tarvikkeiden tietoja tarvike-taulusta ja tyyppiyksikkö-taulusta.
     * 
     * @param unit tarvikeen yksikkö
     * @param con viite tietokannan yhteys olioon
     * @return lista tarvike-olioista, joilla tietokannan tarvike-taulun ja tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public ArrayList<Article> findArticleByUnit(String unit, Connection con) throws SQLException {
        ArrayList<Article> a = new ArrayList<Article>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND yksikko = ?");
            pStatement.setString(1, unit);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                a.add(createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7)));
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
        return a;
    }


    /**
     * Suorittaa tietokantaan kyselyn, jonka avulla haetaan parametrin osoittaman
     * tarvikeen tyypin omaavien tarvikkeiden tietoja tarvike-taulusta ja tyyppiyksikkö-taulusta.
     * 
     * @param typeName tarvikeen tyyppi
     * @param con viite tietokannan yhteys olioon
     * @return lista tarvike-olioista, joilla tietokannan tarvike-taulun ja tyyppiyksikkötaulun tiedot
     * @throws SQLException jos kysely aiheuttaa virheen
     */
    public ArrayList<Article> findArticleByTypeName(String typeName, Connection con) throws SQLException {
        ArrayList<Article> a = new ArrayList<Article>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND tyyppi = ?");
            pStatement.setString(1, typeName);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                a.add(createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7)));
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
        return a;
    }
    
    /**
     * Etsii kaikki tarvikkeet tietokannasta.
     * 
     * @param con viite tietokannan yhteys-oilioon
     * @return lista kaikista tarvikkeista
     * @throws SQLException jos haku ei onnistu
     */
    public ArrayList<Article> findAllArcticles(Connection con) throws SQLException {
        ArrayList<Article> a = new ArrayList<Article>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero");
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                a.add(createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7)));
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
        return a;
    }
    
    /**
     * Poistaa patametrina saadun tarvikeolion avulla olion kanssa samat tiedot omaavan rivin tietokannan tarvike-taulusta.
     * 
     * @param x viite tarvike-olioon
     * @param con viite tietokannan yhteys-olioon
     * @return viite tarvike-olioon, jonka tiedot tietokannasta on poistettu
     * @throws SQLException jos poisto aiheuttaa virheen
     */
    public Article removeArticle(Article x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("DELETE FROM tarvike WHERE tarvikenumero = ?");
            pStatement.setInt(1, x.getNmbr2());
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
     * Päivittää parametrina saadun tarvike-olion avulla olion kanssa samat tiedot omaavaa tietokannan tarvike-taulun riviä.
     * Riviltä päivitetään sisäänostohinta, varastotilanne ja myyntihinta.
     * 
     * @param x viite tarvike-olioon
     * @param con viite tietokannan yhteys-olioon
     * @throws SQLException jos päivityksessä tapahtuu virhe
     */
    public void updateArticle(Article x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("UPDATE tarvike SET sisaanostohinta = sisaanostohinta + ?, varastotilanne = varastotilanne + ?, myyntihinta = myyntihinta + ? WHERE tarvikenumero = ?");
            pStatement.setDouble(1, x.getBuyIn());
            pStatement.setInt(2, x.getStorage());
            pStatement.setDouble(3, x.getSalePrice());
            if(x.getNmbr() != 0){
                pStatement.setInt(4, x.getNmbr2());
            }
            else{
                ArticleTypeCont aTC = new ArticleTypeCont();
                ArticleType ArtT = aTC.findArticleTypeByTypeName(x.getTypeName(), con);
                pStatement.setInt(4, ArtT.getNmbr());
            }
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }    	
    }
    public void updateArticle2(Article x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            pStatement = con.prepareStatement("UPDATE tarvike SET sisaanostohinta = ?, varastotilanne =  ?, myyntihinta = ? WHERE tarvikenumero = ?");
            pStatement.setDouble(1, x.getBuyIn());
            pStatement.setInt(2, x.getStorage());
            pStatement.setDouble(3, x.getSalePrice());
            pStatement.setInt(4, x.getNmbr2());
            pStatement.executeUpdate();
        }
        finally {
            if (pStatement != null) {
                pStatement.close();
            }
        }    	
    }
}