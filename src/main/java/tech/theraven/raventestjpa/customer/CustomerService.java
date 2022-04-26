package tech.theraven.raventestjpa.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Customer addCustomer(Customer customer) {
        customer.setCreated(LocalDateTime.now());
        customer.setActive(true);
        customer.setUpdated(LocalDateTime.now());
        return repo.save(customer);
    }

    public Customer getCustomer(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public void deleteCustomerById(Long id) {
        Customer deletedCustomer = getCustomer(id);
        deletedCustomer.setActive(false);
        repo.save(deletedCustomer);

    }

    public Customer updateCustomer(Customer customer) {
        Customer updatedCustomer = getCustomer(customer.getId());
        updatedCustomer.setUpdated(LocalDateTime.now());
        updatedCustomer.setFullName(customer.getFullName());
        updatedCustomer.setPhone(customer.getPhone());
        return repo.save(updatedCustomer);

    }
}
