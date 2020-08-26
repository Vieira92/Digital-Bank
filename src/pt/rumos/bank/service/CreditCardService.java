package pt.rumos.bank.service;

import pt.rumos.bank.dao.CardsDao;
import pt.rumos.bank.dao.DaoFactory;
import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Client;
import pt.rumos.bank.model.CreditCard;
import pt.rumos.bank.userInterface.UI;

public class CreditCardService {

	private CardsDao cardsDao;

	public CreditCardService() { cardsDao = DaoFactory.createCardsDao(); }
	
	
	public CreditCard newCreditCard(Account account, Client titular) {
		
		if (!cardsDao.verifyClientCard(titular.getId_client(), "C")) {
			if (cardsDao.verifyAccountCreditCards(account.getId_account())) {
				CreditCard creditCard = new CreditCard(account, titular);
				creditCard = cardsDao.insert(creditCard, UI.generatePass());
				if (creditCard != null) {
					System.out.println("\nMethod that sends the (CC) pass via email");
					return creditCard;
				}
			} else { System.out.println("\nAccount can only have 2 credit cards"); }
		} else { System.out.println("\nClient can't have 2 credit cards"); }
		return null;
	}

	public Boolean changePin(int id_card, String pin) {
		return cardsDao.changePin(id_card, pin);
	}

	public Boolean verifyPin(int id_card, String pin) {
		return cardsDao.verifyCardPin(id_card, pin);
	}

	public void removeCreditCard(CreditCard creditCard) {
		cardsDao.deleteById(creditCard.getIdCard());
	}
}
