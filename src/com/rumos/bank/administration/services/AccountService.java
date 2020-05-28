package com.rumos.bank.administration.services;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.MenuADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;

public class AccountService {
	private ClientService clientService = new ClientService();
	private ADMservice admService = new ADMservice();
	private DebitCardService debitCardService = new DebitCardService();
	private CreditCardService creditCardService = new CreditCardService();

	public Boolean verifyAccountCreditCards(Account account) {
		if (account.getCreditCards().isEmpty() || account.getCreditCards().size() < 2) {
			return true;
		} else {
			System.out.println("\nAccounts can´t have more than two Credit Cards");
			return false;
		}
	}

	public void firstDeposit(Account account, Client client) {
		MenuADM menuADM = new MenuADM();
		System.out.print("\nEnter the deposit value: ");
		double value = UI.getDouble();
		while (value < 50.00) {
			System.out.println("\nThe minimum value to open an account is 50.00 $");
			System.out.println("Want to cancel?");
			int choose = UI.choose();
			if (choose == 1) {
				if (client.getAccounts().isEmpty()) {
					clientService.removeListClients(client);
				}
				MenuADM.displayMenu();
				menuADM.selection();
			}
			System.out.print("\nEnter the deposit value: ");
			value = UI.getDouble();
		}
		account.setBalance(value);
	}

	public Account newAccount(Client client) {

		Account account = new Account();

		firstDeposit(account, client);
		account.setMainTitular(client);
		account.setAccountNumber(ADM.accountNumber());

		System.out.println("\nDo you want Debit Card? ");
		int option = UI.choose();
		if (option == 1) {
			debitCardService.newDebitCard(account, client);
		}

		System.out.println("\nDo you want Credit Card?");
		option = UI.choose();
		if (option == 1) {
			creditCardService.newCreditCard(account, client);
		}

		secondTitulars(account);

		client.addAccount(account);
		addListAccounts(account);

		System.out.println(account);
		return account;
	}

	private void secondTitulars(Account account) {
		System.out.println("\nSecundary Titulars?");
		int option = UI.choose();
		if (option == 1) {
			System.out.println("\nMaximum of 4 secondary titulars" + "\nHow many titulars? ");
			int number = UI.getInt();
			while (number > 4 || number < 0) {
				System.out.print("\nWrong option. Choose again: ");
				number = UI.getInt();
			}
			Client otherClient;
			for (int i = 0; i < number; i++) {
				System.out.println("\n1 - New client" + "\n2 - Existing client" + "\n3 - Pass");
				option = UI.getInt();
				while (option != 1 && option != 2 && option != 3) {
					System.out.print("\nWrong option. Choose again: ");
					option = UI.getInt();
				}

				if (option == 1) { // Novo cliente secundario
					otherClient = clientService.newClient();

					System.out.println("\nDo you want Debit Card? ");
					option = UI.choose();
					if (option == 1) {
						debitCardService.newDebitCard(account, otherClient);
					}

					if (verifyAccountCreditCards(account)) {
						System.out.println("\nDo you want Credit Card?");
						option = UI.choose();
						if (option == 1) {
							creditCardService.newCreditCard(account, otherClient);;
						}
					}

					account.addOtherTitular(otherClient);
					otherClient.addAccount(account);
					clientService.addListClients(otherClient);

				} else if (option == 2) { // cliente existente
					System.out.print("Enter the nif: ");
					String nif = UI.scanLine();
					if (admService.showClient(nif) != null && admService.showClient(nif) != account.getMainTitular()) {

						System.out.println("\nDo you want Debit Card?");
						option = UI.choose();
						if (option == 1) {
							debitCardService.newDebitCard(account, admService.showClient(nif));
						}

						if (verifyAccountCreditCards(account)) {
							System.out.println("\nDo you want Credit Card?");
							option = UI.choose();
							if (option == 1) {
								creditCardService.newCreditCard(account, admService.showClient(nif));
							}
						}
						account.addOtherTitular(admService.showClient(nif));
						admService.showClient(nif).addAccount(account);
					}
				} else {
					System.out.println("Pass");
				}
			}
		}
	}

	public void editAccount(Account account) {
		MenuADM menuADM = new MenuADM();
		System.out.println("\nChoose your Action:" 
				+ "\n1 - Add Other Titular" 
				+ "\n2 - Remove Other Titular"
				+ "\n3 - Remove Account" 
				+ "\n4 - Transfer" 
				+ "\n5 - Deposit" 
				+ "\n6 - Draw" 
				+ "\n7 - Back");

		int option = UI.getInt();
		switch (option) {
		case 1:
			if (account.getOtherTitulars() == null || account.getOtherTitulars().size() < 4) {
				addOtherTitular(account);
			} else {
				System.out.println("\nThis account already has four Other Titulars");
			}
			break;
		case 2:
			System.out.print("\nEnter the nif: ");
			String nif = UI.scanLine(); // tinha scan
			if (admService.showClient(nif) != null && admService.showClient(nif).getAccounts().contains(account)) {
				System.out.println("2");
				removeOtherHolder(account, admService.showClient(nif));
			} else {
				System.out.println("\nThis nif doesn't belong to this account");
			}
			break;
		case 3:
			removeAccount(account);
			break;
		case 4:
			System.out.println("\nEnter the account number: ");
			int accountNumber = UI.getInt();
			if (admService.showAccount(accountNumber) != null) {
				if (account.getAccountNumber() == accountNumber) {
					System.out.println("\nCan't transfer money to the same account");
					editAccount(account);
				} else {
					System.out.print("\nEnter the amount to transfer: ");
					double value = UI.getDouble();
					transfer(account, admService.showAccount(accountNumber), value);
				}
			}
			break;
		case 5:
			System.out.print("\nEnter the Deposit value: ");
			double value = UI.getDouble();
			deposit(account, value);
			break;
		case 6:
			System.out.print("\nEnter the Draw value: ");
			value = UI.getDouble();
			draw(account, value);
			break;
		case 7:
			MenuADM.displayMenu();
			menuADM.selection();
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			editAccount(account);
			break;
		}
		MenuADM.displayMenu();
		menuADM.selection();
	}

	// ----------------------------------------------------------------------

	private void addOtherTitular(Account account) {
//		TODO: adicionar outro titular à conta

		System.out.println("\n1 - New client" + "\n2 - Existing client");
		int option = UI.getInt();
		while (option != 1 && option != 2) {
			System.out.print("\nWrong option. Choose again: ");
			option = UI.getInt();
		}
		if (option == 1) {
			Client client = clientService.newClient();
			client.addAccount(account);
			account.addOtherTitular(client);

			System.out.println("\nDo you want Debit Card?");
			option = UI.choose();
			if (option == 1) {
				debitCardService.newDebitCard(account, client);
			}
			
			if (verifyAccountCreditCards(account)) {
				System.out.println("\nDo you want Credit Card?");
				option = UI.choose();
				if (option == 1) {
					creditCardService.newCreditCard(account, client);
				}
			}
	
		} else {
			System.out.print("\nEnter the nif: ");
			String nif = UI.scanLine();
			if (admService.showClient(nif) != null && !account.getOtherTitulars().contains(admService.showClient(nif))
					&& account.getMainTitular() != admService.showClient(nif)) {

				account.addOtherTitular(admService.showClient(nif));
				admService.showClient(nif).addAccount(account);
				System.out.println(admService.showClient(nif));

				System.out.println("\nDo you want Debit Card?");
				option = UI.choose();
				if (option == 1) {
					debitCardService.newDebitCard(account, admService.showClient(nif));
				}
				
				if (verifyAccountCreditCards(account)) {
					System.out.println("\nDo you want Credit Card?");
					option = UI.choose();
					if (option == 1) {
						creditCardService.newCreditCard(account, admService.showClient(nif));
					}
				}
			}
		}
	}

	private void removeOtherHolder(Account account, Client client) {
//		 TODO: remover outro titular da conta
		if (account.getDebitCards().contains(client.getDebitCard())) {
			debitCardService.removeListDebitCards(client.getDebitCard());
			client.setDebitCard(null);
		}
		if (account.getCreditCards().contains(client.getCreditCard())) {
			creditCardService.removeListCreditCards(client.getCreditCard());
			client.setCreditCard(null);
		}
		account.removeOtherTitular(client);
		client.removeAccount(account);

		if (client.getAccounts().isEmpty()) {
			clientService.removeListClients(client);
		}
	}

	private void draw(Account account, Double value) {
		// TODO: levantar guita
		if (account.getBalance() > value) {
			account.setBalance(account.getBalance() - value);
			System.out.println(account);
		} else {
			System.out.println("Don't have that amount in account");
		}
	}

	private void transfer(Account accountFrom, Account accountFor, Double value) {
		// TODO: tranferir guita entre contas
		if (accountFrom.getBalance() > value) {
			accountFrom.setBalance(accountFrom.getBalance() - value);
			accountFor.setBalance(accountFor.getBalance() + value);
			System.out.println(accountFrom);
		} else {
			System.out.println("Don't have that amount in account");
		}

	}

	private void deposit(Account account, Double value) {
		// TODO: depositar guita
		account.setBalance(account.getBalance() + value);
		System.out.println(account);
	}

	private void removeAccount(Account account) {
//		TODO: remover conta
		Client titular = account.getMainTitular();

		if (titular.getDebitCard() != null) {
			if (account.getDebitCards().contains(titular.getDebitCard())) {
				debitCardService.removeListDebitCards(titular.getDebitCard());
				titular.setDebitCard(null);
			}
		}
		if (titular.getCreditCard() != null) {
			if (account.getCreditCards().contains(titular.getCreditCard())) {
				creditCardService.removeListCreditCards(titular.getCreditCard());
				titular.setCreditCard(null);
			}
		}
		titular.removeAccount(account);
		if (titular.getAccounts().isEmpty()) {
			clientService.removeListClients(titular);
		}

		if (account.getOtherTitulars() != null) {
			for (Client client : account.getOtherTitulars()) {
				if (client.getDebitCard() != null) {
					if (account.getDebitCards().contains(client.getDebitCard())) {
						debitCardService.removeListDebitCards(client.getDebitCard());
						client.setDebitCard(null);
					}
				}
				if (client.getCreditCard() != null) {
					if (account.getCreditCards().contains(client.getCreditCard())) {
						creditCardService.removeListCreditCards(client.getCreditCard());
						client.setCreditCard(null);
					}
				}
				client.removeAccount(account);
				if (client.getAccounts().isEmpty()) {
					clientService.removeListClients(client);
				}
			}
		}

		removeListAccounts(account);
	}

	// ---------------------------------------------------

	public void addListAccounts(Account account) {
		ADM.accounts.add(account);
	}

	public void removeListAccounts(Account account) {
		ADM.accounts.remove(account);
	}

}
