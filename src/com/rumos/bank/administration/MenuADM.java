package com.rumos.bank.administration;

import com.rumos.bank.Menu;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.services.ADMservice;
import com.rumos.bank.administration.services.AccountService;
import com.rumos.bank.administration.services.ClientService;
import com.rumos.bank.administration.services.CreditCardService;
import com.rumos.bank.administration.services.DebitCardService;
import com.rumos.bank.administration.services.UI;

public class MenuADM {

	private ADMservice admService = new ADMservice();
	private AccountService accountService = new AccountService();
	private ClientService clientService = new ClientService();
	private DebitCardService debitCardService = new DebitCardService();
	private CreditCardService creditCardService = new CreditCardService();

	public static void displayMenu() {
		System.out.println(
				"\nChoose an option:\n" 
						+ "1 - New\n" 
						+ "2 - Show\n" 
						+ "3 - List\n" 
						+ "4 - Back\n" 
						+ "5 - Exit");
	}

	public void selection() {
		Integer option = UI.getInt();
		switch (option) {
		case 1:
			newMenu();
			break;
		case 2:
			subMenu();
			showSelection();
			break;
		case 3:
			subMenu();
			listSelection();
			break;
		case 4:
			System.out.println();
			Menu.displayMenu();
			break;
		case 5:
			System.out.println("\nThanks for using Rumos Digital Bank");
			UI.scClose();
			System.exit(0);
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			selection();
			break;
		}
	}

	// ---------------------------------------------------------------------

	private void subMenu() {
		System.out.println("\nChoose an option:\n" 
				+ "1 - Account\n" 
				+ "2 - Client\n" 
				+ "3 - Credit Card\n"
				+ "4 - Debit Card\n" 
				+ "5 - Back");
	}

	private void newMenu() {
		System.out.println(
				"\nChoose an option:\n" 
						+ "1 - Account\n" 
						+ "2 - Credit Card\n" 
						+ "3 - Debit Card\n" 
						+ "4 - Back");
		newSelection();
	}

	private void newSelection() {

		int option = UI.getInt();
		switch (option) {
		case 1:

			System.out.println("\n1 - New Client" + "\n2 - Existing Client" + "\n3 - Back");
			option = UI.getInt();
			while (option != 1 && option != 2 && option != 3) {
				System.out.print("\nWrong option. Choose again: ");
				option = UI.getInt();
			}

			if (option == 1) {
				Client newClient = clientService.newClient();
				if (clientService.verifyAge(newClient)) {
					clientService.addListClients(newClient);
					accountService.newAccount(newClient);
				}

			} else if (option == 2) {
				System.out.print("\nEnter client nif: ");
				String nif = UI.scanLine();
				if (admService.showClient(nif) != null) {
					if (clientService.verifyTitular(admService.showClient(nif)) == false) {
						if (clientService.verifyAge(admService.showClient(nif))) {
							accountService.newAccount(admService.showClient(nif));
						}
					}
				}

			} else {
				newMenu();
			}
			break;
		case 2:
			System.out.print("\nEnter client nif: ");
			String nif = UI.scanLine();
			if (admService.showClient(nif) != null) {

				System.out.print("\nEnter account number: ");
				int accountNumber = UI.getInt();
				if (admService.showAccount(accountNumber) != null) {
					if (accountService.verifyAccountCreditCards(admService.showAccount(accountNumber)) == true) {
						creditCardService.newCreditCard(admService.showAccount(accountNumber), admService.showClient(nif));
					}
				}
			}
			break;
		case 3:
			System.out.print("\nEnter client NIF: ");
			nif = UI.scanLine();
			if (admService.showClient(nif) != null) {
				System.out.print("\nEnter account number: ");
				int accountNumber = UI.getInt();
				if (admService.showAccount(accountNumber) != null) {
					debitCardService.newDebitCard(admService.showAccount(accountNumber), admService.showClient(nif));
				}
			}
			break;
		case 4:
			displayMenu();
			selection();
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			selection();
			break;
		}
		displayMenu();
		selection();
	}

	private void showSelection() {

		int option = UI.getInt();
		switch (option) {
		case 1:
			System.out.print("\nAccount number: ");
			int accountNumber = UI.getInt();
			if (admService.showAccount(accountNumber) != null) {
				System.out.println(admService.showAccount(accountNumber));
				accountService.editAccount(admService.showAccount(accountNumber));

			}
			break;
		case 2:
			System.out.print("\nClient NIF: ");
			String nif = UI.scanLine();
			if (admService.showClient(nif) != null) {
				System.out.println(admService.showClient(nif));
				System.out.println("\nWant to edit?");
				option = UI.choose();
				if (option == 1) {
					clientService.editClient(admService.showClient(nif));
				}
			}
			break;
		case 3:
			System.out.print("\nCredit Card number: ");
			int creditCardNumber = UI.getInt();
			admService.showCreditCard(creditCardNumber);
			if (admService.showCreditCard(creditCardNumber) != null) {
				System.out.println(admService.showCreditCard(creditCardNumber));
				System.out.println("\nWant to Delete?");
				option = UI.choose();
				if (option == 1) {
					admService.showCreditCard(creditCardNumber).getTitular().setCreditCard(null);
					admService.showCreditCard(creditCardNumber).getAccount()
							.removeCreditCard(admService.showCreditCard(creditCardNumber));
					creditCardService.removeListCreditCards(admService.showCreditCard(creditCardNumber));
				}
			}
			break;
		case 4:
			System.out.print("\nDebit Card number: ");
			int debitCardNumber = UI.getInt();
			if (admService.showDebitCard(debitCardNumber) != null) {
				System.out.println(admService.showDebitCard(debitCardNumber));
				System.out.println("\nWant to Delete?");
				option = UI.choose();
				if (option == 1) {
					admService.showDebitCard(debitCardNumber).getTitular().setDebitCard(null);
					admService.showDebitCard(debitCardNumber).getAccount()
							.removeDebitCard(admService.showDebitCard(debitCardNumber));
					debitCardService.removeListDebitCards(admService.showDebitCard(debitCardNumber));
				}
			}
			break;
		case 5:
			displayMenu();
			selection();
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			selection();
			break;
		}
		displayMenu();
		selection();
	}

	private void listSelection() {

		int option = UI.getInt();
		switch (option) {
		case 1:
			admService.listAccounts();
			break;
		case 2:
			admService.listClients();
			break;
		case 3:
			admService.listCreditCards();
			break;
		case 4:
			admService.listDebitCards();
			break;
		case 5:
			displayMenu();
			selection();
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			selection();
			break;
		}
		displayMenu();
		selection();
	}

}
