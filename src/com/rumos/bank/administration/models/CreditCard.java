package com.rumos.bank.administration.models;

public class CreditCard {

	private Long creditCardNumber;
	private Account account;
	private Client titular;
	
	@Override
	public String toString() {
		return "\nCreditCard:" 
				+  "\nNumber: " + creditCardNumber 
				+ "\nAccount number: " + account.getAccountNumber() 
				+ "\nTitular: " + titular.getName()
				+ "\nTitular number: " + titular.getClientNumber();
	}
	
	public String toStringClient() {
		return titular
				+ "\nCredit Card:" 
				+ "\n	Number: " + creditCardNumber 
				+ "\n	Account number: " + account.getAccountNumber() 
				+ "\n	Titular: " + titular.getName()
				+ "\n	Titular number: " + titular.getClientNumber();
	}
	
	
	//-------------------------------------------------------
	

	public Long getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(Long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
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


