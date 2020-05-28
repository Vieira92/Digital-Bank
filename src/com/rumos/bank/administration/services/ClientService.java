package com.rumos.bank.administration.services;

import java.time.LocalDate;
import java.time.Period;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;

public class ClientService {
	

	public Client newClient() {

		int clientNumber = ADM.clientNumber();
		System.out.println("\nNew Client:");
		System.out.print("Full Name: ");
		String name = nameFormat(UI.scanLine());

		System.out.print("Date of Birth:" + "\nDay: ");
		int day = UI.getInt();
		System.out.print("Month: ");
		int month = UI.getInt();
		System.out.print("Year: ");
		int year = UI.getInt();

		LocalDate birth = LocalDate.of(year, month, day);
		while(!verifyBirth(birth)) {
			System.out.println("Invalid age, client must have age between 2 and 120 years");
			System.out.println("year, month, day:");
			birth = LocalDate.of(UI.getInt(), UI.getInt(), UI.getInt());
		}


		System.out.print("NIF: ");
		String nif = UI.scanLine(); 
		// TODO: falta metodo de verificacao formataçao de nif

		System.out.print("Email: ");
		String email = UI.scanLine(); //na verificacao falta por que no mail nao pode haver espacos por exemplo
		while (!verifyEmail(email)) {
			System.out.println("Invalid email. write again:");
			email = UI.scanLine(); 
		}
		email.toLowerCase();

		System.out.print("Cellphone: ");
		String cellphone = UI.scanLine();
		// TODO: falta metodo de verificacao formataçao de cellphone

		System.out.print("Telephone: ");
		String telephone = UI.scanLine();
		// TODO: falta metodo de verificacao formataçao de telephone

		System.out.print("Occupation: ");
		String occupation = UI.scanLine();
		occupation = occupation.substring(0, 1).toUpperCase() + occupation.substring(1).toLowerCase();

		Client client = new Client(clientNumber, name, birth, nif, email, cellphone, telephone, occupation);
		
		return client;
	}

	public void editClient(Client client) {
		// TODO: falta formataçoes e melhor por menu

		System.out.print("\nName: ");
		String name = nameFormat(UI.scanLine());
		client.setName(name);

		System.out.print("Email: ");
		String email = UI.scanLine(); 
		client.setEmail(email);

		System.out.print("Cellphone: ");
		String cellphone = UI.scanLine(); 
		client.setCellphone(cellphone);

		System.out.print("Telephone: ");
		String telephone = UI.scanLine(); 
		client.setTelephone(telephone);

		System.out.print("Occupation: ");
		String occupation = nameFormat(UI.scanLine());
		client.setOccupation(occupation);

		System.out.println(client);

	}

	public String nameFormat(String name) {
		String[] fields = name.strip().split(" ");
		String firstName = fields[0];
		firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
		if(fields.length > 1) {
			String lastName = fields[fields.length - 1];
			lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
			name = firstName + " " + lastName;
			return name;
		}
		name = firstName;
		return name;
	}

	public Boolean verifyBirth(LocalDate birth) {
		LocalDate now = LocalDate.now();
		Period period = Period.between(birth, now);
		if(period.getYears() >= 2 && period.getYears() < 120) {
			return true;
		}
		else {
			return false;	
		}
	}
	
	public Boolean verifyAge(Client client) {
		LocalDate now = LocalDate.now();
		Period period = Period.between(client.getBirth(), now);
		if(period.getYears() >= 18) {
			return true;
		}
		else {
			System.out.println("\nClient must have 18 years"
					+ "\ncan't be an account Titular");
			return false;	
		}
	}

	public Boolean verifyEmail(String email) {
		char someChar = '@';
		int count = 0;
		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == someChar) {
				count++;
			}
		}
		if (count == 1 && email.contains(".")) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean verifyTitular(Client client) {
		for (Account account : client.getAccounts()) {
			if (account.getMainTitular() == client) {
				System.out.println("\nClient can't hold two titular accounts.");
				return true;
			}
		}
		return false;
	}
	// ---------------ClientsList----------------------------

	public void addListClients(Client client) {
		ADM.clients.add(client);
	}

	public void removeListClients(Client client) {
		ADM.clients.remove(client);
	}
}

