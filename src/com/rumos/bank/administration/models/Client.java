package com.rumos.bank.administration.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Client {

	private Long clientNumber;
	private String name;
	private Date birth;
	private String nif;
	private String email;
	private String cellphone;
	private String telephone;
	private String occupation;
//	private String password;
	private CreditCard creditCard;
	private DebitCard debitCard;
	private ArrayList<Account> accounts = new ArrayList<>();
	
	//---------------------------------------------------------------
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String birthFormated = sdf.format(birth);
		return 	 "\nNumber: " + clientNumber 
				+ "\nName: " + name 
				+ "\nBirth: " + birthFormated 
				+ "\nNif: " + nif
				+ "\nEmail: " + email 
				+ "\nCellphone: " + cellphone 
				+ "\nTelephone: " + telephone 
				+ "\nOccupation: " + occupation;
	}
	
	//---------------------------------------------------------------
	
	public Long getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
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
