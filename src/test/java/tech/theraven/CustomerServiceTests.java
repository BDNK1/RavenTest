package tech.theraven;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import tech.theraven.entity.Customer;
import tech.theraven.repository.CustomerService;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceTests {

    @Autowired
    private CustomerService customerService;


    @Test
    @Order(1)
    @Rollback(false)
    void saveEmployeeTest() {

        Customer customer = Customer.builder()
                .fullName("Volodymyr Budniak")
                .phone("+380972041491")
                .email("budnyak2002@gmail.com")
                .build();

        customerService.addCustomer(customer);

        Assertions.assertThat(customer.getId()).isPositive();
    }


    @Test
    @Order(2)
    @Rollback(false)
    void getCustomerTest() {

        Customer customer = customerService.getCustomer(1L);

        Assertions.assertThat(customer.getId()).isEqualTo(1L);
    }


    @Test
    @Order(3)
    @Rollback(false)
    void getListOfEmployeesTest() {

        List<Customer> customers = customerService.getAllCustomers();

        Assertions.assertThat(customers.size()).isPositive();

    }


    @Test
    @Order(4)
    @Rollback(value = false)
    void updateCustomerTest() {

        Customer customer = customerService.getCustomer(1L);

        customer.setPhone("+380974957377");

        Customer customerUpdated = customerService.updateCustomer(customer);

        Assertions.assertThat(customerUpdated.getPhone()).isEqualTo("+380974957377");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    void deleteCustomerTest() {


        customerService.deleteCustomerById(1L);

        Customer customer = customerService.getCustomer(1L);

        Assertions.assertThat(customer.isActive()).isFalse();

    }
}
