package com.kkukielka.services;

import com.kkukielka.api.v1.mapper.CustomerMapper;
import com.kkukielka.api.v1.model.CustomerDTO;
import com.kkukielka.domain.Customer;
import com.kkukielka.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void getAllCustomers() {
        // given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        // when
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();

        // then
        assertEquals(customers.size(), customerDTOList.size());
        verify(customerRepository, times(1)).findAll();

    }

    @Test
    public void getCustomerById() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        Optional<Customer> customerOptional = Optional.of(customer);

        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);

        // when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        // then
        assertNotNull(customerDTO);
        verify(customerRepository, times(1)).findById(anyLong());
    }
}