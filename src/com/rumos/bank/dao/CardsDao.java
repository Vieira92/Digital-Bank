package com.rumos.bank.dao;

import java.util.List;

import com.rumos.bank.administration.models.Cards;
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
	CreditCard findCreditCardById(int id_card);
	DebitCard findDebitCardById(int id_card);
	List<DebitCard> findAllDebitCards();
	List<CreditCard> findAllCreditCards();
	List<Cards> findAccountCards(int id_account); 
	
}
