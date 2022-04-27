package tech.theraven;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.Rollback;
import tech.theraven.entity.Customer;
import tech.theraven.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = org.springframework.stereotype.Service.class))
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    @Order(1)
    @Rollback(false)
     void saveEmployeeTest(){

        Customer customer = Customer.builder()
                .fullName("Volodymyr Budniak")
                .phone("+380972041491")
                .email("budnyak2002@gmail.com")
                .build();

        customerRepository.save(customer);

        Assertions.assertThat(customer.getId()).isPositive();
    }


    @Test
    @Order(2)
    @Rollback(false)
     void getCustomerTest(){

        Customer customer = customerRepository.findById(1L).get();

        Assertions.assertThat(customer.getId()).isEqualTo(1L);
    }


    @Test
    @Order(3)
    @Rollback(false)
     void getListOfEmployeesTest(){

        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);

        Assertions.assertThat(customers.size()).isPositive();

    }


    @Test
    @Order(4)
    @Rollback(value = false)
     void updateCustomerTest(){

        Customer customer = customerRepository.findById(1L).get();

        customer.setEmail("budnyak2002@theraven.tech");

        Customer customerUpdated =  customerRepository.save(customer);

        Assertions.assertThat(customerUpdated.getEmail()).isEqualTo("budnyak2002@theraven.tech");

    }


}
