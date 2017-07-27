package broome.banking.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import broome.banking.model.Account;
import broome.banking.model.Customer;

@Component
@Transactional
public class CustomerDAO {
	@PersistenceContext
	private EntityManager entityManager;

	public void create(Customer customer) {
		entityManager.merge(customer);
	}

	public List getAll() {
		return entityManager.createQuery("From Customer").getResultList();
	}

	public void delete(Customer customer) {
		if (entityManager.contains(customer))
			entityManager.remove(customer);
		else
			entityManager.remove(entityManager.merge(customer));
		return;
	}

	public Customer getByEmail(String email) {
		return (Customer) entityManager
				.createQuery("from Customer where email = :email")
				.setParameter("email", email).getSingleResult();
	}

	public Customer getById(int id) {
		return (Customer) entityManager.find(Customer.class, id);
	}

	public void update(Customer customer) {
		entityManager.merge(customer);
	}
	
	public Set<Account> getAccountsByCustomerId(int id){
		return entityManager.find(Customer.class, id).getAccounts();
	}

}
