package com.rumos.bank.dao;

import java.util.List;

import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;

public interface ClientDao {

	Client insert(Client client);
	void update(Client client);
	void deleteByNif(String nif);
	Client findByNif(String nif);
	Boolean verifyTitular(Client client);
	List<Account> findClientAccounts(int id_client);
	List<Client> findAll();
}
