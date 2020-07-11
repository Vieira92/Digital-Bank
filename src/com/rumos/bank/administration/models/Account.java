package com.rumos.bank.administration.models;

import java.util.ArrayList;

public class Account {

	private int accountNumber;
	private Client mainTitular;
	private double balance;
	private ArrayList<Client> otherTitulars = new ArrayList<>();
	private ArrayList<DebitCard> debitCards = new ArrayList<>();
	private ArrayList<CreditCard> creditCards = new ArrayList<>();

	
	public Account() {
	}
	
	public Account(int accountNumber, Client mainTitular, double balance, ArrayList<Client> otherTitulars) {
		this.accountNumber = accountNumber;
		this.mainTitular = mainTitular;
		this.balance = balance;
		this.otherTitulars = otherTitulars;
	}
	
	@Override
	public String toString() {
//		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		String birthFormated = dtFormatter.format(date);
		StringBuilder sb = new StringBuilder();
		sb.append("\nAccount Nº: " + accountNumber 
				+ "\nMain titular: " + mainTitular.getName() + "  Nº: " + mainTitular.getClientNumber());
		
		if(!otherTitulars.isEmpty()) {
			for(Client client : otherTitulars) {
				sb.append("\nOther titular: " + client.getName() + " Nº: " + client.getClientNumber());
			}
		}
		
		sb.append("\nBalance: " + String.format("%.2f", balance));
		
		if(!debitCards.isEmpty()) {
			sb.append("\n");
			for(DebitCard debitCard : debitCards) {
				sb.append("\nDebit card: " + debitCard.getDebitCardNumber() + " Titular Nº: " + debitCard.getTitular().getClientNumber());
			}
		}
		
		if(!creditCards.isEmpty()) {
			sb.append("\n");
			for(CreditCard creditCard : creditCards) {
				sb.append("\nCredit card: " + creditCard.getCreditCardNumber() + " Titular Nº: " + creditCard.getTitular().getClientNumber());
			}
		}

		String account = sb.toString();
		return account;
	}

	//------------------------------------------------------
	
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Client getMainTitular() {
		return mainTitular;
	}
	public void setMainTitular(Client mainTitular) {
		this.mainTitular = mainTitular;
	}
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	//---------------------------------------------------
	

	public void addOtherTitular(Client otherTitular) {
		otherTitulars.add(otherTitular);
	}
	
	public void removeOtherTitular(Client otherTitular) {
		otherTitulars.remove(otherTitular);
	}
	
	
	public ArrayList<Client> getOtherTitulars() {
		return otherTitulars;
	}
	
	public void addDebitCard(DebitCard debitCard) {
		debitCards.add(debitCard);
	}
	
	public void removeDebitCard(DebitCard debitCard) {
		debitCards.remove(debitCard);
	}
	
	public ArrayList<DebitCard> getDebitCards() {
		return debitCards;
	}
	
	public void addCreditCard(CreditCard creditCard) {
		creditCards.add(creditCard);
	}
	
	public void removeCreditCard(CreditCard creditCard) {
		creditCards.remove(creditCard);
	}
	
	public ArrayList<CreditCard> getCreditCards() {
		return creditCards;
	}
}
