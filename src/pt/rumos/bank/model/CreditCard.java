package pt.rumos.bank.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class CreditCard extends Card implements Serializable {

	private static final long serialVersionUID = 9138602685903142518L;
	
	private double plafond;
	private final String  type = "C";

	
	public CreditCard(Account account, Client titular) {
		super(account, titular);
		this.plafond = 500.00;
	}
	
	public CreditCard(int idCard, Account account, Client titular, LocalDate creation, LocalDate expire,
			double plafond) {
		super(account, titular);
		this.idCard = idCard;
		this.account = account;
		this.titular = titular;
		this.creation = creation;
		this.expire = expire;
		this.plafond = plafond;
	}

	@Override
	public String toString() {
		Locale.setDefault(Locale.US);
		return "CreditCard Id: " + idCard 
				+ "    Account Id: " + account.getId_account()				
				+ "    Made: " + getFormattedCreation()
				+ "  Expire: " + getFormattedExpire()
				+ "    Owner Id: " + titular.getId_client()
				+ "  " + titular.getName()
				+ "    Planfond: " + String.format("%.2f", plafond);
	}

	
	public double getPlafond() { return plafond; }

	public void setPlafond(double plafond) { this.plafond = plafond; }

	public String getType() { return type; }

	
	@Override
	public int hashCode() { return Objects.hash(idCard); }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		CreditCard other = (CreditCard) obj;
		return idCard == other.idCard;
	}
}
