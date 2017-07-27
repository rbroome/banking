package broome.banking.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import broome.banking.dao.AccountDAO;
import broome.banking.dao.CustomerDAO;
import broome.banking.dao.TransactionDAO;
import broome.banking.model.Account;
import broome.banking.model.Customer;
import broome.banking.model.Transaction;

@Component
public class CustomerService {
	
	@Autowired 
	CustomerDAO customerDao;
	@Autowired
	AccountDAO accountDao;
	@Autowired
	TransactionDAO transactionDAO;
	
	
	/**
	 * Generates a new customer.
	 * 
	 * @param customer
	 * @return
	 */
	public Customer registerCustomer(Customer customer){

		Set<Account> accounts = new HashSet<Account>(0);
		accounts.add(new Account(new BigDecimal("0"),"12345"));
		customer.setAccounts(accounts); 
		customerDao.create(customer);

		return customer;
	}
	
	
	public List<Customer> getAllCustomers(){
		List<Customer> customers =customerDao.getAll();
		
		return customers;
	}
	public Customer getCustomerById(int id){
		return customerDao.getById(id);
	}
	
	public Account getAccount(int customerId,String accountNumber){
		Customer customer = customerDao.getById(customerId);
		if(customer!=null){
			for(Account account : customer.getAccounts())
			if(account.getAccountNumber().equals(accountNumber)){
				return account;
			}
		}
		return null;
	}
	
	public Set<Account> getAccountsForCustomer(int customerId){
		Customer customer = customerDao.getById(customerId);
		if(customer == null)
			return null;
		return customer.getAccounts();
	}
	
	public Account withdrawMoney(Transaction transaction){
		Account account = changeAccountBalance(transaction.getCustomer_id(), transaction.getAccountNumber(), transaction.getAmount());
		transactionDAO.create(transaction);
		return account;
	}
	
	private Account changeAccountBalance(int customer_id, String accountnumber,String amount){
		Customer customer = customerDao.getById(customer_id);
		Set<Account> accounts = customer.getAccounts();
		Account updatedAccount = null;
		for(Account account : accounts){
			if(account.getAccountNumber().equals(accountnumber)){
				BigDecimal newBalance = account.getBalance().add(new BigDecimal(amount));
				account.setBalance(newBalance);
				updatedAccount = account;
			}
		}
		customerDao.update(customer);
		return updatedAccount;
	}

}
