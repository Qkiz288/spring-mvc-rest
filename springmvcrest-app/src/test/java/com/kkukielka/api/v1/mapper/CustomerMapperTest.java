package com.kkukielka.api.v1.mapper;

import com.kkukielka.domain.Customer;
import com.kkukielka.model.CustomerDTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("Mike");
        customer.setLastname("Brandon");

        // when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // then
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }
}