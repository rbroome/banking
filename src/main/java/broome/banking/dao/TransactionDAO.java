package broome.banking.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import broome.banking.model.Transaction;

@Component
@Transactional
public class TransactionDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(Transaction transaction){
		entityManager.persist(transaction);
	}
	public void delete(Transaction transaction){
		if(entityManager.contains(transaction))
			entityManager.remove(transaction);
		else
			entityManager.remove(entityManager.merge(transaction));
		return;
	}
	
	public Transaction getById(int id){
		return (Transaction) entityManager.find(Transaction.class, id);
	}
	public void update(Transaction transaction){
		entityManager.merge(transaction);
	}
	public List getAll(){
		return entityManager.createQuery("From Transaction").getResultList();
	}

}
