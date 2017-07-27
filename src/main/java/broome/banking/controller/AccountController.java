package broome.banking.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import broome.banking.model.Account;
import broome.banking.model.Transaction;
import broome.banking.service.CustomerService;

@RestController
public class AccountController {
	
	@Autowired
	private CustomerService service;
	
	/**
	 * Returns all accounts for a specific customer
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/customers/{customerId}/accounts")
	public Set<Account> showAccountsForCustomer(@PathVariable int customerId){
		return service.getAccountsForCustomer(customerId);
	}
	
	/**
	 * 
	 * Returns a specific account for a specific Custoemr
	 * @param customerId
	 * @param accountnumber
	 * @return
	 */
	
    @GetMapping(path = "/customers/{customerId}/accounts/{accountnumber}")
    public Account getAccount(@PathVariable int customerId,
            @PathVariable String accountnumber) {
        return service.getAccount(customerId, accountnumber);
    }
    
    /**
     * Creates either a withdrawal or deposit transactions.
     * 
     * 
     * @param transaction
     * @param customerId
     * @param principal
     * @return
     */
    @RequestMapping(value = "/customers/{customerId}/accounts/{accountnumber}", method = RequestMethod.POST, 
            consumes = "application/json", produces = "application/json")
    public Account updateAccountBalance(@RequestBody Transaction transaction, @PathVariable int customerId, Principal principal){
    	if(transaction.getCustomer_id() ==0)
    		transaction.setCustomer_id(customerId);
    	return service.withdrawMoney(transaction);
    	
    }
}
