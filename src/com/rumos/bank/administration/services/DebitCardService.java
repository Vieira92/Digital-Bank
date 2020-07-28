package com.rumos.bank.administration.services;

import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.DebitCard;
import com.rumos.bank.dao.CardsDao;
import com.rumos.bank.dao.DaoFactory;

public class DebitCardService {

	public DebitCard newDebitCard(Account account, Client titular) {

		CardsDao cardsDao = DaoFactory.createCardsDao();

		if (!cardsDao.verifyDebitCard(titular.getId_client())) {
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

//	public Boolean verifyDebitCard(Client client) {
//		if (client.getDebitCard() != null) {
//			return true;
//		} else
//			return false;
//	}

}
