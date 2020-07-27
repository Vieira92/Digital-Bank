package com.rumos.bank.dao;

import com.rumos.bank.dao.impl.AccountDaoJDBC;
import com.rumos.bank.dao.impl.CardsDaoJDBC;
import com.rumos.bank.dao.impl.ClientDaoJDBC;
import com.rumos.bank.db.DB;

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
