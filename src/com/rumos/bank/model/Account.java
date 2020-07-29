package com.rumos.bank.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Account  implements Serializable {

	private static final long serialVersionUID = -464613278489188233L;
	
	private int id_account; 
	private Client mainTitular;
	private double balance;
	private LocalDate creation;
	private List<Client> otherTitulars = new ArrayList<>();
	private List<DebitCard> debitCards = new ArrayList<>();
	private List<CreditCard> creditCards = new ArrayList<>();

	

	
	public Account(int id_account) {
		this.id_account = id_account;
	}
	
	public Account(Client mainTitular, double balance) {
		this.mainTitular = mainTitular;
		this.balance = balance;
		this.creation = LocalDate.now();
	}
	
	public Account(int id_account, Client mainTitular, double balance, LocalDate creation) {
		this.id_account = id_account;
		this.mainTitular = mainTitular;
		this.balance = balance;
		this.creation = creation;
	}
	
	@Override
	public String toString() {
		Locale.setDefault(Locale.US);
		StringBuilder sb = new StringBuilder();
		sb.append("Account Id: " + id_account 
				+ "    Main titular: " + mainTitular.getName() 
				+ "  Id: " + mainTitular.getId_client()
				+ "    Balance: " + String.format("%.2f", balance));
		
		String account = sb.toString();
		return account;
	}
	
	//------------------------------------------------------
	
	public int getId_account() { return id_account;	}

	public Client getMainTitular() { return mainTitular; }

	public void setMainTitular(Client mainTitular) { this.mainTitular = mainTitular; }
	
	public Double getBalance() { return balance; }

	public void setBalance(Double balance) { this.balance = balance; }
	
	public LocalDate getCreation() { return creation; }
	
	//---------------------------------------------------
	
	public void addOtherTitular(Client otherTitular) { otherTitulars.add(otherTitular);	}
	
	public void removeOtherTitular(Client otherTitular) { otherTitulars.remove(otherTitular); }
	
	public List<Client> getOtherTitulars() { return otherTitulars; }
	
	public void addDebitCard(DebitCard debitCard) {	debitCards.add(debitCard); }
	
	public void removeDebitCard(DebitCard debitCard) { debitCards.remove(debitCard); }
	
	public List<DebitCard> getDebitCards() { return debitCards; }
	
	public void addCreditCard(CreditCard creditCard) { creditCards.add(creditCard); }
	
	public void removeCreditCard(CreditCard creditCard) { creditCards.remove(creditCard); }
	
	public List<CreditCard> getCreditCards() { return creditCards;	}

	@Override
	public int hashCode() {
		return Objects.hash(id_account);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true;	}
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		Account other = (Account) obj;
		return id_account == other.id_account;
	}
	

}
