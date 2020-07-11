package com.rumos.bank.administration.models;

public class DebitCard {

	private int debitCardNumber;
	private Account account;
	private Client titular;
	// valor já levantado hoje (dia)
	// atribuiçao de pin e alteraçao
	//private Integer pin;
	
	public DebitCard(int debitCardNumber, Account account, Client titular) {
		this.debitCardNumber = debitCardNumber;
		this.account = account;
		this.titular = titular;
	}
	
	@Override
	public String toString() {
		return "\nDebitCard:" 
				+ "\nNumber: " + debitCardNumber 
				+ "\nAccount number: " + account.getAccountNumber() 
				+ "\nTitular: " + titular.getName()
				+ "\nTitular number: " + titular.getClientNumber();
	}
	
	public String toStringClient() {
		return titular
				+ "\nDebit Card:" 
				+ "\n	Number: " + debitCardNumber 
				+ "\n	Account number: " + account.getAccountNumber() 
				+ "\n	Titular: " + titular.getName()
				+ "\n	Titular number: " + titular.getClientNumber();
	}
	
	//--------------------------------------------------------
	
	public int getDebitCardNumber() {
		return debitCardNumber;
	}
	
	public void setDebitCardNumber(int debitCardNumber) {
		this.debitCardNumber = debitCardNumber;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Client getTitular() {
		return titular;
	}
	public void setTitular(Client titular) {
		this.titular = titular;
	}
}
