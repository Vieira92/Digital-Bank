package com.rumos.bank.administration.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Client {

	private int clientNumber;
	private String name;
	private LocalDate birth;
	private String nif;
	private String email;
	private String cellphone;
	private String telephone;
	private String occupation;
//	private String password;
	private CreditCard creditCard;
	private DebitCard debitCard;
	private ArrayList<Account> accounts = new ArrayList<>();
	
	
	public Client(int clientNumber, String name, LocalDate birth, String nif, String email, String cellphone,
			String telephone, String occupation) {
		this.clientNumber = clientNumber;
		this.name = name;
		this.birth = birth;
		this.nif = nif;
		this.email = email;
		this.cellphone = cellphone;
		this.telephone = telephone;
		this.occupation = occupation;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String birthFormated = dtFormatter.format(birth);
		StringBuilder sb = new StringBuilder();
		sb.append("\nName: " + name 
				+ "\nNumber: " + clientNumber
				+ "\nBirth: " + birthFormated 
				+ "\nNif: " + nif
				+ "\nEmail: " + email 
				+ "\nCellphone: " + cellphone 
				+ "\nTelephone: " + telephone 
				+ "\nOccupation: " + occupation);
		sb.append("\n");
		if(debitCard != null) {
			sb.append("\nDebit card Nº:  " + debitCard.getDebitCardNumber() 
					+ "   account: " + debitCard.getAccount().getAccountNumber());
		}
		
		if(creditCard != null) {
			sb.append("\nCredit card Nº: " + creditCard.getCreditCardNumber() 
					+ "   account: " + creditCard.getAccount().getAccountNumber());
		}
		
		if(accounts.size() < 2) {
			sb.append("\nAccount:");
		}
		else {
			sb.append("\nAccounts:");
		}
		for(Account account : accounts) {
			sb.append("\nNº: " + account.getAccountNumber());
			sb.append("   Main titular: " + account.getMainTitular().name);
			sb.append("	Balance: " + String.format("%.2f", account.getBalance()));
		}

		String client = sb.toString();
		return client;
	}

	//---------------------------------------------------------------
	
	public int getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public DebitCard getDebitCard() {
		return debitCard;
	}

	public void setDebitCard(DebitCard debitCard) {
		this.debitCard = debitCard;
	}
	
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	
	//-----------------------------------------------
	

	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);
	}
}
