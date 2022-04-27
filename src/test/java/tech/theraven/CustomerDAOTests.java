package tech.theraven;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import tech.theraven.entity.Customer;
import tech.theraven.repository.CustomerDAO;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerDAOTests {

    @Autowired
    private CustomerDAO customerDAO;

    @Test
    @BeforeAll
    static void setUp(){

    }
    @Test
    @Order(1)
    @Rollback(false)
     void saveEmployeeTest(){

        Customer customer = Customer.builder()
                .fullName("Volodymyr Budniak")
                .phone("+380972041491")
                .email("budnyak2002@gmail.com")
                .build();

        customerDAO.save(customer);

        Assertions.assertThat(customer.getId()).isPositive();
    }


    @Test
    @Order(2)
    @Rollback(false)
     void getCustomerTest(){

        Customer customer = customerDAO.findById(1L).get();

        Assertions.assertThat(customer.getId()).isEqualTo(1L);
    }


    @Test
    @Order(3)
    @Rollback(false)
     void getListOfEmployeesTest(){

        List<Customer> customers = new ArrayList<>();
        customerDAO.findAll().forEach(customers::add);

        Assertions.assertThat(customers.size()).isPositive();

    }


    @Test
    @Order(4)
    @Rollback(value = false)
     void updateCustomerTest(){

        Customer customer = customerDAO.findById(1L).get();

        customer.setEmail("budnyak2002@theraven.tech");

        Customer customerUpdated =  customerDAO.save(customer);

        Assertions.assertThat(customerUpdated.getEmail()).isEqualTo("budnyak2002@theraven.tech");

    }


}
