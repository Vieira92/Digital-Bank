package com.rumos.bank.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client implements Serializable {

	private static final long serialVersionUID = -1986525952012234520L;
	
	private int id_client;
	private String name;
	private LocalDate birth;
	private String nif;
	private String email;
	private String cellphone;
	private String telephone;
	private String occupation;
	private LocalDate creation;
//	private String password;
	private CreditCard creditCard;
	private DebitCard debitCard;
	private List<Account> accounts = new ArrayList<>();
	

	
	
	public Client(int id_client, String name) {
		this.id_client = id_client;
		this.name = name;
	}

	public Client(String name, LocalDate birth, String nif, String email, String cellphone, String telephone,
			String occupation ) {
		this.name = name;
		this.birth = birth;
		this.nif = nif;
		this.email = email;
		this.cellphone = cellphone;
		this.telephone = telephone;
		this.occupation = occupation;
		this.creation = LocalDate.now();
	}
	
	public Client(int id_client, String name, LocalDate birth, String nif, String email, String cellphone,
			String telephone, String occupation, LocalDate creation) {
		this.id_client = id_client;
		this.name = name;
		this.birth = birth;
		this.nif = nif;
		this.email = email;
		this.cellphone = cellphone;
		this.telephone = telephone;
		this.occupation = occupation;
		this.creation = creation;
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("Id: " + id_client 
				+ "   " + name
				+ "   Nif: " + nif
				+ "   Birth: " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(birth)
				+ "   Email: " + email 
				+ "   Cellphone: " + cellphone 
				+ "   Telephone: " + telephone 
				+ "   Occupation: " + occupation);
		
		if(debitCard != null) {
			sb.append("\nDebitCard  Id: " + debitCard.getIdCard() 
					+ "    Account Id: " + debitCard.getAccount().getId_account()
					+ "    Made: " + debitCard.getFormattedCreation()
					+ "    Expire: "  + debitCard.getFormattedExpire());
		}
		
		if(creditCard != null) {
			sb.append("\nCreditCard Id: " + creditCard.getIdCard() 
					+ "    Account Id: " + creditCard.getAccount().getId_account()
					+ "    Made: " + creditCard.getFormattedCreation()
					+ "    Expire: "  + creditCard.getFormattedExpire());
		}
		
		for(Account account : accounts) {
			sb.append("\nAccount Id: " + account.getId_account());
			sb.append("    Main titular: " + account.getMainTitular().name);
			sb.append("    Balance: " + String.format("%.2f", account.getBalance()));
		}
		
		String client = sb.toString();
		return client;
	}
		


	public int getId_client() {	return id_client; }

	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }

	public LocalDate getBirth() { return birth; }

	public String getNif() { return nif; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public String getCellphone() { return cellphone; }

	public void setCellphone(String cellphone) { this.cellphone = cellphone; }

	public String getTelephone() { return telephone; }

	public void setTelephone(String telephone) { this.telephone = telephone; }

	public String getOccupation() { return occupation; }

	public void setOccupation(String occupation) { this.occupation = occupation; }
	
	public LocalDate getCreation() { return creation; }

	public DebitCard getDebitCard() { return debitCard; }

	public void setDebitCard(DebitCard debitCard) { this.debitCard = debitCard; }
	
	public CreditCard getCreditCard() { return creditCard; }

	public void setCreditCard(CreditCard creditCard) { this.creditCard = creditCard; }
	


	@Override
	public int hashCode() {
		return Objects.hash(id_client, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		Client other = (Client) obj;
		return id_client == other.id_client && Objects.equals(name, other.name);
	}
	
}
