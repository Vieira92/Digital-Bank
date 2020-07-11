package com.rumos.bank.administration.services;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.DebitCard;

public class DebitCardService {
	
	public DebitCard newDebitCard(Account account, Client titular) {
		if(verifyDebitCard(titular) == false) {
			int debitCardNumber = ADM.debitCardNumber();
			
			DebitCard debitCard = new DebitCard(debitCardNumber, account, titular);
			
			titular.setDebitCard(debitCard);
			account.addDebitCard(debitCard);
			addListDebitCards(debitCard);
			return debitCard;
		}
		return null;
	}
	
	public Boolean verifyDebitCard(Client client) {
		if (client.getDebitCard() != null) {
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
