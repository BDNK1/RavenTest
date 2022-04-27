package tech.theraven.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.theraven.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
