package com.rumos.bank.administration;

import java.util.ArrayList;

import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;

public class ADM {

	private static int accountNumber = 100;
	private static int clientNumber = 0;
	private static int creditCardNumber = 10;
	private static int debitCardNumber = 10;
	public static ArrayList<Client> clients = new ArrayList<>();
	public static ArrayList<Account> accounts = new ArrayList<>();
	public static ArrayList<DebitCard> debitCards = new ArrayList<>();
	public static ArrayList<CreditCard> creditCards = new ArrayList<>();
	
	
	public static int accountNumber() {
		return ++accountNumber;
	}
	
	public static int clientNumber() {
		return ++clientNumber;
	}
	
	public static int creditCardNumber() {
		return ++creditCardNumber;
	}
	
	public static int debitCardNumber() {
		return ++debitCardNumber;
	}
}
