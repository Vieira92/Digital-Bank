package com.rumos.bank.administration;

import java.util.Scanner;

import com.rumos.bank.Menu;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;
import com.rumos.bank.administration.services.ADMservice;
import com.rumos.bank.administration.services.AccountService;
import com.rumos.bank.administration.services.ClientService;
import com.rumos.bank.administration.services.CreditCardService;
import com.rumos.bank.administration.services.DebitCardService;

public class MenuADM {
	
	private Scanner sc = new Scanner(System.in);
	
	public void displayMenu() {
		System.out.println("\nChoose an option:\n"
							+ "1 - New\n"
							+ "2 - Show\n"  
							+ "3 - List\n" 
							+ "4 - Back\n" 
							+ "5 - Exit");
		}
	
	public void selection() {
		Scanner sc = new Scanner(System.in);
		Integer option = sc.nextInt();
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
			Menu backMenu = new Menu();
			backMenu.displayMenu();
			backMenu.selection();
			break;
		case 5:
			//TODO: Sair da app
			System.out.println("Exit");
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			selection();
			break;
		}
		sc.close();
	}
	
	//---------------------------------------------------------------------
	
	private void subMenu() {
		System.out.println("\nChoose an option:\n"
							+ "1 - Account\n"
							+ "2 - Client\n"
							+ "3 - Credit Card\n" 
							+ "4 - Debit Card\n"
							+ "5 - Back");
		}
	
	private void newMenu() {
		System.out.println("\nChoose an option:\n"
							+ "1 - Account\n"
							+ "2 - Credit Card\n" 
							+ "3 - Debit Card\n"
							+ "4 - Back");
		newSelection();
		}
	
	private Integer choose() {
		System.out.println("1 - Yes" + "\n2 - Not");
		Integer choose = sc.nextInt();
		while (choose != 1 && choose != 2) {
			System.out.print("\nWrong option. Choose again: ");
			choose = sc.nextInt();
		}
		return choose;
	}
	
	private void newSelection() {
		ADMservice admService = new ADMservice();
		AccountService accountService = new AccountService();
		ClientService clientService = new ClientService();
		DebitCardService debitCardService = new DebitCardService();
		CreditCardService creditCardService = new CreditCardService();
		
		Integer option = sc.nextInt();
		switch (option) {
		case 1:
			Account newAccount;
			System.out.println("\n1 - New Client"
							+  "\n2 - Existing Client"
							+  "\n3 - Back");

			option = sc.nextInt();
			while (option != 1 && option != 2 && option != 3) {
			System.out.print("\nWrong option. Choose again: ");
			option = sc.nextInt();
			}
			
			if (option == 1) {
				Client newClient = new Client();				
				newClient = clientService.newClient();
				newAccount = accountService.newAccount(newClient);
			
			} else if (option == 2){
				System.out.println("\nEnter client nif:");
				String nif = sc.next();
				if(admService.showClient(nif) != null) {
					if(clientService.verifyTitular(admService.showClient(nif)) == false) {
						System.out.println("\nNew Account:");
						newAccount = accountService.newAccount(admService.showClient(nif));	
					}					
				}
				
			} else {
				newMenu();
			}
			break;
		case 2:
			System.out.print("\nEnter client nif: ");
			String nif = sc.next();
			if(admService.showClient(nif) != null) {				
				if (creditCardService.verifyCreditCard(admService.showClient(nif)) == false) {
					System.out.print("\nEnter account number: ");
					Long accountNumber = sc.nextLong();
					if( admService.showAccount(accountNumber) != null) {
						if(accountService.verifyAccountCreditCards(admService.showAccount(accountNumber)) == true) {
							CreditCard creditCard = new CreditCard();
							creditCard = creditCardService.newCreditCard(admService.showAccount(accountNumber), admService.showClient(nif));
							admService.showAccount(accountNumber).addCreditCard(creditCard);
							System.out.println(creditCard.toStringClient());
						}
					}	
				} 
			}
			break;
		case 3:
			System.out.print("\nEnter client NIF: ");
			nif = sc.next();
			if(admService.showClient(nif) != null) {
				if (debitCardService.verifyDebitCard(admService.showClient(nif)) == false) {
					System.out.print("\nEnter account number: ");
					Long accountNumber = sc.nextLong();
					if( admService.showAccount(accountNumber) != null) {
						DebitCard debitCard = new DebitCard();
						debitCard = debitCardService.newDebitCard(admService.showAccount(accountNumber), admService.showClient(nif));
						admService.showAccount(accountNumber).addDebitCard(debitCard);
						System.out.println(debitCard.toStringClient());
					}	
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
		ADMservice admService = new ADMservice();
		AccountService accountService = new AccountService();
		ClientService clientService = new ClientService();
		DebitCardService debitCardService = new DebitCardService();
		CreditCardService creditCardService = new CreditCardService();
		
		Integer option = sc.nextInt();
		switch (option) {
		case 1:
			System.out.print("\nAccount number: ");
			Long accountNumber = sc.nextLong();
			if(admService.showAccount(accountNumber) != null) {
				System.out.println(admService.showAccount(accountNumber));				
				System.out.println("\nMore actions?");
				option = choose();
				if(option == 1) {
					accountService.editAccount(admService.showAccount(accountNumber));
				}
			}
			break;
		case 2:
			System.out.print("\nClient NIF: ");
			String nif = sc.next();
			if(admService.showClient(nif) != null) {
				System.out.println(admService.showClient(nif));
				System.out.println("\nWant to edit?");
				option = choose();
				if(option == 1) {
					clientService.editClient(admService.showClient(nif));
				}
			}
			break;
		case 3:
			System.out.print("\nCredit Card number: ");
			Long creditCardNumber = sc.nextLong();
			admService.showCreditCard(creditCardNumber);
			if(admService.showCreditCard(creditCardNumber) != null) {
				System.out.println(admService.showCreditCard(creditCardNumber));
				System.out.println("\nWant to Delete?");
				option = choose();
				if (option == 1) {
					admService.showCreditCard(creditCardNumber).getTitular().setCreditCard(null);
					admService.showCreditCard(creditCardNumber).getAccount().removeCreditCard(admService.showCreditCard(creditCardNumber));
					creditCardService.removeListCreditCards(admService.showCreditCard(creditCardNumber));
				}
			}
			break;
		case 4:
			System.out.print("\nDebit Card number: ");
			Long debitCardNumber = sc.nextLong();
			if(admService.showDebitCard(debitCardNumber) != null) {
				System.out.println(admService.showDebitCard(debitCardNumber));
				System.out.println("\nWant to Delete?");
				option = choose();
				if(option == 1) {
					admService.showDebitCard(debitCardNumber).getTitular().setDebitCard(null);
					admService.showDebitCard(debitCardNumber).getAccount().removeDebitCard(admService.showDebitCard(debitCardNumber));
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
		ADMservice listService = new ADMservice();
	
		Integer option = sc.nextInt();
		switch (option) {
		case 1:
			listService.listAccounts();
			break;
		case 2:
			listService.listClients();
			break;
		case 3:
			listService.listCreditCards();
			break;
		case 4:
			listService.listDebitCards();
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
