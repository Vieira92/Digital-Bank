package com.rumos.bank.administration.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.dao.ClientDao;
import com.rumos.bank.dao.DaoFactory;

public class ClientService {

	private ClientDao clientDao;

	public ClientService() {
		clientDao = DaoFactory.createClientDao();
	}

	public Client newClient(String name, LocalDate birth, String nif, String email, String cellphone, String telephone,
			String occupation) {

		if (clientDao.findByNif(nif) == null) {
			Client client = new Client(name, birth, nif, email, cellphone, telephone, occupation);
			client = clientDao.insert(client);
			if (client != null) {
				return client;
			}
		}
		return null;
	}

	public void editName(Client client, String name) {
		client.setName(nameFormat(name));
		clientDao.update(client);
	}

	public void editEmail(Client client, String email) {
		if (verifyEmail(email)) {
			client.setEmail(email);
			clientDao.update(client);
		}
	}

	public void editCellphone(Client client, String cellphone) {
//		TODO: falta formatacao
		client.setCellphone(cellphone);
		clientDao.update(client);
	}

	public void editTelephone(Client client, String telephone) {
//		 TODO: falta formatacao
		client.setTelephone(telephone);
		clientDao.update(client);
	}

	public void editOccupation(Client client, String occupation) {
		client.setOccupation(nameFormat(occupation));
		clientDao.update(client);
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
			if (getClientAccounts(client).isEmpty()) {
				clientDao.deleteByNif(client.getNif());
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
		ClientDao clientDao = DaoFactory.createClientDao();

		if (clientDao.verifyTitular(client)) {
			return true;
		} else {
			return false;
		}
	}

	public List<Account> getClientAccounts(Client client) {
		List<Account> list = clientDao.findClientAccounts(client.getId_client());
		return list;

	}
}

