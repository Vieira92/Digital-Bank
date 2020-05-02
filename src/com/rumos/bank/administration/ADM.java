package com.rumos.bank.administration;

import java.util.ArrayList;

import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;

public class ADM {

	private static Long accountNumber = 100L;
	private static Long clientNumber = 0L;
	private static Long creditCardNumber = 10L;
	private static Long debitCardNumber = 10L;
	public static ArrayList<Client> clients = new ArrayList<>();
	public static ArrayList<Account> accounts = new ArrayList<>();
	public static ArrayList<DebitCard> debitCards = new ArrayList<>();
	public static ArrayList<CreditCard> creditCards = new ArrayList<>();
	
	
	public static Long accountNumber() {
		return ++accountNumber;
	}
	
	public static Long clientNumber() {
		return ++clientNumber;
	}
	
	public static Long creditCardNumber() {
		return ++creditCardNumber;
	}
	
	public static Long debitCardNumber() {
		return ++debitCardNumber;
	}
}
