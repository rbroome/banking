package broome.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import broome.banking.model.Customer;
import broome.banking.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	
	/**
	 * Returns all registered customers.
	 * @return
	 */
	@RequestMapping("/customers")
	public Iterable<Customer> showAllCustomers(){
		return service.getAllCustomers();
	}
	
	/**
	 * SignUp as a new customer
	 * 
	 * @param customer
	 * @return
	 */
    @RequestMapping(value = "/signup", method = RequestMethod.POST, 
            consumes = "application/json", produces = "application/json")
    public Customer signUp(@RequestBody Customer customer){
    	return service.registerCustomer(customer);
    }
    
    
    /**
     * Returns customer with CusomerID
     * @param customerId
     * @return
     */
	@RequestMapping("/customers/{customerId}")
	public Customer showSpecificCustomer(@PathVariable int customerId){
		return service.getCustomerById(customerId);
	}
    
    
	
}
