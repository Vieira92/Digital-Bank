package pt.rumos.bank.dao;

import java.util.List;

import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Client;

public interface ClientDao {

	Client insert(Client client, String pin);
	void update(Client client);
	void deleteByNif(String nif);
	Client findByNif(String nif);
	Boolean changePin(int id_client, String pin);
	Boolean verifyClientPin(int id_client, String pin);
	Boolean verifyTitular(Client client);
	List<Account> findClientAccounts(int id_client);
	List<Client> findAll();
}
