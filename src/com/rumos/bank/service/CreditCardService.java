package com.rumos.bank.service;

import com.rumos.bank.dao.CardsDao;
import com.rumos.bank.dao.DaoFactory;
import com.rumos.bank.model.Account;
import com.rumos.bank.model.Client;
import com.rumos.bank.model.CreditCard;

public class CreditCardService {
	
	public CreditCard newCreditCard(Account account, Client titular) {
		
		CardsDao cardsDao = DaoFactory.createCardsDao();
		
		if(!cardsDao.verifyClientCard(titular.getId_client(), "C")) {
			if(cardsDao.verifyaccountCreditCards(account.getId_account())) {
				CreditCard creditCard = new CreditCard(account, titular);
				creditCard = cardsDao.insert(creditCard);
				if (creditCard != null) {
					return creditCard;	
				}
			} else { System.out.println("\nAccount can only have 2 credit cards"); }
		} else { System.out.println("\nClient can't have 2 credit cards"); }
		
		return null;
	}
	
	public void removeCreditCard(CreditCard creditCard) {
		CardsDao cardsDao = DaoFactory.createCardsDao();
		cardsDao.deleteById(creditCard.getIdCard());
	}
}
