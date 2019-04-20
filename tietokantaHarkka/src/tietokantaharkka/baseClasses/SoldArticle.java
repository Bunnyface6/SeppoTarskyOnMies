/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.baseClasses;

/**
 *
 * @author Cassu
 */
public class SoldArticle {
    
    private int invoiceNmbr;
    private int articleNmbr;
    private int discountPer;
    private int nmbrOfSold;

    public SoldArticle(int invoiceNmbr, int articleNmbr, int discountPer, int nmbrOfSold) {
        this.invoiceNmbr = invoiceNmbr;
        this.articleNmbr = articleNmbr;
        this.discountPer = discountPer;
        this.nmbrOfSold = nmbrOfSold;
    }

    public int getInvoiceNmbr() {
        return invoiceNmbr;
    }

    public void setInvoiceNmbr(int invoiceNmbr) {
        this.invoiceNmbr = invoiceNmbr;
    }

    public int getArticleNmbr() {
        return articleNmbr;
    }

    public void setArticleNmbr(int articleNmbr) {
        this.articleNmbr = articleNmbr;
    }

    public int getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(int discountPer) {
        this.discountPer = discountPer;
    }

    public int getNmbrOfSold() {
        return nmbrOfSold;
    }

    public void setNmbrOfSold(int nmbrOfSold) {
        this.nmbrOfSold = nmbrOfSold;
    }
    
    
}
