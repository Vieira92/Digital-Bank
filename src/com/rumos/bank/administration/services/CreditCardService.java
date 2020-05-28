package com.rumos.bank.administration.services;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;

public class CreditCardService {

	public void newCreditCard(Account account, Client titular) {
		if(verifyCreditCard(titular) == false) {
			CreditCard creditCard = new CreditCard();
			creditCard.setCreditCardNumber(ADM.creditCardNumber());
			creditCard.setAccount(account);
			creditCard.setTitular(titular);
			titular.setCreditCard(creditCard);
			account.addCreditCard(creditCard);
			addListCreditCards(creditCard);
			System.out.println(creditCard);
		}
	}
	
	
	private Boolean verifyCreditCard(Client client) {
		if (client.getCreditCard() != null) {
			System.out.println("\nClient can't have two Credit Cards");
			return true;
		} else
			return false;
	}
	
	public void addListCreditCards(CreditCard creditCard) {
		ADM.creditCards.add(creditCard);
	}
	
	public void removeListCreditCards(CreditCard creditCard) {
		ADM.creditCards.remove(creditCard);
	}
}
