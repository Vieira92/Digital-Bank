package pt.rumos.bank.userInterface;

import java.time.LocalDate;
import java.util.ArrayList;

import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Client;
import pt.rumos.bank.model.CreditCard;
import pt.rumos.bank.model.DebitCard;
import pt.rumos.bank.service.AccountService;
import pt.rumos.bank.service.ClientService;
import pt.rumos.bank.service.CreditCardService;
import pt.rumos.bank.service.DebitCardService;
import pt.rumos.bank.userInterface.ADMinput;
import pt.rumos.bank.userInterface.MenuADM;
import pt.rumos.bank.userInterface.UI;

public class NewInput {

	private ClientService clientService;
	private AccountService accountService;
	private CreditCardService creditCardService;
	private DebitCardService debitCardService;
	private MenuADM menuADM;
	private ADMinput admInput;

	public NewInput() {
		clientService = new ClientService();
		accountService = new AccountService();
		creditCardService = new CreditCardService();
		debitCardService = new DebitCardService();
		menuADM = new MenuADM();
		admInput = new ADMinput();
	}

	
	public Client newClient() {

		System.out.println("\nNew Client:");
		System.out.print("Full Name: ");
		String name = clientService.nameFormat(UI.scanLine());

		System.out.print("Date of Birth:" + "\nDay: ");
		int day = UI.getInt();
		System.out.print("Month: ");
		int month = UI.getInt();
		System.out.print("Year: ");
		int year = UI.getInt();
		LocalDate birth = LocalDate.of(year, month, day);
		while (!clientService.verifyBirth(birth)) {
			System.out.println("Invalid age, client must have age between 2 and 120 years");
			System.out.println("year, month, day:");
			birth = LocalDate.of(UI.getInt(), UI.getInt(), UI.getInt());
		}

		System.out.print("NIF: ");
		String nif = UI.scanLine();
		// TODO: falta metodo de verificacao formataçao de nif

		System.out.print("Email: ");
		String email = UI.scanLine();
		// TODO: na verificacao falta por que no mail nao pode haver espacos por exemplo
		while (!clientService.verifyEmail(email)) {
			System.out.println("Invalid email. write again:");
			email = UI.scanLine();
		}
		email.toLowerCase();

		System.out.print("Cellphone: ");
		String cellphone = UI.scanLine();
		// TODO: falta metodo de verificacao formataçao de cellphone

		System.out.print("Telephone: ");
		String telephone = UI.scanLine();
		// TODO: falta metodo de verificacao formataçao de telephone

		System.out.print("Occupation: ");
		String occupation = UI.scanLine();
		occupation = occupation.substring(0, 1).toUpperCase() + occupation.substring(1).toLowerCase();

		return clientService.newClient(name, birth, nif, email, cellphone, telephone, occupation);
	}

	public boolean verifyTitularAccount(Client client) {
		if (clientService.verifyAge(client)) {
			if (clientService.verifyTitular(client) == false) {
				return true;
			} else { System.out.println("\nClient can't hold two titular accounts"); }
			
		} else { System.out.println("\nClient must have 18 years" + "\ncan't be an account titular"); }
		return false;
	}

	public Account newAccount(Client client) {
		System.out.println("\nNew Account:");

		ArrayList<Client> otherTitulars = new ArrayList<>();
		ArrayList<Client> debitCards = new ArrayList<>();
		ArrayList<Client> creditCards = new ArrayList<>();

		System.out.print("Enter the deposit value: ");
		double balance = UI.getDouble();
		while (balance < 50.00) {
			System.out.println("\nThe minimum value to open an account is 50.00 $");
			System.out.println("Want to cancel?");
			int choose = UI.choose();
			if (choose == 1) {
				clientService.deleteClient(client);
				MenuADM.displayMenuADM();
				menuADM.selection();
			}
			System.out.print("\nEnter the deposit value: ");
			balance = UI.getDouble();
		}

		System.out.println("\nDo you want Debit Card? ");
		int option = UI.choose();
		if (option == 1) { debitCards.add(client); }

		System.out.println("\nDo you want Credit Card?");
		option = UI.choose();
		if (option == 1) { creditCards.add(client); }

//		--------------------------------------------------------------------------------------------
		
		System.out.println("\nSecundary Titulars?");
		option = UI.choose();
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
					otherClient = newClient();
					if (otherClient != null) {
						System.out.println("\nDo you want Debit Card? ");
						option = UI.choose();
						if (option == 1) { debitCards.add(otherClient); }
						
						if (creditCards.size() <= 2) {
							System.out.println("\nDo you want Credit Card?");
							option = UI.choose();
							if (option == 1) { creditCards.add(otherClient); }
						}
						otherTitulars.add(otherClient);
					}
					
				} else if (option == 2) { // cliente existente
					otherClient = admInput.showClient();
					if (!otherTitulars.contains(otherClient)) {
						if (otherClient != null && otherClient != client) {
							System.out.println("\nDo you want Debit Card?");
							option = UI.choose();
							if (option == 1) { debitCards.add(otherClient); }

							if (creditCards.size() <= 2) {
								System.out.println("\nDo you want Credit Card?");
								option = UI.choose();
								if (option == 1) { creditCards.add(otherClient); }
							}
							otherTitulars.add(otherClient);
						}
					} else { System.out.println("This client has already been added to the account"); }

				} else { System.out.println("Pass"); }
			}
		}
		return accountService.newAccount(client, balance, otherTitulars, debitCards, creditCards);
	}

	public void newCreditCard() {
		Client client = admInput.showClient();
		if (client != null) {
			Account account = admInput.showAccount();

			if (account != null && accountService.getAccountClients(account).contains(client)) {
				CreditCard creditCard = creditCardService.newCreditCard(account, client);
				if (creditCard != null) { System.out.println(creditCard); } 
				else { System.out.println("Can´t make the Credit Card!"); }
			}
		}
	}

	public void newDebitCard() {
		Client client = admInput.showClient();
		if (client != null) {
			Account account = admInput.showAccount();

			if (account != null && accountService.getAccountClients(account).contains(client)) {
				DebitCard debitCard = debitCardService.newDebitCard(account, client);
				if (debitCard != null) { System.out.println(debitCard); } 
				else { System.out.println("Can't make Debit Card!"); }
			}
		}
	}
}
