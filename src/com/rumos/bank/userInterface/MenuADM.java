package com.rumos.bank.userInterface;

import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;
import com.rumos.bank.userInterface.ADMinput;
import com.rumos.bank.userInterface.Console;
import com.rumos.bank.userInterface.EditInput;
import com.rumos.bank.userInterface.NewInput;
import com.rumos.bank.userInterface.UI;

public class MenuADM {

	public static void displayMenuADM() {
		System.out.println(
				"\nChoose an option:\n" + "1 - New\n" + "2 - Show\n" + "3 - List\n" + "4 - Back\n" + "5 - Exit");
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
			Console.displayMenu();
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
		System.out.println( "\nChoose an option:\n" 
				+ "1 - Account\n" 
				+ "2 - Credit Card\n" 
				+ "3 - Debit Card\n" 
				+ "4 - Back");
		newSelection();
	}

	private void newSelection() {
		NewInput newInput = new NewInput();
		ADMinput admInput = new ADMinput();
		int option = UI.getInt();

		switch (option) {
		case 1:

			System.out.println("\n1 - New Client" + "\n2 - Existing Client" + "\n3 - Back");
			option = UI.getInt();
			while (option != 1 && option != 2 && option != 3) {
				System.out.print("\nWrong option. Choose again: ");
				option = UI.getInt();
			}

			Client client;
			Account account;
			if (option == 1) {
				client = newInput.newClient();
				if (client != null) {
					account = newInput.newAccount(client);
					if (account != null) {
						System.out.println(account);
					} else {
						System.out.println("This client already is titular from an account!");
					}
				} else {
					System.out.println("This client already exists!");
				}

			} else if (option == 2) {
				client = admInput.showClient();
				if (client != null && newInput.verifyTitularAccount(client) == true) {
					account = newInput.newAccount(client);
					System.out.println(account);
				}
			} else {
				newMenu();
			}
			break;
		case 2:
			newInput.newCreditCard();
			break;
		case 3:
			newInput.newDebitCard();
			break;
		case 4:
			displayMenuADM();
			selection();
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			selection();
			break;
		}
		displayMenuADM();
		selection();
	}

	private void showSelection() {
		ADMinput admInput = new ADMinput();
		EditInput editInput = new EditInput();
		int option = UI.getInt();

		switch (option) {
		case 1:
			Account account = admInput.showAccount();

			if (account != null) {
				editInput.editAccount(account);
			}
			break;
		case 2:
			Client client = admInput.showClient();

			if (client != null) {
				editInput.editClient(client);
			}
			break;
		case 3:
			CreditCard creditCard = admInput.showCreditCard();

			if (creditCard != null) {
				editInput.editCreditCard(creditCard);
			}
			break;
		case 4:
			DebitCard debitCard = admInput.showDebitCard();

			if (debitCard != null) {
				editInput.editDebitCard(debitCard);
			}
			break;
		case 5:
			displayMenuADM();
			selection();
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			selection();
			break;
		}
		displayMenuADM();
		selection();
	}

	private void listSelection() {
		int option = UI.getInt();
		switch (option) {
		case 1:
			ADMinput.listAccounts();
			break;
		case 2:
			ADMinput.listClients();
			break;
		case 3:
			ADMinput.listCreditCards();
			break;
		case 4:
			ADMinput.listDebitCards();
			break;
		case 5:
			displayMenuADM();
			selection();
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			selection();
			break;
		}
		displayMenuADM();
		selection();
	}
}
