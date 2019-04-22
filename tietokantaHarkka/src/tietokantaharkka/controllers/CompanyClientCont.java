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
    
    public CompanyClient createCompanyClient(String name, int yIdentifier, int nmbr, int locationNmbr) {
        CompanyClient x = new CompanyClient(name, yIdentifier, nmbr, locationNmbr);
        recentCompanyClients.add(x);
        lastUsed = x;
        return x;
    }

    public void addNewCompanyClient(CompanyClient x, Connection con) throws SQLException{
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("INSERT INTO asiakas(osoitenumero) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, x.getLocationNmbr());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            resultSet.last();
            pStatement.clearParameters();
            pStatement = con.prepareStatement("INSERT INTO yritys(asiakasnumero, y-tunnus, nimi) VALUES(?, ?, ?)");
            pStatement.setInt(1, resultSet.getInt(1));
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
	//Idllä
    public CompanyClient findCompanyClient(int nmbr, Connection con) throws SQLException{
        CompanyClient cC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT yritys.asiakasnumero, yritys.y-tunnus, yritys.nimi, asiakas.osoitenumero FROM yritys, asiakas WHERE asiakas.asiakasnumero = yritys.asiakasnumero AND yritys.asiakasnumero = ?");
            pStatement.setInt(1, nmbr);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                cC = createCompanyClient(resultSet.getString(3), resultSet.getInt(2), resultSet.getInt(1), resultSet.getInt(4));
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
        return cC;
    }
    

    //Nimellä
    public ArrayList<CompanyClient> findCompanyClientByName(String name, Connection con) throws SQLException{
        ArrayList<CompanyClient> cCAL = new ArrayList<CompanyClient>();
	CompanyClient cC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
	int rows = 0;
        try {
            con.setAutoCommit(false);
	    pStatement = con.prepareStatement("SELECT COUNT(*) FROM yritys WHERE nimi = ?");
	    pStatement.setString(1, name);
	    resultSet = pStatement.executeQuery();
	    resultSet.next();
            rows = resultSet.getInt(1);
	    for (int i = 0; i < rows; i++) {
	        pStatement = con.prepareStatement("SELECT yritys.asiakasnumero, y-tunnus, nimi, asiakas.osoitenumero, ROW_NUMBER() over (ORDER BY asiakasnumero) as rownum FROM yritys, asiakas WHERE asiakas.asiakasnumero = yritys.asiakasnumero AND nimi = ? AND rownum = ?");
	        pStatement.setString(1, name);
		pStatement.setInt(2, i+1);
                resultSet = pStatement.executeQuery();
                resultSet.next();
		cC = createCompanyClient(resultSet.getString(3), resultSet.getInt(2), resultSet.getInt(1), resultSet.getInt(4));
		cCAL.add(cC);
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
        return cCAL;
    }


    //ytunnuksella
    public CompanyClient findCompanyClientByYIdentifier(int yIdentifier, Connection con) throws SQLException{
        CompanyClient cC = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("SELECT yritys.asiakasnumero, yritys.y-tunnus, yritys.nimi, asiakas.osoitenumero FROM yritys, asiakas WHERE asiakas.asiakasnumero = yritys.asiakasnumero AND yritys.y-tunnus = ?");
            pStatement.setInt(1, yIdentifier);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                cC = createCompanyClient(resultSet.getString(3), resultSet.getInt(2), resultSet.getInt(1), resultSet.getInt(4));
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
        return cC;
    }

    public CompanyClient removeCompanyClient(CompanyClient x, Connection con) throws SQLException {
        PreparedStatement pStatement = null;
        try {
            con.setAutoCommit(false);
            pStatement = con.prepareStatement("DELETE FROM yritys WHERE asiakasnumero = ?");
            pStatement.setInt(1, x.getNmbr());
            pStatement.executeUpdate();
            pStatement.clearParameters();
            pStatement = con.prepareStatement("DELETE FROM asiakas WHERE asiakasnumero = ?");
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