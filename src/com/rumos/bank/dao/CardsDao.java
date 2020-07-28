package com.rumos.bank.dao;

import java.util.List;

import com.rumos.bank.administration.models.Card;
import com.rumos.bank.administration.models.CreditCard;
import com.rumos.bank.administration.models.DebitCard;

public interface CardsDao {

//	TODO: FAZER PARENTE CARDS E GENERALIZAR ISTO	
	DebitCard insert(DebitCard debitCard);
	CreditCard insert(CreditCard creditCard);
	void update(DebitCard debitCard);
	void update(CreditCard creditCard);
	void deleteById(int id_card);
	void deleteByAccount(int id_account);
	void deleteByClient(int id_client);
	Boolean verifyDebitCard(int id_client);
	Boolean verifyCreditCard(int id_client);
	Boolean verifyaccountCreditCards(int id_account);
//	<T extends Card> T findCardById(int id_card);
	CreditCard findCreditCardById(int id_card);
	DebitCard findDebitCardById(int id_card);
	List<Card> findAllTypeCards(String type);
	List<Card> findAccountCards(int id_account); 
	
}
