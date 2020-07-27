package com.rumos.bank.administration.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class CreditCard extends Cards {

	private int id_creditCard;
	private Account account;
	private Client titular;
	private LocalDate creation;
	private LocalDate expire;
	private double plafond;
	private final String  type = "C";
//	valor já levantado hoje (dia)
//	atribuiçao de pin e alteraçao
//	private Integer pin;
		
	
	
	
	public CreditCard(Account account, Client titular) {
		this.account = account;
		this.titular = titular;
		this.creation = LocalDate.now();
		this.expire = creation.plusYears(4);
		this.plafond = 500.00;
	}
	
	public CreditCard(int id_creditCard, Account account, Client titular, LocalDate creation, LocalDate expire,
			double plafond) {
		this.id_creditCard = id_creditCard;
		this.account = account;
		this.titular = titular;
		this.creation = creation;
		this.expire = expire;
		this.plafond = plafond;
	}

	@Override
	public String toString() {
		Locale.setDefault(Locale.US);
		return "CreditCard Id: " + id_creditCard 
				+ "    Account Id: " + account.getId_account()				
				+ "    Made: " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(creation)
				+ "  Expire: " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(expire)
				+ "    Owner Id: " + titular.getId_client()
				+ "  " + titular.getName()
				+ "    Planfond: " + String.format("%.2f", plafond);
	}
	
	//-------------------------------------------------------

	public int getId_creditCard() { return id_creditCard; }

	public Account getAccount() { return account; }
	
	public Client getTitular() { return titular; }
	
	public LocalDate getCreation() { return creation; }

	public void setCreation(LocalDate creation) { this.creation = creation; }
	
	public LocalDate getExpire() { return expire; }

	public void setExpire(LocalDate expire) { this.expire = expire;	}
	
	public double getPlafond() { return plafond; }

	public void setPlafond(double plafond) { this.plafond = plafond; }

	public String getType() { return type; }

	@Override
	public int hashCode() {
		return Objects.hash(id_creditCard);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		CreditCard other = (CreditCard) obj;
		return id_creditCard == other.id_creditCard;
	}
}
