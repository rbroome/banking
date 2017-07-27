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
import broome.banking.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	CustomerService service;
	
	@Before
	public void setUp(){
		service.registerCustomer(createCustomer("test"));
	}
	
	@Test
	public void thatCustomersReturnsAllCustomers() throws JSONException {
		String url = "http://localhost:" + port
				+ "/customers/";

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.GET, entity, String.class);

		String expected = "[{\"customer_id\":1,\"firstname\":\"test\",\"lastname\":\"test\",\"email\":\"test@test.com\",\"password\":\"secret\",\"accounts\":[{\"balance\":0.00,\"accountNumber\":\"12345\",\"id\":1}]}]";
		
		

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	public void thatCustomerIdReturnOnlyThatCustomer() throws JSONException{
		String url = "http://localhost:" + port
				+ "/customers/1";

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpEntity<String> entity = new HttpEntity<String>(null, createHeaders());

		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.GET, entity, String.class);

		String expected = "{\"customer_id\":1,\"firstname\":\"test\",\"lastname\":\"test\",\"email\":\"test@test.com\",\"password\":\"secret\",\"accounts\":[{\"balance\":0.00,\"accountNumber\":\"12345\",\"id\":1}]}";
		
		

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	public void thatAUserCanSignUp() throws JSONException{
		String url = "http://localhost:" + port
				+ "/signup";
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpEntity<Customer> entity = new HttpEntity<Customer>(createCustomer("Robin"),createHeaders());
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);
		
		
		String expected = "{\"firstname\":\"Robin\",\"lastname\":\"test\",\"email\":\"test@test.com\",\"password\":\"secret\"}";
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
	private HttpHeaders createHeaders(){
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
}
