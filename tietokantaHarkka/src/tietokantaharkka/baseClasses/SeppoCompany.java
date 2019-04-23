/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.baseClasses;

/**
 *
 * @author nico
 */
public class SeppoCompany {
    private String name;
    private int yIdentifier;
    private String address;
    private double workVAT;
    private double articleVAT;
    private double bookVAT;
    private int consumerFee;
    private int companyFee;
    private double companyInterest;
    private double consumerInterest;

    public SeppoCompany(String name, int yIdentifier, String address, double workVAT, double articleVAT, double bookVAT, int consumerFee, int companyFee, double companyInterest, double consumerInterest) {
        this.name = name;
        this.yIdentifier = yIdentifier;
        this.address = address;
        this.workVAT = workVAT;
        this.articleVAT = articleVAT;
        this.bookVAT = bookVAT;
        this.consumerFee = consumerFee;
        this.companyFee = companyFee;
        this.companyInterest = companyInterest;
        this.consumerInterest = consumerInterest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getyIdentifier() {
        return yIdentifier;
    }

    public void setyIdentifier(int yIdentifier) {
        this.yIdentifier = yIdentifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getWorkVAT() {
        return workVAT;
    }

    public void setWorkVAT(double workVAT) {
        this.workVAT = workVAT;
    }

    public double getArticleVAT() {
        return articleVAT;
    }

    public void setArticleVAT(double articleVAT) {
        this.articleVAT = articleVAT;
    }

    public double getBookVAT() {
        return bookVAT;
    }

    public void setBookVAT(double bookVAT) {
        this.bookVAT = bookVAT;
    }

    public int getConsumerFee() {
        return consumerFee;
    }

    public void setConsumerFee(int consumerFee) {
        this.consumerFee = consumerFee;
    }

    public int getCompanyFee() {
        return companyFee;
    }

    public void setCompanyFee(int companyFee) {
        this.companyFee = companyFee;
    }

    public double getCompanyInterest() {
        return companyInterest;
    }

    public void setCompanyInterest(double companyInterest) {
        this.companyInterest = companyInterest;
    }

    public double getConsumerInterest() {
        return consumerInterest;
    }

    public void setConsumerInterest(double consumerInterest) {
        this.consumerInterest = consumerInterest;
    }
    
    
}
