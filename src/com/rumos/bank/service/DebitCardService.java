package com.rumos.bank.service;

import com.rumos.bank.dao.CardsDao;
import com.rumos.bank.dao.DaoFactory;
import com.rumos.bank.model.Account;
import com.rumos.bank.model.Client;
import com.rumos.bank.model.DebitCard;

public class DebitCardService {

	public DebitCard newDebitCard(Account account, Client titular) {

		CardsDao cardsDao = DaoFactory.createCardsDao();

		if (!cardsDao.verifyClientCard(titular.getId_client(), "D")) {
			DebitCard debitCard = new DebitCard(account, titular);
			debitCard = cardsDao.insert(debitCard);
			if (debitCard != null) {
				return debitCard;	
			}	
		} 
		System.out.println("\nClient can't have 2 debit cards");
		return null;
	}
	
	public void removeDebitCard(DebitCard debitCard) {
		CardsDao cardsDao = DaoFactory.createCardsDao();
		cardsDao.deleteById(debitCard.getIdCard());
	}
}
