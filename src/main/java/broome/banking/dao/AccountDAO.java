package broome.banking.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import broome.banking.model.Account;

@Component
@Transactional
public class AccountDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Account getByAccountNumber(String accountNumber){
		return (Account) entityManager
				.createQuery("from Account where accountNumber = :accountNumber")
				.setParameter("accountNumber", accountNumber).getSingleResult();
	}
}
