package com.rumos.bank.userInterface;

import java.util.List;

import com.rumos.bank.model.Account;
import com.rumos.bank.model.Client;
import com.rumos.bank.model.CreditCard;
import com.rumos.bank.model.DebitCard;
import com.rumos.bank.service.AccountService;
import com.rumos.bank.service.ClientService;
import com.rumos.bank.service.CreditCardService;
import com.rumos.bank.service.DebitCardService;
import com.rumos.bank.userInterface.ADMinput;
import com.rumos.bank.userInterface.MenuADM;
import com.rumos.bank.userInterface.NewInput;
import com.rumos.bank.userInterface.UI;

public class EditInput {

	private AccountService accountService;
	private ClientService clientService;
	private CreditCardService creditCardService;
	private DebitCardService debitCardService;
	private ADMinput admInput;
	
	public EditInput() {
		accountService = new AccountService();
		clientService = new ClientService();
		creditCardService = new CreditCardService();
		debitCardService = new DebitCardService();
		admInput = new ADMinput();
	}

	public void editAccount(Account account) {
		System.out.println("\nWant to edit?");
		int option = UI.choose();
		if (option == 1) {
			MenuADM menuADM = new MenuADM();
			System.out.println("\nChoose your Action:" 
					+ "\n1 - Add Other Titular" 
					+ "\n2 - Remove Other Titular"
					+ "\n3 - Remove Account" 
					+ "\n4 - Transfer" 
					+ "\n5 - Deposit" 
					+ "\n6 - Draw" 
					+ "\n7 - Back");

			option = UI.getInt();
			switch (option) {
			case 1:
				if (accountService.verifyOtherTitulars(account)) {
					System.out.println("\n1 - New client" + "\n2 - Existing client");
					option = UI.getInt();
					while (option != 1 && option != 2) {
						System.out.print("\nWrong option. Choose again: ");
						option = UI.getInt();
					}
					Client client;
					if (option == 1) {
						NewInput newInput = new NewInput();
						client = newInput.newClient();
						if (client != null) {
							System.out.println("\nDo you want Debit Card?");
							option = UI.choose();
							if (option == 1) {
								if(debitCardService.newDebitCard(account, client) == null) {
									System.out.println("Can't make Debit Card!");
								}
							}
							
							System.out.println("\nDo you want Credit Card?");
							option = UI.choose();
							if (option == 1) {
								if(creditCardService.newCreditCard(account, client) == null) {
									System.out.println("Can´t make the Credit Card!");
								}
							}
							
							addOtherTitular(account, client);
						}
						
					} else {
						client = admInput.showClient();

						if (client != null) {

							System.out.println("\nDo you want Debit Card?");
							option = UI.choose();
							if (option == 1) {
								debitCardService.newDebitCard(account, client);
							}
							
							System.out.println("\nDo you want Credit Card?");
							option = UI.choose();
							if (option == 1) {
								creditCardService.newCreditCard(account, client);
							}
							addOtherTitular(account, client);
						}
					}
				} else {
					System.out.println("\nThis account already has four Other Titulars");
				}
				break;
			case 2:
				
				List<Client> clients = accountService.getAccountClients(account);
				
				Client client = admInput.showClient();
				
				if (client != null && clients.contains(client)) {
					accountService.removeOtherHolder(account, client);
					System.out.println("Client successfully removed");
				} 
				else {
					System.out.println("\nThis nif doesn't belong to this account");
				}
				break;
			case 3:
				accountService.removeAccount(account);
				break;
			case 4:
				Account accountTo = admInput.showAccount();
				if (accountTo != null) {
					if (account.getId_account() == accountTo.getId_account()) {
						System.out.println("\nCan't transfer money to the same account");
					} else {
						System.out.print("\nEnter the amount to transfer: ");
						double value = UI.getDouble();
						accountService.transfer(account, accountTo, value);
					}
				}
				break;
			case 5:
				System.out.print("\nEnter the Deposit value: ");
				double value = UI.getDouble();
				accountService.deposit(account, value);
				break;
			case 6:
				System.out.print("\nEnter the Draw value: ");
				value = UI.getDouble();
				accountService.draw(account, value);
				break;
			case 7:
				MenuADM.displayMenuADM();
				menuADM.selection();
				break;
			default:
				System.out.print("Wrong option. Choose again:");
				editAccount(account);
				break;
			}
			MenuADM.displayMenuADM();
			menuADM.selection();
		}
	}
	
	public void editClient(Client client) {
		System.out.println("\nWant to edit?");
		int option = UI.choose();
		if (option == 1) {
			MenuADM menuADM = new MenuADM();
			System.out.println("\nChoose your Action:" 
					+ "\n1 - Name" 
					+ "\n2 - Email"
					+ "\n3 - Cellphone" 
					+ "\n4 - Telephone" 
					+ "\n5 - Occupation" 
					+ "\n6 - Back");

			option = UI.getInt();
			switch (option) {
			case 1:
				System.out.print("\nName: ");
				String name = UI.scanLine();
				clientService.editName(client, name);
				break;
			case 2:
				System.out.print("\nEmail: ");
				String email = UI.scanLine();
				if (clientService.verifyEmail(email)) {
					clientService.editEmail(client, email);
				} else {
					System.out.println("Invalid email");
				}
				break;
			case 3:
				System.out.print("\nCellphone: ");
				String cellphone = UI.scanLine();
				clientService.editCellphone(client, cellphone);
				break;
			case 4:
				System.out.print("\nTelephone: ");
				String telephone = UI.scanLine();
				clientService.editTelephone(client, telephone);
				break;
			case 5:
				System.out.print("\nOccupation: ");
				String occupation = UI.scanLine();
				clientService.editOccupation(client, occupation);
				break;
			case 6:
				MenuADM.displayMenuADM();
				menuADM.selection();
				break;
			default:
				System.out.print("Wrong option. Choose again:");
				editClient(client);
				break;
			}
			MenuADM.displayMenuADM();
			menuADM.selection();
		}
	}
	
	public void editCreditCard(CreditCard creditCard) {
		System.out.println("\nWant to Delete?");
		int option = UI.choose();
		if (option == 1) {
//			TODO: falta verificação antes do delete tem que se ver plafond e dividas 
//			e se é o titular da conta se nao for e na boa porque as dividas passam para ele
			creditCardService.removeCreditCard(creditCard);
			System.out.println("\nCredit Card deleted!");
		}
	}
	
	public void editDebitCard(DebitCard debitCard) {
		System.out.println("\nWant to Delete?");
		int option = UI.choose();
		if (option == 1) {
//			TODO: falta verificação antes do delete tem que se ver dividas 
//			e se é o titular da conta se nao for e na boa porque as dividas passam para ele
			debitCardService.removeDebitCard(debitCard);
			System.out.println("\nDebit Card deleted!");
		}
	}
	
	private void addOtherTitular(Account account, Client client) {
		if(accountService.addOtherTitular(account, client)) {
			System.out.println("Inserted successfully");
		} else {
			System.out.println("This client can't be associated with this account");
		}
	}
}
