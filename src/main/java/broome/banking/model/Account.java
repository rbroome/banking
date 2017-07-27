package broome.banking.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int account_id;
	private BigDecimal balance;
	private String accountNumber;
	
	public Account(){}
	public Account(BigDecimal balance, String accountNumber) {
		super();
		this.balance = balance;
		this.accountNumber = accountNumber;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Id
	@GeneratedValue
	@Column(name = "ACCOUNT_ID")
	public int getId() {
		return account_id;
	}

	public void setId(int id) {
		this.account_id = id;
	}
	
	
	

}
