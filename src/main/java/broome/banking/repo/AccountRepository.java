package broome.banking.repo;

import org.springframework.data.repository.CrudRepository;

import broome.banking.model.Account;

public interface AccountRepository extends CrudRepository<Account, Integer>{

}
