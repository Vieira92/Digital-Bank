package com.rumos.bank.administration.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;

public class ClientService {
	
	public Boolean verifyTitular(Client client) {
		for(Account account : client.getAccounts()) {
			if(account.getMainTitular() == client) {
				System.out.println("Client can't hold two titular accounts.");
				return true;
			}
		}	
		return false;
	}
	
	public Client newClient() {
		Scanner sc = new Scanner(System.in);
		Client client = new Client();
		
		client.setClientNumber(ADM.clientNumber());
		System.out.println("\nNew Client:");
		System.out.print("Name: ");
		String name = sc.nextLine();
		name = nameFormat(name); // ainda falta na formataçao fazer um split ou assim 
									//para os segundos nomes tambem terem a primeira letra grande
		client.setName(name);
		
		System.out.print("Date of Birth:"
						+ "\nDay: ");
		Integer day = sc.nextInt();
		System.out.print("Month: ");
		Integer month = sc.nextInt();
		System.out.print("Year: ");
		Integer year = sc.nextInt();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.YEAR, year);
		Date birth = calendar.getTime();
		//TODO: falta a verificaçao de menor de idade e melhor separar por metodos
		
		client.setBirth(birth);
		

		System.out.print("NIF: ");
		String nif = sc.next();
		//TODO: falta metodo de verificacao formataçao de nif 
		client.setNif(nif);
	
		System.out.print("Email: ");
		String email = sc.next();
		//TODO: falta metodo de verificacao formataçao de email 
		client.setEmail(email);
		
		System.out.print("Cellphone: ");
		String cellphone = sc.next();
		//TODO: falta metodo de verificacao formataçao de cellphone
		client.setCellphone(cellphone);
		
		System.out.print("Telephone: ");
		String telephone = sc.next();
		//TODO: falta metodo de verificacao formataçao de telephone 
		client.setTelephone(telephone);
		
		System.out.print("Occupation: ");
		String occupation = sc.next();
		occupation = nameFormat(occupation);
		client.setOccupation(occupation);

		addListClients(client);
		return client;
	}
	
	public void editClient(Client client) {
		Scanner sc = new Scanner(System.in);
		//TODO: falta formataçoes
		
		System.out.print("Name: ");
		String name = sc.nextLine();
		name = nameFormat(name);
		client.setName(name);
		
		System.out.print("Email: ");
		String email = sc.next();
		client.setEmail(email);
		
		System.out.print("Cellphone: ");
		String cellphone = sc.next();
		client.setCellphone(cellphone);
		
		System.out.print("Telephone: ");
		String telephone = sc.next();
		client.setTelephone(telephone);
		
		System.out.print("Occupation: ");
		String occupation = sc.next();
		occupation = nameFormat(occupation);
		client.setOccupation(occupation);
		
		System.out.println(client);
	
	}
	
	public String nameFormat(String name) {
		name = name.trim(); 
		name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase().trim();
		return name;
	}
	//---------------ClientsList----------------------------
	
	public void addListClients(Client client) {
		ADM.clients.add(client);
	}
	
	public void removeListClients(Client client) {
		ADM.clients.remove(client);
	}
}

