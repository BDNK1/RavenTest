package tech.theraven.repository;


import org.springframework.stereotype.Repository;
import tech.theraven.entity.Customer;
import tech.theraven.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDAO {

    public Iterable<Customer> findAll() {
        String query = """
                SELECT * FROM customer;
                """;
        List<Customer> customersList = new ArrayList<>();

        try (var con = ConnectionManager.open();
             var stmt = con.createStatement()) {
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                Customer customer = new Customer();
                customer.setFullName(res.getString("full_name"));
                customer.setCreated(res.getTimestamp("created").toLocalDateTime());
                customer.setUpdated(res.getTimestamp("updated").toLocalDateTime());
                customer.setPhone(res.getString("phone"));
                customer.setEmail(res.getString("email"));
                customer.setActive(res.getBoolean("is_active"));
                customer.setId(res.getLong("id"));
                customersList.add(customer);
            }
            return customersList;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Optional<Customer> findById(Long id) {
        String query = """
                SELECT * FROM customer WHERE id = ?;
                """;

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet res = stmt.executeQuery();

            Customer customer = null;

            if (res.next()) {
                customer = Customer.builder()
                        .fullName(res.getString("full_name"))
                        .created(res.getTimestamp("created").toLocalDateTime())
                        .updated(res.getTimestamp("updated").toLocalDateTime())
                        .phone(res.getString("phone"))
                        .email(res.getString("email"))
                        .isActive(res.getBoolean("is_active"))
                        .id(res.getLong("id")).build();
            }
            return Optional.of(customer);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Customer save(Customer customer) {
        String insertQuery = """
                INSERT 
                INTO customer (created, email, full_name, is_active, phone, updated) 
                VALUES(?,?,?,?,?,?);
                """;
        String updateQuery = """
                UPDATE customer
                SET created = ?, email = ?, full_name = ?, is_active = ?, phone = ?,updated = ?
                WHERE id = ?;
                """;
        boolean present = customer.getId() != null;
        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(present ? updateQuery : insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, present ? Timestamp.valueOf(customer.getCreated()) : Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getFullName());
            stmt.setBoolean(4, customer.isActive());
            stmt.setString(5, customer.getPhone());
            stmt.setTimestamp(6, present ? Timestamp.valueOf(customer.getUpdated()) : Timestamp.valueOf(LocalDateTime.now()));
            if (present) {
                stmt.setLong(7, customer.getId());
            }
            stmt.executeUpdate();
            var res = stmt.getGeneratedKeys();
            if (res.next()) {
                customer.setId(res.getLong(1));
            }
            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
