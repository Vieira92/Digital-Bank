package com.rumos.bank.dao;

import java.util.List;

import com.rumos.bank.model.Card;
import com.rumos.bank.model.CreditCard;
import com.rumos.bank.model.DebitCard;

public interface CardsDao {

//	TODO: FAZER PARENTE CARDS E GENERALIZAR ISTO
	DebitCard insert(DebitCard debitCard);
	CreditCard insert(CreditCard creditCard);
	void update(Card card);
	void deleteById(int id_card);
	void deleteByAccount(int id_account);
	void deleteByClient(int id_client);
	Boolean verifyClientCard(int id_client, String type);
	Boolean verifyaccountCreditCards(int id_account);
	CreditCard findCreditCardById(int id_card);
	DebitCard findDebitCardById(int id_card);
	List<Card> findAllTypeCards(String type);
	List<Card> findAccountCards(int id_account); 
	
}
