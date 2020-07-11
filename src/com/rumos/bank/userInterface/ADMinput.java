package com.rumos.bank.userInterface;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;
import com.rumos.bank.administration.services.ADMservice;
import com.rumos.bank.userInterface.UI;

public class ADMinput {

	private ADMservice admService; 
	
	public ADMinput() {
		admService = new ADMservice();
	}
	
	public Account showAccount() {
		System.out.print("\nAccount number: ");
		int accountNumber = UI.getInt();
		
		if (admService.showAccount(accountNumber) != null) {
			System.out.println(admService.showAccount(accountNumber));
			return admService.showAccount(accountNumber);
		}
		System.out.println("\nInvalid Account number");
		return null;
	}

	public Client showClient() {
		System.out.print("\nClient NIF: ");
		String nif = UI.scanLine();
		
		if (admService.showClient(nif) != null) {
			System.out.println(admService.showClient(nif));
			return admService.showClient(nif);
			}
		System.out.println("\nInvalid NIF");
		return null;	
	}

	public CreditCard showCreditCard() {
		System.out.print("\nCredit Card number: ");
		int creditCardNumber = UI.getInt();
		
		if (admService.showCreditCard(creditCardNumber) != null) {
			System.out.println(admService.showCreditCard(creditCardNumber));
			return admService.showCreditCard(creditCardNumber);
			}
		System.out.println("\nInvalid Credit Card number");
		return null;
	}

	public DebitCard showDebitCard() {
		System.out.print("\nDebit Card number: ");
		int debitCardNumber = UI.getInt();
		
		if (admService.showDebitCard(debitCardNumber) != null) {
			System.out.println(admService.showDebitCard(debitCardNumber));
			return admService.showDebitCard(debitCardNumber);
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
				System.out.println(
						"Nº: " + account.getAccountNumber() + " / Titular: " + account.getMainTitular().getName());
			}
		}
	}

	public void listClients() {
		if (ADM.clients.isEmpty()) {
			System.out.println("\nThis bank has no clients and accounts");
		} else {
			System.out.println("\nAll Clients:");
			for (Client client : ADM.clients) {
				System.out.println("Nº: " + client.getClientNumber() + " / Name: " + client.getName());
			}
		}
	}

	public void listCreditCards() {
		if (ADM.creditCards.isEmpty()) {
			System.out.println("\nThis bank has no Credit Cards");
		} else {
			System.out.println("\nAll Credit Cards:");
			for (CreditCard creditCard : ADM.creditCards) {
				System.out.println("Nº: " + creditCard.getCreditCardNumber() + "/ Titular name: "
						+ creditCard.getTitular().getName());
			}
		}
	}

	public void listDebitCards() {
		if (ADM.debitCards.isEmpty()) {
			System.out.println("\nThis bank has no Debit Cards");
		} else {
			System.out.println("\nAll Debit Cards:");
			for (DebitCard debitCard : ADM.debitCards) {
				System.out.println("Nº: " + debitCard.getDebitCardNumber() + " / Titular name: "
						+ debitCard.getTitular().getName());
			}
		}
	}
}
