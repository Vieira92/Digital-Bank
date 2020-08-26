package pt.rumos.bank.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pt.rumos.bank.dao.ClientDao;
import pt.rumos.bank.db.DB;
import pt.rumos.bank.db.DbException;
import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Client;

public class ClientDaoJDBC implements ClientDao {

	private Connection conn;

	public ClientDaoJDBC(Connection conn) { this.conn = conn; }

	
	@Override
	public Client insert(Client client, String pin) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement( "INSERT INTO client " 
					+ "(name, nif, birth, email, cellphone, telephone, occupation, created) " 
					+ "VALUES "
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
			
			if (st.executeUpdate() > 0) {
				rs = st.getGeneratedKeys();
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
					
					if (insertPin(newClient, pin)) { return newClient; }
				}
				return null;	
			} else { throw new DbException("Unexpected error! No rows affected!"); }
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	private boolean insertPin(Client client, String pin) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO client_pass "
					+ "(id_client, pass) "
					+ "VALUES (?, ?)");
			
			st.setInt(1, client.getId_client());
			st.setString(2, pin);
			
			if(st.executeUpdate() > 0) { return true; } 
			else { return false; }
			
		} catch (SQLException e) { throw new DbException(e.getMessage()); 
		} finally {	DB.closeStatement(st); }
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

		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {	DB.closeStatement(st); }
	}

	@Override
	public void deleteByNif(String nif) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM client WHERE nif = ?");

			st.setString(1, nif);

			int rows = st.executeUpdate();

			if (rows == 0) { throw new DbException("Unexpected error! No rows affected!"); }

		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {	DB.closeStatement(st); }
	}

	@Override
	public Client findByNif(String nif) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM client WHERE client.nif = ?");

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
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public Boolean changePin(int id_client, String pin) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE client_pass "
					+ "SET pass = ? " 
					+ "WHERE id_client = ?");

			st.setString(1, pin);
			st.setInt(2, id_client);
			
			if(st.executeUpdate() > 0) { return true; }
			return false;
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {	DB.closeStatement(st); }
	}
	
	@Override
	public Boolean verifyClientPin(int id_client, String pin) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * " 
					+ "FROM client_pass "
					+ "WHERE id_client = ?");		

			st.setInt(1, id_client);
			rs = st.executeQuery();
			
			if (rs.next()) {
				String pass = rs.getString("pass");
				if (pass.equals(pin)) { return true; }
			} 
			return false;
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}			
	}
	
	@Override
	public Boolean verifyTitular(Client client) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT * FROM account WHERE account.id_titular = ?");

				st.setInt(1, client.getId_client());
				rs = st.executeQuery();
								
				if (rs.next()) { return true; }
				return false;
				
			} catch (SQLException e) { throw new DbException(e.getMessage());
			} finally {
				DB.closeResultSet(rs);
				DB.closeStatement(st);
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
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
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
				Client client = new Client(rs.getInt("id_client"), 
						rs.getString("name"), 
						rs.getDate("birth").toLocalDate(), 
						rs.getString("nif"),
						rs.getString("email"), 
						rs.getString("cellphone"), 
						rs.getString("telephone"),
						rs.getString("occupation"), 
						rs.getDate("created").toLocalDate());
				list.add(client);
			}
			return list;
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
//	TODO: DB.closeConnection();  ver porque tem que fechar em algum lado 
//	ainda nao sei onde se fechar aqui estraga tudo!
}
