package com.rumos.bank.administration.services;

import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.dao.CardsDao;
import com.rumos.bank.dao.DaoFactory;

public class CreditCardService {
	
	public CreditCard newCreditCard(Account account, Client titular) {
		
		CardsDao cardsDao = DaoFactory.createCardsDao();
		
		if(!cardsDao.verifyCreditCard(titular.getId_client())) {
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
	
//	public Boolean verifyCreditCard(Client client) {
//		if (client.getCreditCard() != null) {
//			return true;
//		} else
//			return false;
//	}
	
//	public Boolean verifyAccountCreditCards(Account account) {
//		CardsDao cardsDao = DaoFactory.createCardsDao();
//		
//		if (cardsDao.verifyaccountCreditCards(account.getId_account())) {
//			return true;
//		} else {
//
//			return false;
//		}
//	}
	
}
