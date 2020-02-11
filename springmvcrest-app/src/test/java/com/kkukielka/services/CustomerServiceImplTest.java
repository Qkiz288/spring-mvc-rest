package com.kkukielka.services;

import com.kkukielka.api.v1.mapper.CustomerMapper;
import com.kkukielka.api.v1.model.CustomerDTO;
import com.kkukielka.controllers.v1.CustomerController;
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

public class CustomerServiceImplTest {

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
        customer.setFirstname("John");
        customer.setLastname("Johnson");
        Optional<Customer> customerOptional = Optional.of(customer);

        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);

        // when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        // then
        assertNotNull(customerDTO);
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
        assertEquals(CustomerController.BASE_URL + "/1", customerDTO.getCustomerUrl());
        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createNewCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("John");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        // then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals(CustomerController.BASE_URL + "/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void testUpdateCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("John");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setFirstname(customerDTO.getFirstname());
        updatedCustomer.setLastname(customerDTO.getLastname());

        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        // when
        CustomerDTO updatedDTO = customerService.updateCustomer(1L, customerDTO);

        // then
        assertEquals(customerDTO.getFirstname(), updatedDTO.getFirstname());
        assertEquals(CustomerController.BASE_URL + "/1", updatedDTO.getCustomerUrl());
    }

    @Test
    public void testDeleteCustomer() {
        // given
        Long id = 1L;

        doNothing().when(customerRepository).deleteById(anyLong());

        // when
        customerService.deleteCustomerById(id);

        // then
        verify(customerRepository, times(1)).deleteById(anyLong());
    }

}