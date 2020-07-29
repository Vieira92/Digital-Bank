package pt.rumos.bank.dao;

import pt.rumos.bank.dao.impl.AccountDaoJDBC;
import pt.rumos.bank.dao.impl.CardsDaoJDBC;
import pt.rumos.bank.dao.impl.ClientDaoJDBC;
import pt.rumos.bank.db.DB;

public class DaoFactory {
	
	public static ClientDao createClientDao() {
		return new ClientDaoJDBC(DB.getConnection());
	}
	
	public static AccountDao createAccountDao() {
		return new AccountDaoJDBC(DB.getConnection());
	}
	
	public static CardsDao createCardsDao() {
		return new CardsDaoJDBC(DB.getConnection());
	}
}
