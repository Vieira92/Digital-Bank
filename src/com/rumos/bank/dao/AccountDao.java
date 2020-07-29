package com.rumos.bank.dao;

import java.util.List;

import com.rumos.bank.model.Account;
import com.rumos.bank.model.Client;
import com.rumos.bank.model.CreditCard;
import com.rumos.bank.model.DebitCard;

public interface AccountDao {

	Account insert(Account account);
	void insertAccountClients(Account account, Client client);
	void deleteById(int id);
	void deleteAccount_client(int id_account, int id_client);
	Account findByid(Integer id);
	Account findByTitularNif(String nif);
	Client findTitularByAccountId(int id_account);
	List<Account> findAll();
	List<CreditCard> findAccountCreditCards(int id_account);
	List<DebitCard> findAccountDebitCards(int id_account);
	List<Client> findAccountClients(int id_account);
	
}
