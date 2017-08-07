package broome.banking.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import broome.banking.model.Account;
import broome.banking.model.Customer;
import broome.banking.model.Transaction;
import broome.banking.repo.AccountRepository;
import broome.banking.repo.CustomerRepository;
import broome.banking.repo.TransactionRepository;


@Service
public class CustomerService implements CustomerServiceI{
	
	CustomerRepository customerRepository;
	AccountRepository accountRepository;
	TransactionRepository transactionRepository;
	
	@Autowired
	public CustomerService(CustomerRepository customerRepository,AccountRepository accountRepository,TransactionRepository transactionRepository) {
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}
	
	public Customer registerCustomer(Customer customer){
		//old code
		Set<Account> accounts = new HashSet<Account>(0);
		accounts.add(new Account(new BigDecimal("0"),"12345"));
		customer.setAccounts(accounts);
		//old
		
		if(!customerRepository.exists(customer.getCustomer_id()))
			return customerRepository.save(customer);
		return null;
	}
	
	public Iterable<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	public long total(){
		return customerRepository.count();
	}
	
	
	//ACCOUNT STUFF
	public Account createAccount(int customerId, String accountNumber){
		if(!customerRepository.exists(customerId)){
			throw new RuntimeException("Customer does not exist:" +customerId);
		}
		return accountRepository.save(new Account(new BigDecimal("0"),accountNumber));
	}
	public Iterable<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	public long getTotalAccounts(){
		return accountRepository.count();
	}

	@Override
	public Account getAccount(int customerId, String accountNumber) {
		Set<Account> accounts= getAccountsForCustomer(customerId);
		if(accounts != null){
		return accounts.stream().filter(account -> account.getAccountNumber().equals(accountNumber))
				.findFirst().orElse(null);
		}
		return null;
	}

	@Override
	public Set<Account> getAccountsForCustomer(int customerId) {
		Customer customer = customerRepository.findOne(customerId);
		if(customer!= null)
			return customer.getAccounts();
		return null;
	}
	
	
	//Transaction stuff
	
	public Account withdrawMoney(Transaction transaction){
		Account account = changeAccountBalance(transaction.getCustomer_id(), transaction.getAccountNumber(), transaction.getAmount());
		transactionRepository.save(transaction);
		return account;
	}
	
	private Account changeAccountBalance(int customer_id, String accountnumber,String amount){
		Customer customer = customerRepository.findOne(customer_id);
		Set<Account> accounts = customer.getAccounts();
		Account account = accounts.stream().filter(acc -> acc.getAccountNumber().equals(accountnumber))
				.findFirst().orElse(null);
		BigDecimal newBalance = account.getBalance().add(new BigDecimal(amount));
		account.setBalance(newBalance);
		
		customerRepository.save(customer);
		return account;
	}

	@Override
	public Customer getCustomerById(int id) {
		return customerRepository.findOne(id);
	}
	

}
