package broome.banking.repo;

import org.springframework.data.repository.CrudRepository;

import broome.banking.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
