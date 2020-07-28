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
import com.rumos.bank.administration.models.Card;
import com.rumos.bank.administration.models.Client;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;
import com.rumos.bank.dao.CardsDao;
import com.rumos.bank.db.DB;
import com.rumos.bank.db.DbException;

public class CardsDaoJDBC implements CardsDao {
	
	private Connection conn;

	public CardsDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public DebitCard insert(DebitCard debitCard) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
						"INSERT INTO cards " 
						+ "(id_client, id_account, type, creation, expire, plafond) " 
						+ "VALUES "
						+ "(?, ?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, debitCard.getTitular().getId_client());
			st.setInt(2, debitCard.getAccount().getId_account());
			st.setString(3, debitCard.getType());
			st.setDate(4, Date.valueOf(debitCard.getCreation()));
			st.setDate(5, Date.valueOf(debitCard.getExpire()));
			st.setDouble(6, 0.0);
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					DebitCard newDebitCard = new DebitCard(rs.getInt(1), 
							debitCard.getAccount(), 
							debitCard.getTitular(), 
							debitCard.getCreation(),
							debitCard.getExpire());					
					return newDebitCard;
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
	public CreditCard insert(CreditCard creditCard) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
						"INSERT INTO cards " 
						+ "(id_client, id_account, type, creation, expire, plafond) " 
						+ "VALUES "
						+ "(?, ?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, creditCard.getTitular().getId_client());
			st.setInt(2, creditCard.getAccount().getId_account());
			st.setString(3, creditCard.getType());
			st.setDate(4, Date.valueOf(creditCard.getCreation()));
			st.setDate(5, Date.valueOf(creditCard.getExpire()));
			st.setDouble(6, creditCard.getPlafond());
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					CreditCard newCreditCard = new CreditCard(rs.getInt(1),
							creditCard.getAccount(),
							creditCard.getTitular(), 
							creditCard.getCreation(),
							creditCard.getExpire(),
							creditCard.getPlafond());
					return newCreditCard;
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
	public void update(DebitCard debitCard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CreditCard creditCard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id_card) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM cards WHERE id_card = ?");

			st.setInt(1, id_card);

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
	public void deleteByAccount(int id_account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByClient(int id_client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean verifyDebitCard(int id_client) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT * FROM cards WHERE cards.id_client = ? AND cards.type = 'D'");

			st.setInt(1, id_client);
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
	public Boolean verifyCreditCard(int id_client) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT * FROM cards WHERE cards.id_client = ? AND cards.type = 'C'");

			st.setInt(1, id_client);
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
	public Boolean verifyaccountCreditCards(int id_account) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			List<Integer> list = new ArrayList<>();
			st = conn.prepareStatement(
					"SELECT * FROM cards WHERE cards.id_account = ? AND cards.type = 'C'");

			st.setInt(1, id_account);
			rs = st.executeQuery();
							
			while (rs.next()) {
				list.add(rs.getInt("id_card"));
			}
			
			if (list.size() < 2) {
				return true;	
			} else {
				return false;	
			}
						
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	

	@Override
	public CreditCard findCreditCardById(int id_card) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT C.*, CL.name " 
					+ "FROM cards C "
					+ "INNER JOIN client CL "
					+ "ON C.id_client = CL.id_client "
					+ "WHERE C.id_card = ?");

			st.setInt(1, id_card);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"));
				Account account = new Account(rs.getInt("id_account"));
				
				CreditCard creditCard = new CreditCard(rs.getInt("id_card"), 
						account, 
						client, 
						rs.getDate("creation").toLocalDate(), 
						rs.getDate("expire").toLocalDate(), 
						rs.getDouble("plafond"));
		
				return creditCard;
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
	public DebitCard findDebitCardById(int id_card) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT C.*, CL.name " 
					+ "FROM cards C "
					+ "INNER JOIN client CL "
					+ "ON C.id_client = CL.id_client "
					+ "WHERE C.id_card = ?");

			st.setInt(1, id_card);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"));
				Account account = new Account(rs.getInt("id_account"));
				
				DebitCard debitCard = new DebitCard(rs.getInt("id_card"), 
						account, 
						client, 
						rs.getDate("creation").toLocalDate(), 
						rs.getDate("expire").toLocalDate());
		
				return debitCard;
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
	public List<Card> findAccountCards(int id_account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Card> findAllTypeCards(String type) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT C.*, CL.name " 
					+ "FROM cards C "
					+ "INNER JOIN client CL "
					+ "ON C.id_client = CL.id_client "
					+ "WHERE type = ?");

			st.setString(1, type);
			rs = st.executeQuery();

			List<Card> list = new ArrayList<>();
			while (rs.next()) {
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"));
				Account account = new Account(rs.getInt("id_account"));
				
				Card card;
				if(type == "C") {
					card = new CreditCard(rs.getInt("id_card"), 
							account, 
							client, 
							rs.getDate("creation").toLocalDate(), 
							rs.getDate("expire").toLocalDate(), 
							rs.getDouble("plafond")); 
				} else {
					card = new DebitCard(rs.getInt("id_card"), 
							account, 
							client, 
							rs.getDate("creation").toLocalDate(), 
							rs.getDate("expire").toLocalDate());
				}
				

				list.add(card);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

//	@Override
//	public <T extends Card> T findCardById(int id_card) {
//		PreparedStatement st = null;
//		ResultSet rs = null;
//
//		try {
//			st = conn.prepareStatement("SELECT C.*, CL.name " 
//					+ "FROM cards C "
//					+ "INNER JOIN client CL "
//					+ "ON C.id_client = CL.id_client "
//					+ "WHERE C.id_card = ?");
//
//			st.setInt(1, id_card);
//			rs = st.executeQuery();
//			
//			if (rs.next()) {
//				Client client = new Client(rs.getInt("id_client"), rs.getString("name"));
//				Account account = new Account(rs.getInt("id_account"));
//				
//				T card;
//				if(rs.getString("type") == "C") {
//				card = new CreditCard(rs.getInt("id_card"), 
//							account, 
//							client, 
//							rs.getDate("creation").toLocalDate(), 
//							rs.getDate("expire").toLocalDate(), 
//							rs.getDouble("plafond")); 
//				} else {
//				card = new DebitCard(rs.getInt("id_card"), 
//							account, 
//							client, 
//							rs.getDate("creation").toLocalDate(), 
//							rs.getDate("expire").toLocalDate());
//				}
//				return card;
//			}
//			
//			return null;
//			
//		} catch (SQLException e) {
//			throw new DbException(e.getMessage());
//		} finally {
//			DB.closeStatement(st);
//			DB.closeResultSet(rs);
//		}
//	}
}
