package com.rumos.bank.administration.services;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;

public class CreditCardService {
	
	public CreditCard newCreditCard(Account account, Client titular) {
		if(verifyCreditCard(titular) == false) {
			if(verifyAccountCreditCards(account) == true) {
				int creditCardNumber = ADM.creditCardNumber();
				
				CreditCard creditCard = new CreditCard(creditCardNumber, account, titular);
				
				titular.setCreditCard(creditCard);
				account.addCreditCard(creditCard);
				addListCreditCards(creditCard);
				return creditCard;
			}
		}
		return null;
	}
	
	public Boolean verifyCreditCard(Client client) {
		if (client.getCreditCard() != null) {
			return true;
		} else
			return false;
	}
	
	public Boolean verifyAccountCreditCards(Account account) {
		if (account.getCreditCards().isEmpty() || account.getCreditCards().size() < 2) {
			return true;
		} else {

			return false;
		}
	}
	
	public void addListCreditCards(CreditCard creditCard) {
		ADM.creditCards.add(creditCard);
	}
	
	public void removeListCreditCards(CreditCard creditCard) {
		ADM.creditCards.remove(creditCard);
	}
}
