package pt.rumos.bank.model;

public class Movement {

	String id_movement;
	String id_account;
	String amount;
	String balance;
	String date;
	
	
	public Movement(String id_movement, String id_account, String amount, String balance, String date) {
		this.id_movement = id_movement;
		this.id_account = id_account;
		this.amount = amount;
		this.balance = balance;
		this.date = date;
	}

	@Override
	public String toString() {
		return date + "     " + balance + "     " + amount;
	}

	
	public String getId_movement() { return id_movement; }

	public String getId_account() {	return id_account; }

	public String getAmount() {	return amount; }

	public String getBalance() { return balance; }

	public String getDate() { return date; }		
}
