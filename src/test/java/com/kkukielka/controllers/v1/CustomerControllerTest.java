package com.kkukielka.controllers.v1;

import com.kkukielka.api.v1.model.CustomerDTO;
import com.kkukielka.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    public static final String MIKE = "Mike";
    public static final String JOHNSON = "Johnson";
    public static final String HTTP_TEST_COM = "http://test.com";

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListCustomers() throws Exception {
        // given
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("Mike");
        customerDTO1.setLastname("Johnson");
        customerDTO1.setCustomerUrl("http://test.com");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname("John");
        customerDTO2.setLastname("Mikeson");
        customerDTO2.setCustomerUrl("http://test.pl");

        List<CustomerDTO> customers = Arrays.asList(customerDTO1, customerDTO2);

        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);

        // when - then
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

    }

    @Test
    public void testGetCustomerById() throws Exception {
        // given
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname(MIKE);
        customerDTO1.setLastname(JOHNSON);
        customerDTO1.setCustomerUrl("api/v1/customers/1");

        Mockito.when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);

        // when - then
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(MIKE)))
                .andExpect(jsonPath("$.lastname", equalTo(JOHNSON)))
                .andExpect(jsonPath("$.customerUrl", equalTo("api/v1/customers/1")));
    }

}