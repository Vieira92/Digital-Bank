package com.rumos.bank.administration.services;

import java.util.ArrayList;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;

public class AccountService {
	
	private ClientService clientService = new ClientService();
	private DebitCardService debitCardService = new DebitCardService();
	private CreditCardService creditCardService = new CreditCardService();
	
	public void addListAccounts(Account account) {
		ADM.accounts.add(account);
	}

	public void removeListAccounts(Account account) {
		ADM.accounts.remove(account);
	}

	public Account newAccount(Client mainTitular, double balance, ArrayList<Client> otherTitulars, ArrayList<Client> debitCards, ArrayList<Client> creditCards) {
		int accountNumber = ADM.accountNumber();

		Account account = new Account(accountNumber, mainTitular, balance, otherTitulars);
		
		for (Client client : otherTitulars) {
			client.addAccount(account);
		}
		
		for (Client client : debitCards) {
			debitCardService.newDebitCard(account, client);
		}
		
		for (Client client : creditCards) {
			creditCardService.newCreditCard(account, client);
		}
		
		mainTitular.addAccount(account);
		addListAccounts(account);		
		return account;
	}

	// ----------------------------------------------------------------------

	public void addOtherTitular(Account account, Client client) {
		client.addAccount(account);
		account.addOtherTitular(client);
	}

	public void removeOtherHolder(Account account, Client client) {
		if (account.getDebitCards().contains(client.getDebitCard())) {
			debitCardService.removeListDebitCards(client.getDebitCard());
			client.setDebitCard(null);
		}
		if (account.getCreditCards().contains(client.getCreditCard())) {
			creditCardService.removeListCreditCards(client.getCreditCard());
			client.setCreditCard(null);
		}
		account.removeOtherTitular(client);
		client.removeAccount(account);

		if (client.getAccounts().isEmpty()) {
			clientService.removeListClients(client);
		}
	}

	public void removeAccount(Account account) {
		Client titular = account.getMainTitular();

		if (titular.getDebitCard() != null) {
			if (account.getDebitCards().contains(titular.getDebitCard())) {
				debitCardService.removeListDebitCards(titular.getDebitCard());
				titular.setDebitCard(null);
			}
		}
		if (titular.getCreditCard() != null) {
			if (account.getCreditCards().contains(titular.getCreditCard())) {
				creditCardService.removeListCreditCards(titular.getCreditCard());
				titular.setCreditCard(null);
			}
		}
		titular.removeAccount(account);
		if (titular.getAccounts().isEmpty()) {
			clientService.removeListClients(titular);
		}

		if (account.getOtherTitulars() != null) {
			for (Client client : account.getOtherTitulars()) {
				if (client.getDebitCard() != null) {
					if (account.getDebitCards().contains(client.getDebitCard())) {
						debitCardService.removeListDebitCards(client.getDebitCard());
						client.setDebitCard(null);
					}
				}
				if (client.getCreditCard() != null) {
					if (account.getCreditCards().contains(client.getCreditCard())) {
						creditCardService.removeListCreditCards(client.getCreditCard());
						client.setCreditCard(null);
					}
				}
				client.removeAccount(account);
				if (client.getAccounts().isEmpty()) {
					clientService.removeListClients(client);
				}
			}
		}
		removeListAccounts(account);
	}
	
	public void draw(Account account, Double value) {
		if (account.getBalance() > value) {
			account.setBalance(account.getBalance() - value);
			System.out.println(account);
		} else {
			//TODO: tem que sair daqui
			System.out.println("Don't have that amount in account");
		}
	}

	public void transfer(Account accountFrom, Account accountFor, Double value) {
		if (accountFrom.getBalance() > value) {
			accountFrom.setBalance(accountFrom.getBalance() - value);
			accountFor.setBalance(accountFor.getBalance() + value);
			System.out.println(accountFrom);
		} else {
			//TODO: tem que sair daqui
			System.out.println("Don't have that amount in account");
		}

	}

	public void deposit(Account account, Double value) {
		account.setBalance(account.getBalance() + value);
		System.out.println(account);
	}
}
