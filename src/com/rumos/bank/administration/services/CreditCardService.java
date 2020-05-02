package com.rumos.bank.administration.services;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;

public class CreditCardService {
	
	public CreditCard newCreditCard(Account account, Client titular) {
		CreditCard creditCard = new CreditCard();
		creditCard.setCreditCardNumber(ADM.creditCardNumber());
		creditCard.setAccount(account);
		creditCard.setTitular(titular);
		titular.setCreditCard(creditCard);
		account.addCreditCard(creditCard);
		addListCreditCards(creditCard);
		return creditCard;
	}
	
	
	public Boolean verifyCreditCard(Client client) {
		if (client.getCreditCard() != null) {
			System.out.println("Client can't have two Debit Cards");
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
