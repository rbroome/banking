package broome.banking.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="Customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customer_id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CUSTOMER_ACCOUNT", joinColumns = { @JoinColumn(name = "CUSTOMER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ACCOUNT_ID") })
	private Set<Account> accounts = new HashSet<Account>(0);
	
	
	public Customer(){
		
	}
	
	public Customer(String email, String firstname, String lastname, String password,Set<Account> accounts) {
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.accounts = accounts;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public Set<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
