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
 *
 * @author Jipsu
 */
public class ArticleTypeCont {

    private ArrayList<ArticleType> recentArticleTypes = new ArrayList<ArticleType>();
    
    private ArticleType lastUsed;
    
    public ArticleType createArticleType(int nmbr, String unit, String typeName) {
        ArticleType x = new ArticleType(nmbr, unit, typeName);
        recentArticleTypes.add(x);
        lastUsed = x;
        return x;
    }

    public void addNewArticleType(ArticleType x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO tyyppiyksikko(tyyppiyksikkonumero, tyyppi, yksikko) VALUES(?, ?, ?)");
            pStatement.setInt(1, x.getNmbr());
            pStatement.setString(2, x.getTypeName());
            pStatement.setString(3, x.getUnit());
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


    //IDllä
    public ArticleType findArticleTypeByNmbr(int nmbr, Connection con) throws SQLException{
        ArticleType aT = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyyppiyksikkonumero, tyyppi, yksikko FROM tyyppiyksikko WHERE tyyppiyksikkonumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                aT = createArticleType(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2));
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
        return aT;
    }


    //Yksiköllä
    public ArrayList<ArticleType> findArticleTypeByUnit(String unit, Connection con) throws SQLException{
        ArrayList<ArticleType> aT = new ArrayList<ArticleType>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyyppiyksikkonumero, tyyppi, yksikko FROM tyyppiyksikko WHERE yksikko = ?");
            pStatement.setString(1, unit);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                aT.add(createArticleType(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2)));
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
        return aT;
    }


    //tyypillä
    public ArticleType findArticleTypeByTypeName(String typeName, Connection con) throws SQLException{
        ArticleType aT = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyyppiyksikkonumero, tyyppi, yksikko FROM tyyppiyksikko WHERE tyyppi = ?");
            pStatement.setString(1, typeName);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                aT = createArticleType(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2));
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
        return aT;
    }
    
    public ArrayList<ArticleType> findArticleTypes(Connection con) throws SQLException{
        ArrayList<ArticleType> aT = new ArrayList<ArticleType>();
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT tyyppiyksikkonumero, tyyppi, yksikko FROM tyyppiyksikko;");
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                aT.add(createArticleType(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2)));
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
        return aT;
    }
    
    public ArticleType removeArticleType(ArticleType x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM tyyppiyksikko WHERE tyyppiyksikkonumero = ?");
            pStatement.setInt(1, x.getNmbr());
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
}
