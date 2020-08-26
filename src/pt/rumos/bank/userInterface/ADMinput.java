package pt.rumos.bank.userInterface;

import java.util.List;

import pt.rumos.bank.dao.AccountDao;
import pt.rumos.bank.dao.CardsDao;
import pt.rumos.bank.dao.ClientDao;
import pt.rumos.bank.dao.DaoFactory;
import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Card;
import pt.rumos.bank.model.Client;
import pt.rumos.bank.model.CreditCard;
import pt.rumos.bank.model.DebitCard;
import pt.rumos.bank.userInterface.UI;

public class ADMinput {
	
	public Account showAccount() {
		AccountDao accountDao = DaoFactory.createAccountDao();
		System.out.print("\nAccount number: ");
		int id = UI.getInt();
		Account account = accountDao.findById(id);
		if (account != null) {
			System.out.println(account);
			return account;
		}
		System.out.println("\nInvalid Account number");
		return null;
	}

	public Client showClient() {
		ClientDao clientDao = DaoFactory.createClientDao();
		System.out.print("\nClient NIF: ");
		String nif = UI.scanLine();
		Client client = clientDao.findByNif(nif);
		if (client != null) {
			System.out.println(client);
			return client;
			} else {
				System.out.println("\nInvalid NIF");
				return null;			
			}
	}

	public CreditCard showCreditCard() {
		CardsDao cardsDao = DaoFactory.createCardsDao();
		System.out.print("\nCredit Card number: ");
		int id_card = UI.getInt();
		CreditCard creditCard = cardsDao.findCreditCardById(id_card);
		if (creditCard != null) {
			System.out.println(creditCard);
			return creditCard;
			}
		System.out.println("\nInvalid Credit Card number");
		return null;
	}

	public DebitCard showDebitCard() {
		CardsDao cardsDao = DaoFactory.createCardsDao();
		System.out.print("\nDebit Card number: ");
		int id_card = UI.getInt();
		DebitCard debitCard = cardsDao.findDebitCardById(id_card);
		if (debitCard != null) {
			System.out.println(debitCard);
			return debitCard;
		}
		System.out.println("\nInvalid Debit Card number");
		return null;
	}

	// -------------------------LIST------------------------------

	public static void listAccounts() {
		AccountDao accountDao = DaoFactory.createAccountDao();
		List<Account> accounts = accountDao.findAll();
		if (accounts.isEmpty()) {
			System.out.println("\nThis bank has no accounts");
		} else {
			System.out.println("\nAll Accounts");
			for (Account account : accounts) { 	System.out.println(account); }
		}
	}

	public static void listClients() {
		ClientDao clientDao = DaoFactory.createClientDao();
		List<Client> clients = clientDao.findAll();
		if (clients.isEmpty()) {
			System.out.println("\nThis bank has no clients and accounts");
		} else {
			System.out.println("\nAll Clients:");
			for (Client client : clients) { System.out.println(client); }
		}
	}

	public static void listCreditCards() {
		CardsDao cardsDao = DaoFactory.createCardsDao();
		List<Card> creditCards = cardsDao.findAllTypeCards("C");
		if (creditCards.isEmpty()) {
			System.out.println("\nThis bank has no Credit Cards");
		} else {
			System.out.println("\nAll Credit Cards:");
			for (Card creditCard : creditCards) { System.out.println(creditCard); }
		}
	}

	public static void listDebitCards() {
		CardsDao cardsDao = DaoFactory.createCardsDao();
		List<Card> debitCards = cardsDao.findAllTypeCards("D");
		if (debitCards.isEmpty()) {
			System.out.println("\nThis bank has no Debit Cards");
		} else {
			System.out.println("\nAll Debit Cards:");
			for (Card debitCard : debitCards) { System.out.println(debitCard); }
		}
	}
}
