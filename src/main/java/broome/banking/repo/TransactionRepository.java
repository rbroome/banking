package broome.banking.repo;

import org.springframework.data.repository.CrudRepository;

import broome.banking.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, String>{

}
