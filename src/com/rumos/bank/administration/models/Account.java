package com.rumos.bank.administration.models;

import java.util.ArrayList;

public class Account {

	private Long accountNumber;
	private Client mainTitular;
	private Double balance;
	private ArrayList<Client> otherTitulars = new ArrayList<>();
	private ArrayList<DebitCard> debitCards = new ArrayList<>();
	private ArrayList<CreditCard> creditCards = new ArrayList<>();

	
	@Override
	public String toString() {
		return "\nAccount number: " + accountNumber 
				+ "\nMain titular: " + mainTitular.getName() + ", number: " + mainTitular.getClientNumber()
				+ "\nOther titulars: " + !otherTitulars.isEmpty()
				+ "\nBalance: " + balance;
	}

	//------------------------------------------------------
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
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

	//----------------------------------------------------	
}
