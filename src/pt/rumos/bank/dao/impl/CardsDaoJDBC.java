package pt.rumos.bank.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pt.rumos.bank.dao.CardsDao;
import pt.rumos.bank.db.DB;
import pt.rumos.bank.db.DbException;
import pt.rumos.bank.model.Account;
import pt.rumos.bank.model.Card;
import pt.rumos.bank.model.Client;
import pt.rumos.bank.model.CreditCard;
import pt.rumos.bank.model.DebitCard;

public class CardsDaoJDBC implements CardsDao {

	private Connection conn;

	public CardsDaoJDBC(Connection conn) { this.conn = conn; }

	
	@Override
	public DebitCard insert(DebitCard debitCard, String pin) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO cards "
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
						
			
			if (st.executeUpdate() > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					DebitCard newDebitCard = new DebitCard(rs.getInt(1), 
							debitCard.getAccount(), 
							debitCard.getTitular(),
							debitCard.getCreation(), 
							debitCard.getExpire());
					
					if (insertPin(newDebitCard, pin)) { return newDebitCard; }
				}
				return null;	
			} else { throw new DbException("Unexpected error! No rows affected!"); }
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	@Override
	public CreditCard insert(CreditCard creditCard, String pin) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO cards "
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
						
			
			if (st.executeUpdate() > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					CreditCard newCreditCard = new CreditCard(rs.getInt(1), 
							creditCard.getAccount(),
							creditCard.getTitular(), 
							creditCard.getCreation(), 
							creditCard.getExpire(),
							creditCard.getPlafond());
					
					if (insertPin(newCreditCard, pin)) { return newCreditCard; }
				}
				return null;	
			} else { throw new DbException("Unexpected error! No rows affected!"); }
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	private boolean insertPin(Card card, String pin) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO card_pass "
					+ "(id_card, pass) "
					+ "VALUES (?, ?)");
			
			st.setInt(1, card.getIdCard());
			st.setString(2, pin);
			
			if(st.executeUpdate() > 0) { return true; } 
			else { return false; }
			
		} catch (SQLException e) { throw new DbException(e.getMessage()); 
		} finally {	DB.closeStatement(st); }
	}

	@Override
	public void update(Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(int id_card) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM cards WHERE id_card = ?");

			st.setInt(1, id_card);

			int rows = st.executeUpdate();

			if (rows == 0) { throw new DbException("Unexpected error! No rows affected!"); }

		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally { DB.closeStatement(st); }
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
	public Boolean changePin(int id_card, String pin) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE card_pass "
					+ "SET pass = ? " 
					+ "WHERE id_card = ?");

			st.setString(1, pin);
			st.setInt(2, id_card);
			
			if(st.executeUpdate() > 0) { return true; }
			return false;
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally { DB.closeStatement(st); }
	}

	@Override
	public Boolean verifyCardPin(int id_card, String pin) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * " 
					+ "FROM card_pass "
					+ "WHERE id_card = ?");
					

			st.setInt(1, id_card);
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
	public Boolean verifyClientCard(int id_client, String type) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM cards WHERE cards.id_client = ? AND cards.type = ?");

			st.setInt(1, id_client);
			st.setString(2, type);
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
	public Boolean verifyAccountCreditCards(int id_account) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			List<Integer> list = new ArrayList<>();
			st = conn.prepareStatement("SELECT * FROM cards " 
					+ "WHERE cards.id_account = ? AND cards.type = 'C'");

			st.setInt(1, id_account);
			rs = st.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("id_card"));
			}

			if (list.size() < 2) { return true; } 
			return false;

		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public Card findCardById(int id_card) {
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
				String type = rs.getString("type");
				Client client = new Client(rs.getInt("id_client"), rs.getString("name"));
				Account account = new Account(rs.getInt("id_account"));
				Card card;
				if(type == "D") {
					card = new DebitCard(id_card, 
							account, 
							client, 
							rs.getDate("creation").toLocalDate(), 
							rs.getDate("expire").toLocalDate());
				} else {
					card = new CreditCard(id_card, 
							account, 
							client, 
							rs.getDate("creation").toLocalDate(), 
							rs.getDate("expire").toLocalDate(), 
							rs.getDouble("plafond")); 
				}	
				return card;
			}
			return null;
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
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

		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
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

		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

//	TODO: @Override
//	public List<Card> findAccountCards(int id_account) {
//		// TODO Auto-generated method stub
//		return null;
//	}

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
				if (type == "C") {
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
			
		} catch (SQLException e) { throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
//	TODO: DB.closeConnection();  ver porque tem que fechar em algum lado 
//	ainda nao sei onde se fechar aqui estraga tudo!
}
