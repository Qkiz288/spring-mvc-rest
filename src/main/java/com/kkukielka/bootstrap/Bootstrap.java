package com.kkukielka.bootstrap;

import com.kkukielka.domain.Category;
import com.kkukielka.domain.Customer;
import com.kkukielka.repositories.CategoryRepository;
import com.kkukielka.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        initializeCategories();
        initializeCustomers();
    }

    private void initializeCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.info("Saved Categories count: " + categoryRepository.findAll().size());
    }

    private void initializeCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstname("Mike");
        customer1.setLastname("Johnson");

        Customer customer2 = new Customer();
        customer2.setFirstname("John");
        customer2.setLastname("Wylde");

        Customer customer3 = new Customer();
        customer3.setFirstname("Barbara");
        customer3.setLastname("Strauss");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        log.info("Saved Customers count: " + customerRepository.findAll().size());
    }

}
