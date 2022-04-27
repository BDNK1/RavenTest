package tech.theraven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.theraven.repository.CustomerService;
import tech.theraven.entity.Customer;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/api/customers")
    public Customer postCustomer(@RequestBody Customer customer) {
        try {
            return service.addCustomer(customer);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Violating constraints of customer", e);
        }
    }

    @PutMapping("/api/customers/{id}")
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        try {
            return service.updateCustomer(customer);
        } catch (IllegalArgumentException e) {
            if (e.getCause() instanceof DataAccessException) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Violating constraints of customer", e);
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found", e);
            }
        }
    }


    @DeleteMapping("/api/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        try {
            service.deleteCustomerById(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer not found", e);
        }
    }

    @GetMapping("/api/customers/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        try {
            return service.getCustomer(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer not found", e);
        }
    }

    @GetMapping("/api/customers")
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }
}
