package com.rumos.bank.administration.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DebitCard extends Cards {

	private int id_debitCard;
	private Account account;
	private Client titular;
	private LocalDate creation;
	private LocalDate expire;
	private final String  type = "D";
	// valor já levantado hoje (dia)
	// atribuiçao de pin e alteraçao
	//private Integer pin;
	
	
	
	
	public DebitCard(Account account, Client titular) {
		this.account = account;
		this.titular = titular;
		this.creation = LocalDate.now();
		this.expire = creation.plusYears(4);
	}
		
	public DebitCard(int id_debitCard, Account account, Client titular, LocalDate creation, LocalDate expire) {
		this.id_debitCard = id_debitCard;
		this.account = account;
		this.titular = titular;
		this.creation = creation;
		this.expire = expire;
	}

	@Override
	public String toString() {
		return "DebitCard  Id: " + id_debitCard 
				+ "    Account Id: " + account.getId_account()
				+ "    Made: " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(creation)
				+ "  Expire: " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(expire)
				+ "    Owner Id: " + titular.getId_client()
				+ "  "+ titular.getName();
	}
		
	//--------------------------------------------------------
	
	public int getId_debitCard() { return id_debitCard; }

	public Account getAccount() { return account; }
		
	public Client getTitular() { return titular; }
	
	public LocalDate getCreation() { return creation; }
	
	public void setCreation(LocalDate creation) { this.creation = creation;	}

	public LocalDate getExpire() { return expire; }

	public void setExpire(LocalDate expire) { this.expire = expire;	}
	
	public String getType() { return type; }

	@Override
	public int hashCode() {
		return Objects.hash(id_debitCard);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true;	}
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		DebitCard other = (DebitCard) obj;
		return id_debitCard == other.id_debitCard;
	}
}

