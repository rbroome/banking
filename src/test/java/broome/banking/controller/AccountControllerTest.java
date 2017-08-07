package broome.banking.controller;

import java.util.Arrays;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import broome.banking.Application;
import broome.banking.model.Customer;
import broome.banking.model.Transaction;
import broome.banking.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {
	@LocalServerPort
	private int port;
	
	@Autowired
	CustomerService service;
	
	@Before
	public void setUp(){
		service.registerCustomer(createCustomer("test"));
	}
	@Test
	public void thatItIsPossibleToDepositMoney() throws JSONException {
		String url = "http://localhost:" + port
				+ "/customers/1/accounts/1";
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpEntity<Transaction> entity = new HttpEntity<Transaction>(createTransaction("12345",1,"500"),createHeaders());
		restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);
		
		
		String expected = "{\"balance\":1000.00,\"accountNumber\":\"12345\",\"id\":1}";
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	public void thatItIsPossibleToWithDrawMoney() throws JSONException {
		service.registerCustomer(createCustomer("test"));
		String url = "http://localhost:" + port
				+ "/customers/2/accounts/1";
		TestRestTemplate restTemplate = new TestRestTemplate();
		//Add 500 to the account first.
		HttpEntity<Transaction> entity = new HttpEntity<Transaction>(createTransaction("12345",2,"500"),createHeaders());
		restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);
		entity = new HttpEntity<Transaction>(createTransaction("12345",2,"-250"),createHeaders());
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);
		
		
		String expected = "{\"balance\":250.00,\"accountNumber\":\"12345\",\"id\":2}";
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void thatItIsPossibleToViewAccountStatus() throws JSONException{
		String url = "http://localhost:" + port
				+ "/customers/1/accounts/12345";
		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpEntity<String> entity = new HttpEntity<String>(null, createHeaders());
		
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.GET, entity, String.class);
		
		String expected = "{\"balance\":1000.00,\"accountNumber\":\"12345\",\"id\":1}";
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	


	
	
	private Customer createCustomer(String name){
		Customer customer = new Customer();
		
		customer.setEmail("test@test.com");
		customer.setFirstname(name);
		customer.setLastname("test");
		customer.setPassword("secret");
		
		return customer;
	}
	
	private Transaction createTransaction(String accId,int customerId,String amount){
		Transaction tran = new Transaction(accId, customerId, amount);
		return tran;
	}
	private HttpHeaders createHeaders(){
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
}
