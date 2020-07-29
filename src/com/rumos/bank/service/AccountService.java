package com.rumos.bank.service;

import java.util.ArrayList;
import java.util.List;

import com.rumos.bank.dao.AccountDao;
import com.rumos.bank.dao.ClientDao;
import com.rumos.bank.dao.DaoFactory;
import com.rumos.bank.model.Account;
import com.rumos.bank.model.Client;
import com.rumos.bank.model.CreditCard;
import com.rumos.bank.model.DebitCard;

public class AccountService {

	private ClientService clientService;
	private DebitCardService debitCardService;
	private CreditCardService creditCardService;
	private ClientDao clientDao;
	private AccountDao accountDao;

	public AccountService() {
		clientService = new ClientService();
		debitCardService = new DebitCardService();
		creditCardService = new CreditCardService();
		clientDao = DaoFactory.createClientDao();
		accountDao = DaoFactory.createAccountDao();
	}

	public Account newAccount(Client mainTitular, double balance, ArrayList<Client> otherTitulars,
			ArrayList<Client> debitCards, ArrayList<Client> creditCards) {

		if (clientDao.verifyTitular(mainTitular) == false) {
			Account account = new Account(mainTitular, balance);

			account = accountDao.insert(account);
			if (account != null) {
				accountDao.insertAccountClients(account, mainTitular);

				if (!otherTitulars.isEmpty()) {
					for (Client client : otherTitulars) {
						accountDao.insertAccountClients(account, client);
						account.addOtherTitular(client);
					}
				}

				for (Client client : debitCards) {
					DebitCard debitCard = debitCardService.newDebitCard(account, client);
					if (debitCard != null) {
						account.addDebitCard(debitCard);
					}
				}

				for (Client client : creditCards) {
					CreditCard creditCard = creditCardService.newCreditCard(account, client);
					if (creditCard != null) {
						account.addCreditCard(creditCard);
					}
				}
				return account;
			}
		}
		return null;

	}

	// ----------------------------------------------------------------------

	public Boolean addOtherTitular(Account account, Client client) {
		if (accountDao.findTitularByAccountId(account.getId_account()) != client) {
			List<Client> clients = accountDao.findAccountClients(account.getId_account());
			if (!clients.contains(client)) {
				accountDao.insertAccountClients(account, client);
				return true;
			}
		}
		return false;
	}

	public Boolean verifyOtherTitulars(Account account) {
		if (accountDao.findAccountClients(account.getId_account()).size() <= 4) {
			return true;
		}
		return false;
	}

	public List<Client> getAccountClients(Account account) {
		return accountDao.findAccountClients(account.getId_account());
	}

	public List<CreditCard> getAccountCreditCards(Account account) {
		return accountDao.findAccountCreditCards(account.getId_account());
	}

	public List<DebitCard> getAccountDebitCards(Account account) {
		return accountDao.findAccountDebitCards(account.getId_account());
	}

	public void removeOtherHolder(Account account, Client client) {
		List<Client> accountClients = getAccountClients(account);

		if (client != account.getMainTitular() && accountClients.contains(client)) {

			List<DebitCard> accountDebitCards = getAccountDebitCards(account);
			List<CreditCard> accountCreditCards = getAccountCreditCards(account);

			if (!accountDebitCards.isEmpty()) {
				for (DebitCard debitCard : accountDebitCards) {
					if (debitCard.getTitular().equals(client)) {
						debitCardService.removeDebitCard(debitCard);
					}
				}
			}


			if(!accountCreditCards.isEmpty()) {
				for (CreditCard creditCard : accountCreditCards) {
					if (creditCard.getTitular().equals(client)) {
//						TODO: TEM que tratar dos plafonds e dividas
						creditCardService.removeCreditCard(creditCard);
					}
				}
			}
			

			List<Account> clientAccounts = clientService.getClientAccounts(client);
			clientAccounts.remove(account);
			accountDao.deleteAccount_client(account.getId_account(), client.getId_client());
			if (clientAccounts.isEmpty()) {
				clientDao.deleteByNif(client.getNif());
			}
		}
	}

	public void removeAccount(Account account) {
		Client titular = account.getMainTitular();
		
		List<Client> accountClients = getAccountClients(account);

		accountClients.remove(titular);
		for (Client c : accountClients) {
			removeOtherHolder(account, c);
		}

		List<DebitCard> accountDebitCards = getAccountDebitCards(account);
		List<CreditCard> accountCreditCards = getAccountCreditCards(account);


		for (DebitCard debitCard : accountDebitCards) {
			if (debitCard.getTitular().equals(titular)) {
				debitCardService.removeDebitCard(debitCard);
			}
		}

		for (CreditCard creditCard : accountCreditCards) {
			if (creditCard.getTitular().equals(titular)) {
//				TODO: TEM que tratar dos plafonds e dividas
				creditCardService.removeCreditCard(creditCard);
			}
		}

		List<Account> titularAccounts = clientService.getClientAccounts(titular);
		titularAccounts.remove(account);
		
		accountDao.deleteAccount_client(account.getId_account(), account.getMainTitular().getId_client());
		accountDao.deleteById(account.getId_account());	

		if (titularAccounts.isEmpty()) {
			clientService.deleteClient(titular); 
		}
	}

	public void draw(Account account, Double value) {
		if (account.getBalance() > value) {
			account.setBalance(account.getBalance() - value);
			System.out.println(account);
		} else {
			// TODO: tem que sair daqui
			System.out.println("Don't have that amount in account");
		}
	}

	public void transfer(Account accountFrom, Account accountFor, Double value) {
		if (accountFrom.getBalance() > value) {
			accountFrom.setBalance(accountFrom.getBalance() - value);
			accountFor.setBalance(accountFor.getBalance() + value);
			System.out.println(accountFrom);
		} else {
			// TODO: tem que sair daqui
			System.out.println("Don't have that amount in account");
		}

	}

	public void deposit(Account account, Double value) {
		account.setBalance(account.getBalance() + value);
		System.out.println(account);
	}
}