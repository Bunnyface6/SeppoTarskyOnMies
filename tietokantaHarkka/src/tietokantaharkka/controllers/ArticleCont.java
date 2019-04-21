/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.Article;
import java.sql.*;

public class ArticleCont {
	
    private ArrayList<Article> recentClients = new ArrayList<Article>();
    private Article lastUsed;
	
    public Article createArticle(String name, double buyIn, int storage, double salePrice, int nmbr, int nmbr2, String unit, String typeName){
        Article x = new Article(name, buyIn, storage, salePrice, nmbr, nmbr2, unit, typeName);
	recentClients.add(x);
	lastUsed = x;
	return x;
    }
	
    public void addNewArticle(Article x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO tarvike(sisaanostohinta, nimi, varastotilanne, myyntihinta, tyyppiyksikkonumero) VALUES(?, ?, ?, ?, ?)");
            pStatement.setDouble(1, x.getBuyIn());
            pStatement.setString(2, x.getName());
            pStatement.setInt(3, x.getStorage());
            pStatement.setDouble(4, x.getSalePrice());
            pStatement.setInt(5, x.getNmbr());
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
    
	//ID:llä	
    public Article findArticleByNmbr(int nmbr, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            // Tarkistettava, meneekö näin.
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND tarvikenumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }

	//Nimellä
    public Article findArticleByName(String name, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            // Tarkistettava, meneekö näin.
            pStatement = con.prepareStatement("SELECT tarvikenumero, sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.nimi = ?");
            pStatement.setString(1, name);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }

	// sisäänostolla
    public Article findArticleByBuyIn(double buyIn, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            // Tarkistettava, meneekö näin.
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND sisaanostohinta = ?");
            pStatement.setDouble(1, buyIn);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }

	//Varastolla
    public Article findArticleByStorage(int storage, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            // Tarkistettava, meneekö näin.
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND varastotilanne = ?");
            pStatement.setInt(1, storage);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }

	//Myyntihinnalla
    public Article findArticleBySalePrice(double salePrice, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            // Tarkistettava, meneekö näin.
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND myyntihinta = ?");
            pStatement.setDouble(1, salePrice);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }

	//Yksiköllä
    public Article findArticleByUnit(String unit, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            // Tarkistettava, meneekö näin.
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND yksikko = ?");
            pStatement.setString(1, unit);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }


    //Tyypillä
    public Article findArticleByTypeName(String typeName, Connection con) throws SQLException {
        Article a = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            // Tarkistettava, meneekö näin.
            pStatement = con.prepareStatement("SELECT tarvikenumero ,sisaanostohinta, nimi, varastotilanne, myyntihinta, tarvike.tyyppiyksikkonumero, tyyppi, yksikko " 
                                              + "FROM tarvike, tyyppiyksikko WHERE tarvike.tyyppiyksikkonumero = tyyppiyksikko.tyyppiyksikkonumero AND tyyppi = ?");
            pStatement.setString(1, typeName);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                a = createArticle(resultSet.getString(3), resultSet.getDouble(2), resultSet.getInt(4), resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(1), resultSet.getString(8), resultSet.getString(7));
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
        return a;
    }
	
    //public Article findClient(){
	//Jatka
    //}
	
    public Article removeArticle(Article x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM tarvike WHERE tarvikenumero = ?");
            pStatement.setInt(1, x.getNmbr2());
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
    
    public void updateArticle(Article x, Connection con) throws SQLException {
	PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("UPDATE tarvike SET sisaanostohinta = ?,varastotilanne = ?, myyntihinta = ? WHERE tarvikenumero = ?");
            pStatement.setDouble(1, x.getBuyIn());
            pStatement.setInt(2, x.getStorage());
            pStatement.setDouble(3, x.getSalePrice());
            pStatement.setInt(4, x.getNmbr2());
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
}
