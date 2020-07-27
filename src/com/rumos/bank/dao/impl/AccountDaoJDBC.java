package com.rumos.bank.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rumos.bank.administration.models.Account;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;
import com.rumos.bank.dao.AccountDao;
import com.rumos.bank.db.DB;
import com.rumos.bank.db.DbException;

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
//						int id = rs.getInt(1);
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
}
