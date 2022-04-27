package tech.theraven.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import tech.theraven.entity.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    @Autowired
    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customersList = new ArrayList<>();
        repo.findAll().forEach(customersList::add);
        return customersList;
    }

    public Customer addCustomer(Customer customer) throws IllegalArgumentException  {
        customer.setCreated(LocalDateTime.now());
        customer.setActive(true);
        customer.setUpdated(LocalDateTime.now());
        try {
            return repo.save(customer);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public Customer getCustomer(Long id) throws IllegalArgumentException {
        return repo.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteCustomerById(Long id) throws IllegalArgumentException {
        Customer deletedCustomer = getCustomer(id);
        deletedCustomer.setActive(false);
        repo.save(deletedCustomer);
    }

    public Customer updateCustomer(Customer customer) throws IllegalArgumentException {

        try{
            Customer updatedCustomer = getCustomer(customer.getId());
            updatedCustomer.setUpdated(LocalDateTime.now());
            updatedCustomer.setFullName(customer.getFullName());
            updatedCustomer.setPhone(customer.getPhone());
            return repo.save(updatedCustomer);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("violating constraints",e);
        }
        catch(IllegalArgumentException e ){
            e.printStackTrace();
            throw new IllegalArgumentException("Customer not found",e);
        }

    }
}
