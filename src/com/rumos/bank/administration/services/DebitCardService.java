package com.rumos.bank.administration.services;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.DebitCard;

public class DebitCardService {

	public void newDebitCard(Account account, Client titular) {
		if(verifyDebitCard(titular) == false) {
			DebitCard debitCard = new DebitCard();
			debitCard.setDebitCardNumber(ADM.debitCardNumber());
			debitCard.setAccount(account);
			debitCard.setTitular(titular);
			titular.setDebitCard(debitCard);
			account.addDebitCard(debitCard);
			addListDebitCards(debitCard);
			System.out.println(debitCard);
		}
	}
	
	public Boolean verifyDebitCard(Client client) {
		if (client.getDebitCard() != null) {
			System.out.println("\nClient can't have two Debit Cards");
			return true;
		} else
			return false;
	}

	public void addListDebitCards(DebitCard debitCard) {
		ADM.debitCards.add(debitCard);
	}
	
	public void removeListDebitCards(DebitCard debitCard) {
		ADM.debitCards.remove(debitCard);
	}
}