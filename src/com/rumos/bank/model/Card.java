package com.rumos.bank.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Card {
	
	protected int idCard;
	protected Account account;
	protected Client titular;
	protected LocalDate creation;
	protected LocalDate expire;
	
	
	public Card(Account account, Client titular) {
		this.account = account;
		this.titular = titular;
		this.creation =  LocalDate.now();
		this.expire = creation.plusYears(4);
	}
	
	public int getIdCard() { return idCard; }

	public Account getAccount() { return account; }
	
	public Client getTitular() { return titular; }
	
	public LocalDate getCreation() { return creation; }
	
	public String getFormattedCreation() {
		return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(creation);
	}

	public void setCreation(LocalDate creation) { this.creation = creation; }
	
	public LocalDate getExpire() { return expire; }
	
	public String getFormattedExpire() {
		return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(expire);
	}

	public void setExpire(LocalDate expire) { this.expire = expire;	}
}
