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
 *
 * @author nico
 */
public class SeppoCompanyCont {
    
    public SeppoCompany seppoInfo(Connection con) throws SQLException {
        SeppoCompany seppo = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
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
        return seppo;
    };
    
    public void modifyAddress(String address){
    
    };
    public void modifyWorkVAT(double newWVAT){
    
    };
    public void modifyArticleVAT(double newAVAT){
    
    };
    public void modifyBookVAT(double newBVAT){
    
    };
    public void modifyCompanyFee(int newCompanyFee){
    
    };
    public void modifyConsumerFee(int newConsumerFee){
    
    };
    public void modifyConsumerInterest(double newConsumerInterest){
    
    };
    public void modifyCompanyInterest(double newCompanyInterest){
    
    };
}
