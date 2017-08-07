package broome.banking.service;

import java.util.Set;

import broome.banking.model.Account;
import broome.banking.model.Customer;
import broome.banking.model.Transaction;

public interface CustomerServiceI {
	
	public Customer registerCustomer(Customer customer);
	public Iterable<Customer> getAllCustomers();
	public Customer getCustomerById(int id);
	public Account getAccount(int customerId,String accountNumber);
	public Set<Account> getAccountsForCustomer(int customerId);
	public Account withdrawMoney(Transaction transaction);

}
