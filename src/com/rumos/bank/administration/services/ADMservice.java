package com.rumos.bank.administration.services;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;

public class ADMservice {
	// ----------------------SHOW-------------------------------

	public Account showAccount(int accountNumber) {
		for (Account account : ADM.accounts) {
			if (account.getAccountNumber() == accountNumber) {
				return account;
			}
		}
		System.out.println("\nInvalid Account number");
		return null;
	}

	public Client showClient(String nif) {
		for (Client client : ADM.clients) {
			if (client.getNif().equals(nif)) {
				return client;
			}
		}
		System.out.println("\nInvalid NIF");
		return null;
	}

//	public Optional<Client> showClient(String nif) {	
//	for(Client client : ADM.clients) {
//		if(client.getNif().contains(nif)) {
//			System.out.println(client);
//			return Optional.of(client);
//		} 
//	}
//	return Optional.empty();
//	}

	public CreditCard showCreditCard(int creditCarNumber) {
		for(CreditCard creditCard : ADM.creditCards) {
			if(creditCard.getCreditCardNumber() == creditCarNumber) {
				return creditCard;
			}
		}
		System.out.println("\nInvalid Credit Card number");
		return null;
	}

	public DebitCard showDebitCard(int debitCardNumber) {
		for (DebitCard debitCard : ADM.debitCards) {
			if(debitCard.getDebitCardNumber() == debitCardNumber) {
				return debitCard;
			}
		}
		System.out.println("\nInvalid Debit Card number");
		return null;
	}

	// -------------------------LIST------------------------------

	public void listAccounts() {
		if (ADM.accounts.isEmpty()) {
			System.out.println("\nThis bank has no accounts");
		} else {
			System.out.println("\nAll Accounts");
			for (Account account : ADM.accounts) {
				System.out.println("Nº: " + account.getAccountNumber() 
				+ " / Titular: " + account.getMainTitular().getName());
			}
		}
	}

	public void listClients() {
		if (ADM.clients.isEmpty()) {
			System.out.println("\nThis bank has no clients and accounts");
		} else {
			System.out.println("\nAll Clients:");
			for (Client client : ADM.clients) {
				System.out.println("Nº: " + client.getClientNumber() 
				+ " / Name: " + client.getName());
			}
		}
	}

	public void listCreditCards() {
		if (ADM.creditCards.isEmpty()) {
			System.out.println("\nThis bank has no Credit Cards");
		} else {
			System.out.println("\nAll Credit Cards:");
			for (CreditCard creditCard : ADM.creditCards) {
				System.out.println("Nº: " + creditCard.getCreditCardNumber() 
				+ "/ Titular name: " + creditCard.getTitular().getName());
			}
		}
	}

	public void listDebitCards() {
		if (ADM.debitCards.isEmpty()) {
			System.out.println("\nThis bank has no Debit Cards");
		} else {
			System.out.println("\nAll Debit Cards:");
			for (DebitCard debitCard : ADM.debitCards) {
				System.out.println("Nº: " + debitCard.getDebitCardNumber() 
				+ " / Titular name: " + debitCard.getTitular().getName());
			}
		}
	}

}
