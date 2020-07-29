package pt.rumos.bank.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pt.rumos.bank.dao.AccountDao;
import pt.rumos.bank.db.DB;
import pt.rumos.bank.db.DbException;
import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Client;
import pt.rumos.bank.model.CreditCard;
import pt.rumos.bank.model.DebitCard;

public class AccountDaoJDBC implements AccountDao {

	private Connection conn;

	public AccountDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Account insert(Account account) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO account " + "(id_titular, balance, create_acc) " + "VALUES " + "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, account.getMainTitular().getId_client());
			st.setDouble(2, account.getBalance());
			st.setDate(3, Date.valueOf(account.getCreation()));

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Account newAccount = new Account(rs.getInt(1), account.getMainTitular(), account.getBalance(),
							account.getCreation());
					return newAccount;
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		return null;
	}
	
	@Override
	public void insertAccountClients(Account account, Client client) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO account_clients " + "(id_account, id_client) " + "VALUES " + "(?, ?)");

			st.setInt(1, account.getId_account());
			st.setInt(2, client.getId_client());

			int rowsAffected = st.executeUpdate();

			System.out.println("Done! Rows affected: " + rowsAffected);

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Boolean accountDrawOrDeposit(Account account, double amount, String type) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement("UPDATE account "
					+ "SET balance = ? " 
					+ "WHERE id_account = ?");

			st.setDouble(1, account.getBalance());
			st.setInt(2, account.getId_account());

			int rows = st.executeUpdate();
			
//			TODO: falta saber como converter para dateTime no sql por agora é string
			LocalDateTime ldt = LocalDateTime.now();
			String date = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			st2 = conn.prepareStatement("INSERT INTO account_movement "
					+ "(id_account, date, amount, balance) "
					+ "VALUES (?, ?, ?, ?)");
			
			double x;
			if (type == "Draw") { x = - amount;} 
			else if (type == "Deposit") { x = amount; }
			else { return false; }
			
			st2.setInt(1, account.getId_account());
			st2.setString(2, date);
			st2.setDouble(3, x);
			st2.setDouble(4, account.getBalance());
			
			int rows1 = st2.executeUpdate();
			
			conn.commit();
			if (rows == 0 || rows1 == 0 ) {
				return false;
			} else { return true; }

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	
	@Override
	public Boolean accountTransfer(Account accountFrom, Account accountFor, double amount) {
	try{
		conn.setAutoCommit(false);
		
		if(accountDrawOrDeposit(accountFrom, amount, "Draw")) {
			if (accountDrawOrDeposit(accountFor, amount, "Deposit")) {
				conn.commit();
				return true;
			}
		}
		conn.commit();
		return false;
	} catch (SQLException e) {
		throw new DbException(e.getMessage());
	} 
}
//	TODO: perguntar ao prof qual é melhor fazer de novo ou como tenho em cima!! 
//	Acho que em cima sao mais pessoas a aceder ao mesmo metodo e pode dar conflito nao sei
	
//	@Override
//	public Boolean accountTransfer(Account accountFrom, Account accountFor, double amount) {
//		PreparedStatement st = null;
//		PreparedStatement st1 = null;
//		PreparedStatement st2 = null;
//		PreparedStatement st3 = null;
//		try {
//			conn.setAutoCommit(false);
//			
//			LocalDateTime ldt = LocalDateTime.now();
//			String date = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//			
//			st = conn.prepareStatement("UPDATE account "
//					+ "SET balance = ? " 
//					+ "WHERE id_account = ?");
//
//			st.setDouble(1, accountFrom.getBalance());
//			st.setInt(2, accountFrom.getId_account());
//			int rows = st.executeUpdate();
//			
//			st1 = conn.prepareStatement("INSERT INTO account_movement "
//					+ "(id_account, date, amount, balance) "
//					+ "VALUES (?, ?, ?, ?)");
//			
//			double x = - amount;
//			st1.setInt(1, accountFrom.getId_account());
//			st1.setString(2, date);
//			st1.setDouble(3, x);
//			st1.setDouble(4, accountFrom.getBalance());
//			int rows1 = st1.executeUpdate();
//			
//			
//			
//			st2 = conn.prepareStatement("UPDATE account "
//					+ "SET balance = ? " 
//					+ "WHERE id_account = ?");
//
//			st2.setDouble(1, accountFor.getBalance());
//			st2.setInt(2, accountFor.getId_account());
//			int rows2 = st2.executeUpdate();
//			
//			st3 = conn.prepareStatement("INSERT INTO account_movement "
//					+ "(id_account, date, amount, balance) "
//					+ "VALUES (?, ?, ?, ?)");
//						
//			st3.setInt(1, accountFor.getId_account());
//			st3.setString(2, date);
//			st3.setDouble(3, amount);
//			st3.setDouble(4, accountFor.getBalance());
//			int rows3 = st3.executeUpdate();
//			
//			conn.commit();
//			if (rows == 0 || rows1 == 0 || rows2 == 0 || rows3 == 0) {
//				return false;
//			} else { return true; }
//
//		} catch (SQLException e) {
//			throw new DbException(e.getMessage());
//		} finally {
//			DB.closeStatement(st);
//		}
//	}
	
	@Override
	public void deleteById(int id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM account " + "WHERE id_account = ?");

			st.setInt(1, id);

			int rows = st.executeUpdate();

			if (rows == 0) {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteAccount_client(int id_account, int id_client) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM account_clients " + "WHERE id_account = ? AND id_client = ?");

			st.setInt(1, id_account);
			st.setInt(2, id_client);

			int rows = st.executeUpdate();

			if (rows == 0) {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Account findByid(Integer id_account) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT A.*, C.* " + "FROM account A " + "INNER JOIN client C "
					+ "ON A.id_titular = C.id_client " + "WHERE A.id_account = ?");

			st.setInt(1, id_account);
			rs = st.executeQuery();

			if (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"),
						rs.getDate("birth").toLocalDate(), rs.getString("nif"), rs.getString("email"),
						rs.getString("cellphone"), rs.getString("telephone"), rs.getString("occupation"),
						rs.getDate("created").toLocalDate());

				Account account = new Account(rs.getInt("id_account"), client, rs.getDouble("balance"),
						rs.getDate("create_acc").toLocalDate());

				return account;
			}

			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Account findByTitularNif(String nif) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client findTitularByAccountId(int id_account) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT A.id_account, A.id_titular, C.* " + "FROM account A "
					+ "INNER JOIN client C " + "ON A.id_titular = C.id_client " + "WHERE A.id_account = ?");

			st.setInt(1, id_account);
			rs = st.executeQuery();

			if (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"),
						rs.getDate("birth").toLocalDate(), rs.getString("nif"), rs.getString("email"),
						rs.getString("cellphone"), rs.getString("telephone"), rs.getString("occupation"),
						rs.getDate("created").toLocalDate());

				return client;
			}

			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Account> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT A.*, C.id_client, C.name " + "FROM account A " + "INNER JOIN client C "
					+ "ON A.id_titular = C.id_client ");

			rs = st.executeQuery();

			List<Account> list = new ArrayList<>();

			while (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"));

				Account account = new Account(rs.getInt("id_account"), client, rs.getDouble("balance"),
						rs.getDate("create_acc").toLocalDate());

				list.add(account);
			}
			if (list.isEmpty()) {
				return null;
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Client> findAccountClients(int id_account) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT C.* " + "FROM account_clients AC " + "INNER JOIN client C "
					+ "ON AC.id_client = C.id_client " + "WHERE AC.id_account = ?");

			st.setInt(1, id_account);
			rs = st.executeQuery();

			List<Client> list = new ArrayList<>();
			while (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"),
						rs.getDate("birth").toLocalDate(), rs.getString("nif"), rs.getString("email"),
						rs.getString("cellphone"), rs.getString("telephone"), rs.getString("occupation"),
						rs.getDate("created").toLocalDate());
				list.add(client);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<CreditCard> findAccountCreditCards(int id_account) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT C.*, CL.name " + "FROM cards C " + "INNER JOIN client CL "
					+ "ON C.id_client = CL.id_client " + "WHERE type = 'C' AND id_account = ?");

			st.setInt(1, id_account);
			rs = st.executeQuery();

			List<CreditCard> list = new ArrayList<>();
			while (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"));

				Account account = new Account(rs.getInt("id_account"));

				CreditCard creditCard = new CreditCard(rs.getInt("id_card"), account, client,
						rs.getDate("creation").toLocalDate(), rs.getDate("expire").toLocalDate(),
						rs.getDouble("plafond"));

				list.add(creditCard);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<DebitCard> findAccountDebitCards(int id_account) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT C.*, CL.name " + "FROM cards C " + "INNER JOIN client CL "
					+ "ON C.id_client = CL.id_client " + "WHERE type = 'D' AND id_account = ?");

			st.setInt(1, id_account);
			rs = st.executeQuery();

			List<DebitCard> list = new ArrayList<>();
			while (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"));

				Account account = new Account(rs.getInt("id_account"));

				DebitCard debitCard = new DebitCard(rs.getInt("id_card"), account, client,
						rs.getDate("creation").toLocalDate(), rs.getDate("expire").toLocalDate());

				list.add(debitCard);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Double> findMovementOfDay(int id_account, LocalDate date) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT AM.amount " 
					+ "FROM account_movement AM "  
					+ "WHERE id_account = ? AND date = ?");

			st.setInt(1, id_account);
			st.setDate(2, Date.valueOf(date));
			rs = st.executeQuery();

			List<Double> list = new ArrayList<>();
			while (rs.next()) {
				list.add(rs.getDouble("amount"));
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<String> consultAccountMovement(int id_account) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT AM.amount, AM.balance, AM.date " 
					+ "FROM account_movement AM "  
					+ "WHERE id_account = ? ORDER BY id_movement");

			st.setInt(1, id_account);
			rs = st.executeQuery();

			List<String> list = new ArrayList<>();
			while (rs.next() && list.size() < 10) {
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("date"));
				sb.append("     ");
				sb.append(String.format("%.2f", rs.getDouble("balance")));
				sb.append("     ");
				sb.append(String.format("%.2f", rs.getDouble("amount")));
				list.add(sb.toString());
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
