package pt.rumos.bank.dao;

import java.time.LocalDate;
import java.util.List;
import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Client;
import pt.rumos.bank.model.CreditCard;
import pt.rumos.bank.model.DebitCard;
import pt.rumos.bank.model.Movement;

public interface AccountDao {

	Account insert(Account account);
	void insertAccountClients(Account account, Client client);
	Boolean accountDrawOrDeposit(Account account, double amount, String type);
	Boolean accountTransfer(Account accountFrom, Account accountFor, double amount);
//	Boolean accountDraw(Account account, double amount);
//	Boolean accountDeposit(Account account, double amount);
	void deleteById(int id);
	void deleteAccount_client(int id_account, int id_client);
	Account findById(Integer id_account);
	Account findByTitularNif(String nif);
	Client findTitularByAccountId(int id_account);
	List<Account> findAll();
	List<Client> findAccountClients(int id_account);
	List<CreditCard> findAccountCreditCards(int id_account);
	List<DebitCard> findAccountDebitCards(int id_account);
	List<Movement> findMovementOfDay(int id_account, LocalDate date);
	List<Movement> consultAccountMovement(int id_account);
}
