package tech.theraven.raventestjpa.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/api/customers")
    public Customer postCustomer(@RequestBody Customer customer){
        return service.addCustomer(customer);
    }
    @PutMapping("/api/customers/{id}")
    public void updateCustomer(@RequestBody Customer customer,@PathVariable Long id){
        service.updateCustomer(customer);

    }
    @DeleteMapping("/api/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        service.deleteCustomerById(id);
    }

    @GetMapping("/api/customers/{id}")
    public Customer getCustomer(@PathVariable Long id){
        return service.getCustomer(id);
    }

    @GetMapping("/api/customers")
    public List<Customer> getAllCustomers(){
        return service.getAllCustomers();
    }
}
