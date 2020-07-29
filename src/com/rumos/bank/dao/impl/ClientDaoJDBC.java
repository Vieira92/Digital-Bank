package com.rumos.bank.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rumos.bank.dao.ClientDao;
import com.rumos.bank.db.DB;
import com.rumos.bank.db.DbException;
import com.rumos.bank.model.Account;
import com.rumos.bank.model.Client;

public class ClientDaoJDBC implements ClientDao {

	private Connection conn;

	public ClientDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Client insert(Client client) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO client " + "(name, nif, birth, email, cellphone, telephone, occupation, created) " + "VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, client.getName());
			st.setString(2, client.getNif());
			st.setDate(3, Date.valueOf(client.getBirth()));
			st.setString(4, client.getEmail());
			st.setString(5, client.getCellphone());
			st.setString(6, client.getTelephone());
			st.setString(7, client.getOccupation());
			st.setDate(8, Date.valueOf(client.getCreation()));

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					Client newClient = new Client(rs.getInt(1), 
							client.getName(), 
							client.getBirth(), 
							client.getNif(), 
							client.getEmail(), 
							client.getCellphone(), 
							client.getTelephone(), 
							client.getOccupation(), 
							client.getCreation());
					return newClient;
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
	public void update(Client client) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE client "
					+ "SET name = ?, email = ?, cellphone = ?, telephone = ?, occupation = ? " 
					+ "WHERE id_client = ?");

			st.setString(1, client.getName());
			st.setString(2, client.getEmail());
			st.setString(3, client.getCellphone());
			st.setString(4, client.getTelephone());
			st.setString(5, client.getOccupation());
			st.setInt(6, client.getId_client());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteByNif(String nif) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM client WHERE nif = ?");

			st.setString(1, nif);

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
	public Client findByNif(String nif) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT * FROM client WHERE client.nif = ?");

			st.setString(1, nif);
			rs = st.executeQuery();
			
			if (rs.next()) {
				 
				Client client = new Client(rs.getInt("id_client"), 
						rs.getString("name"), 
						rs.getDate("birth").toLocalDate(), 
						rs.getString("nif"), 
						rs.getString("email"), 
						rs.getString("cellphone"), 
						rs.getString("telephone"), 
						rs.getString("occupation"), 
						rs.getDate("created").toLocalDate());
		
				return client;
			}
			
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
//			TODO: DB.closeConnection();  ver porque tem que fechar em algum lado 
//			ainda nao sei onde
		}
	}

	@Override
	public List<Client> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM client ORDER BY id_client");

			rs = st.executeQuery();

			List<Client> list = new ArrayList<>();

			while (rs.next()) {

				Client client = new Client(rs.getInt("id_client"), rs.getString("name"), rs.getDate("birth").toLocalDate(), rs.getString("nif"),
						rs.getString("email"), rs.getString("cellphone"), rs.getString("telephone"),
						rs.getString("occupation"), rs.getDate("created").toLocalDate());

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
	public Boolean verifyTitular(Client client) {

			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				st = conn.prepareStatement(
						"SELECT * FROM account WHERE account.id_titular = ?");

				st.setInt(1, client.getId_client());
				rs = st.executeQuery();
								
				if (rs.next()) {				
					return true;
				}
				
				return false;
				
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
			}
	}

	@Override
	public List<Account> findClientAccounts(int id_client) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT AC.id_account " 
					+ "FROM account_clients AC "
					+ "WHERE AC.id_client = ?");
					

			st.setInt(1, id_client);
			rs = st.executeQuery();
			
			List<Account> list = new ArrayList<>();
			
			while (rs.next()) {
				Account account = new Account(rs.getInt("id_account"));
				list.add(account);
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
