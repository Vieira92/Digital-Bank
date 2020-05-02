package com.rumos.bank.administration.services;

import java.util.Scanner;

import com.rumos.bank.administration.ADM;
import com.rumos.bank.administration.MenuADM;
import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;

public class AccountService {
	private Scanner sc = new Scanner(System.in);

	public Integer choose() {
		System.out.println("1 - Yes" + "\n2 - Not");
		Integer choose = sc.nextInt();
		while (choose != 1 && choose != 2) {
			System.out.print("\nWrong option. Choose again: ");
			choose = sc.nextInt();
		}
		return choose;
	}

	public Boolean verifyAccountCreditCards(Account account) {
		if (account.getCreditCards() == null || account.getCreditCards().size() <= 2) {
			return true;
		} else {
			System.out.println("Accounts can´t have more than two Credit Cards");
			return false;
		}

	}
	
	public void firstDeposit(Account account) {
		ClientService clientService = new ClientService();
		MenuADM menuADM = new MenuADM(); 
		System.out.print("\nEnter the deposit value: ");
		Double value = sc.nextDouble();
		while(value < 50.00) {
			System.out.println("The minimum value to open an account is 50.00 $");
			System.out.println("Want to cancel?");
			Integer option = choose();
			if(option == 1) {
				//um problema a conta e cancelada mas o cliente fica criado no sistema tenho que o apagar tambem
				if(account.getMainTitular().getAccounts() == null) {
					clientService.removeListClients(account.getMainTitular());
				}
				removeListAccounts(account);
				menuADM.displayMenu();
				menuADM.selection();
			} else {
				System.out.print("\nEnter the deposit value: ");
				value = sc.nextDouble();
			}
		} 
		account.setBalance(value);
	}

	public Account newAccount(Client client) {
		ADMservice admService = new ADMservice();
		ClientService clientService = new ClientService();
		DebitCardService debitCardService = new DebitCardService();
		CreditCardService creditCardService = new CreditCardService();

		Account account = new Account();
		account.setMainTitular(client);
		
		firstDeposit(account);
		
		account.setAccountNumber(ADM.accountNumber());
		client.addAccount(account);

		System.out.println("\nNew account:" 
						+ "\nMain Titular:" + client);

		System.out.println("\nDo you want Debit Card? ");
		Integer option = choose();
		if (option == 1) {
			if (debitCardService.verifyDebitCard(client) == false) {
				DebitCard debitCard = new DebitCard();
				debitCard = debitCardService.newDebitCard(account, client);
				System.out.println(debitCard);
			}
		}

		System.out.println("\nDo you want Credit Card?");
		option = choose();
		if (option == 1) {
			if (creditCardService.verifyCreditCard(client) == false) {
				CreditCard creditCard = new CreditCard();
				creditCard = creditCardService.newCreditCard(account, client);
				System.out.println(creditCard);
			}
		}
		client.addAccount(account);

		
		System.out.println("\nSecundary Titulars?");
		option = choose();
		if (option == 1) {
			Client otherClient = new Client();
			System.out.println("\nMaximum of 4 secondary titulars" + "\nHow many titulars? ");
			Integer number = sc.nextInt();
			while (number > 4 || number < 0) {
				System.out.print("\nWrong option. Choose again: ");
				number = sc.nextInt();
			}

			for (int i = 0; i < number; i++) {
				System.out.println("\n1 - New client" + "\n2 - Existing client" + "\n3 - Pass");
				option = sc.nextInt();
				while (option != 1 && option != 2 && option != 3) {
					System.out.print("\nWrong option. Choose again: ");
					option = sc.nextInt();
				}

				if (option == 1) { // Novo cliente secundario
					otherClient = clientService.newClient();
					
					System.out.println("\nDo you want Debit Card? ");
					option = choose();
					if (option == 1) {
						DebitCard debitCard1 = new DebitCard();
						debitCard1 = debitCardService.newDebitCard(account, otherClient);
						System.out.println(debitCard1);
					} 
					System.out.println(otherClient);
					account.addOtherTitular(otherClient);
					otherClient.addAccount(account);

				} else if (option == 2) { // cliente existente
					System.out.println("Enter the nif:");
					String nif = sc.next();
					if (admService.showClient(nif) != null) {
						//TODO: falta metodo para verificar se o cliente e da mesma conta se for nao pode
						System.out.println("Do you want Debit Card?");
						option = choose();
						if (option == 1) {
							if (debitCardService.verifyDebitCard(admService.showClient(nif)) == false) {
								DebitCard debitCard2 = new DebitCard();
								debitCard2 = debitCardService.newDebitCard(account, admService.showClient(nif));
								System.out.println(debitCard2.toStringClient());
							} 	
						}
						System.out.println(admService.showClient(nif));
						account.addOtherTitular(admService.showClient(nif));
						admService.showClient(nif).addAccount(account);
					}
				} else {
					System.out.println("Pass");
				}
			}
			
			System.out.println("\nDo you want Credit Card?");
			option = choose();
			if (option == 1) {
				System.out.println("\nClient Nif: ");
				String nif = sc.next();
				if(admService.showClient(nif)!= null) {
					if (creditCardService.verifyCreditCard(admService.showClient(nif)) == false) {
						CreditCard creditCard = new CreditCard();
						creditCard = creditCardService.newCreditCard(account, client);
						System.out.println(creditCard.toStringClient());
					}
				}
			}
		}
		addListAccounts(account);
		System.out.println(account);	
		return account;
	}

	public void editAccount(Account account) {
		MenuADM menuADM = new MenuADM();
		ADMservice admService = new ADMservice();
		
		System.out.println("\nChoose your Action:"
				+ "\n1 - Add Other Titular"
				+ "\n2 - Remove Other Titular"
				+ "\n3 - Remove Account"
				+ "\n4 - Transfer"
				+ "\n5 - Deposit"
				+ "\n6 - Draw"
				+ "\n7 - Back");
		
		Integer option = sc.nextInt();
		switch(option) {
		case 1:
			if(account.getOtherTitulars() == null || account.getOtherTitulars().size() <= 4) {
				addOtherTitular(account);	
			} else {
				System.out.println("This account already has four Other Titulars");
			}
			break;
		case 2:
			System.out.println("\nEnter the nif:");
			String nif = sc.next(); //TODO: da erro ver
			if(admService.showClient(nif) != null) {
				//TODO:metodo para verificar se o cliente e da mesma conta se for nao pode
				removeOtherHolder(account, admService.showClient(nif));	
			}
			break;
		case 3:
			removeAccount(account);
			break;
		case 4:
			System.out.println("\nEnter the account number: ");
			Long accountNumber = sc.nextLong();
			if(admService.showAccount(accountNumber) != null) {
				if(account.getAccountNumber() == accountNumber) {
					System.out.println("Can't transfer money to the same account");
					editAccount(account);
				} else {
					System.out.print("\nEnter the amount to transfer: ");
					double value = sc.nextDouble();
					transfer(account, admService.showAccount(accountNumber), value);
				}
			}
			break;
		case 5:
			System.out.print("\nEnter the Deposit value: ");
			Double value = sc.nextDouble();
			deposit(account, value);
			break;
		case 6:
			System.out.print("\nEnter the Draw value: ");
			value = sc.nextDouble();
			draw(account, value);
			break;
		case 7:
			menuADM.displayMenu();
			menuADM.selection();
			break;
		default:
			System.out.print("Wrong option. Choose again:");
			editAccount(account);
			break;
		}
		menuADM.displayMenu();
		menuADM.selection();
	}
	
	//----------------------------------------------------------------------
	
	private void addOtherTitular(Account account) {
		//TODO: adicionar outro titular à conta
		ClientService clientService = new ClientService();
		DebitCardService debitCardService = new DebitCardService();
		ADMservice admService = new ADMservice();
		
			System.out.println("\n1 - New client" + "\n2 - Existing client");
			Integer option = sc.nextInt();
			while (option != 1 && option != 2) {
				System.out.print("\nWrong option. Choose again: ");
				option = sc.nextInt();
			}
			if(option == 1) {
				Client client = clientService.newClient();
				client.addAccount(account);
				account.addOtherTitular(client);
				System.out.println("\nDo you want Debit Card?");
				option = choose();
				if (option == 1) {
					DebitCard debitCard = debitCardService.newDebitCard(account, client);
					System.out.println(debitCard.toStringClient());
				}	
			} else {
				System.out.println("\nEnter the nif:");
				String nif = sc.next();
				if (admService.showClient(nif) != null) {
					//falta metodo para verificar se o cliente e da mesma conta se for nao pode
					account.addOtherTitular(admService.showClient(nif));
					admService.showClient(nif).addAccount(account);
					System.out.println(admService.showClient(nif));
					System.out.println("\nDo you want Debit Card?");
					option = choose();
					if (option == 1) {
						if (debitCardService.verifyDebitCard(admService.showClient(nif)) == false) {
							DebitCard debitCard = debitCardService.newDebitCard(account, admService.showClient(nif));
							System.out.println(debitCard.toStringClient());
						} 	
					}
				}
			}	
	}
	
	private void removeOtherHolder(Account account, Client client) {
		//TODO: remover outro titular da conta
		ClientService clientService = new ClientService();
		CreditCardService creditCardService = new CreditCardService();
		DebitCardService debitCardService = new DebitCardService();
		
		
		if(client.getDebitCard().getAccount() == account) {
			debitCardService.removeListDebitCards(client.getDebitCard());
			client.setDebitCard(null);
		}
		if(client.getCreditCard().getAccount() == account) {
			creditCardService.removeListCreditCards(client.getCreditCard());
			client.setCreditCard(null);	
		}
		account.removeOtherTitular(client);
		client.removeAccount(account);
	
		if(client.getAccounts() == null) {
			clientService.removeListClients(client);
			
		}
		
	}
	
	private void draw(Account account, Double value) {
		//TODO: levantar guita
		if(account.getBalance() > value) {
			account.setBalance(account.getBalance() - value);
			System.out.println(account);
		} else {
			System.out.println("Don't have that amount in account");
		}
	}
	
	private void transfer(Account accountFrom, Account accountFor, Double value) {
		//TODO: tranferir guita entre contas
		if(accountFrom.getBalance() > value) {
			accountFrom.setBalance(accountFrom.getBalance() - value);
			accountFor.setBalance(accountFor.getBalance() + value);
			System.out.println(accountFrom);
		} else {
			System.out.println("Don't have that amount in account");
		}
		
	}
	
	private void deposit(Account account, Double value) {
		//TODO: depositar guita
		account.setBalance(account.getBalance() + value);
		System.out.println(account);
	}
	
	private void removeAccount(Account account) {
		//TODO: remover conta
		ClientService clientService = new ClientService();
		DebitCardService debitCardService = new DebitCardService();
		CreditCardService creditCardService = new CreditCardService();
		
		account.getMainTitular().removeAccount(account);
		
		if(account.getMainTitular().getDebitCard() != null) {
			if(account.getDebitCards().contains(account.getMainTitular().getDebitCard())) {
				debitCardService.removeListDebitCards(account.getMainTitular().getDebitCard());
				account.getMainTitular().setDebitCard(null);
			}
		}
		
		if(account.getMainTitular().getCreditCard() != null) {
			if(account.getCreditCards().contains(account.getMainTitular().getCreditCard())) {
				creditCardService.removeListCreditCards(account.getMainTitular().getCreditCard());
				account.getMainTitular().setCreditCard(null);
			}
		}
		
		if(account.getMainTitular().getAccounts() == null) {
			clientService.removeListClients(account.getMainTitular());
		}
		
		
		if(account.getOtherTitulars() != null) {
			for(Client client : account.getOtherTitulars()) {
				client.removeAccount(account);
				if(client.getDebitCard() != null) {
					if(account.getDebitCards().contains(client.getDebitCard())) {
						debitCardService.removeListDebitCards(client.getDebitCard());
						client.setDebitCard(null);
					}
				}
				
				if(client.getCreditCard() != null) {
					if(account.getCreditCards().contains(client.getCreditCard())) {
						creditCardService.removeListCreditCards(client.getCreditCard());
						client.setCreditCard(null);
					}
				}
				
				if(client.getAccounts() == null) {
					clientService.removeListClients(client);
				}
				
			}
		} 
		removeListAccounts(account);		
	}

	//---------------------------------------------------

	public void addListAccounts(Account account) {
		ADM.accounts.add(account);
	}

	public void removeListAccounts(Account account) {
		ADM.accounts.remove(account);
	}

}
