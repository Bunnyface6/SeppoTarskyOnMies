/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaharkka.controllers;

import java.util.ArrayList;
import tietokantaharkka.baseClasses.Client;

public class ClientCont{
	
	private ArrayList<Client> recentClients = new ArrayList<Client>();
	private Client lastUsed;
	
	public Client createClient(int nmbr){
		Client x = new Client(nmbr);
		recentClients.add(x);
		lastUsed = x;
		return x;
		//Jatka
	}
	
	public void addNewClient(){
		//Jatka
	}
	
	public Client findClient() {
		//Jatka
	}
	
	public Client findClient(){
		//Jatka
	}
	
	public Client removeClient(){
		//Jatka
	}
	
}
