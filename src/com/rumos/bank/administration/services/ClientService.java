package com.rumos.bank.administration.services;

import java.time.LocalDate;
import java.time.Period;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;

public class ClientService {

	public void addListClients(Client client) {
		ADM.clients.add(client);
	}

	public void removeListClients(Client client) {
		ADM.clients.remove(client);
	}

	public Client newClient(String name, LocalDate birth, String nif, String email, String cellphone, String telephone,
			String occupation) {
		int clientNumber = ADM.clientNumber();

		Client client = new Client(clientNumber, name, birth, nif, email, cellphone, telephone, occupation);

		addListClients(client);
		return client;
	}

	public void editName(Client client, String name) {
		client.setName(nameFormat(name));
	}
	
	public void editEmail(Client client, String email) {
		if (verifyEmail(email)) {
			client.setEmail(email);
		}
	}
	
	public void editCellphone(Client client, String cellphone) {
//		TODO: falta formatacao
		client.setCellphone(cellphone);
	}
	
	public void editTelephone(Client client, String telephone) {
//		 TODO: falta formatacao
		client.setTelephone(telephone);
	}
	
	public void editOccupation(Client client, String occupation) {
		client.setOccupation(nameFormat(occupation));
	}
	
	public String nameFormat(String name) {
		String[] fields = name.strip().split(" ");
		String firstName = fields[0];
		firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
		if (fields.length > 1) {
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
		if (period.getYears() >= 2 && period.getYears() < 120) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean verifyAge(Client client) {
		LocalDate now = LocalDate.now();
		Period period = Period.between(client.getBirth(), now);
		if (period.getYears() >= 18) {
			return true;
		} else {
			if(client.getAccounts().isEmpty()) {
				removeListClients(client);
			}
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
				if(client.getAccounts().isEmpty()) {
					removeListClients(client);
				}
				return true;
			}
		}
		return false;
	}
}

