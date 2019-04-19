/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tietokantaharkka.controllers;
import java.sql.*;
import tietokantaharkka.baseClasses.CompanyClient;
import java.util.ArrayList;

/**
 *
 * @author Jipsu
 */
public class CompanyClientCont {

    private ArrayList<CompanyClient> recentCompanyClients = new ArrayList<CompanyClient>();
    
    private CompanyClient lastUsed;
    
    public CompanyClient createCompanyClient(String name, int yIdentifier, int nmbr) {
        CompanyClient x = new CompanyClient(name, yIdentifier, nmbr);
        recentCompanyClients.add(x);
        lastUsed = x;
        return x;
    }

    public void addNewCompanyClient(CompanyClient x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO yritys(asiakasnumero, y-tunnus, nimi) VALUES(?, ?, ?)");
            pStatement.setInt(1, x.getNmbr());
            pStatement.setInt(2, x.getyIdentifier());
            pStatement.setString(2, x.getName());
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

    public CompanyClient findCompanyClient(int nmbr, Connection con) throws SQLException{
        CompanyClient cC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT asiakasnumero, y-tunnus, nimi FROM yritys WHERE asiakasnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            cC = createCompanyClient(resultSet.getString(3), resultSet.getInt(2), resultSet.getInt(1));
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
        return cC;
    }
    
    public CompanyClient removeCompanyClient(CompanyClient x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM yritys WHERE asiakasnumero = ?");
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
