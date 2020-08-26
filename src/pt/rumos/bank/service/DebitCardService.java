package pt.rumos.bank.service;

import pt.rumos.bank.dao.CardsDao;
import pt.rumos.bank.dao.DaoFactory;
import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Client;
import pt.rumos.bank.model.DebitCard;
import pt.rumos.bank.userInterface.UI;

public class DebitCardService {

	private CardsDao cardsDao;

	public DebitCardService() { cardsDao = DaoFactory.createCardsDao(); }
	
	
	public DebitCard newDebitCard(Account account, Client titular) {
		
		if (!cardsDao.verifyClientCard(titular.getId_client(), "D")) {
			DebitCard debitCard = new DebitCard(account, titular);
			debitCard = cardsDao.insert(debitCard, UI.generatePass());
			if (debitCard != null) {
				System.out.println("\nMethod that sends the (DC) pass via email");
				return debitCard;	
			}	
		} 
		System.out.println("\nClient can't have 2 debit cards");
		return null;
	}
	
	public Boolean changePin(int id_card, String pin) {
		return cardsDao.changePin(id_card, pin);
	}
	
	public Boolean verifyPin(int id_card, String pin) {
		return cardsDao.verifyCardPin(id_card, pin);
	}
	
	public void removeDebitCard(DebitCard debitCard) {
		cardsDao.deleteById(debitCard.getIdCard());
	}
}
