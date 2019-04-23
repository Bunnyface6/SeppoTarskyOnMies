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
    
    public SeppoCompany seppoInfo() {
        SeppoCompany seppo = new SeppoCompany();
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
