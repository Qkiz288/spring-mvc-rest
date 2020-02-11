package com.kkukielka.controllers.v1;

import com.kkukielka.api.v1.model.CustomerDTO;
import com.kkukielka.controllers.RestResponseEntityExceptionHandler;
import com.kkukielka.services.CustomerService;
import com.kkukielka.services.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class  CustomerControllerTest extends AbstractRestControllerTest {

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

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
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

        when(customerService.getAllCustomers()).thenReturn(customers);

        // when - then
        mockMvc.perform(get(CustomerController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
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
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);

        // when - then
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(MIKE)))
                .andExpect(jsonPath("$.lastname", equalTo(JOHNSON)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testCustomerNotFound() throws Exception {
        // given
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        // when - then
        mockMvc.perform(get(CustomerController.BASE_URL + "/1"))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateNewCustomer() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Mikey");
        customerDTO.setLastname("Mouse");

        CustomerDTO savedDTO = new CustomerDTO();
        savedDTO.setFirstname(customerDTO.getFirstname());
        savedDTO.setLastname(customerDTO.getLastname());
        savedDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customerDTO)).thenReturn(savedDTO);

        // when - then
        mockMvc.perform(post(CustomerController.BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Mikey")))
                .andExpect(jsonPath("$.lastname", equalTo("Mouse")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Mikey");
        customerDTO.setLastname("Mouse");

        CustomerDTO updatedDTO = new CustomerDTO();
        updatedDTO.setFirstname(customerDTO.getFirstname());
        updatedDTO.setLastname(customerDTO.getLastname());
        updatedDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(updatedDTO);

        // when - then
        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Mikey")))
                .andExpect(jsonPath("$.lastname", equalTo("Mouse")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");

        CustomerDTO patchDTO = new CustomerDTO();
        patchDTO.setFirstname(customerDTO.getFirstname());
        patchDTO.setLastname("Mercury");
        patchDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(patchDTO);

        // when - then
        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(customerDTO.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(patchDTO.getLastname())))
                .andExpect(jsonPath("$.customer_url", equalTo(patchDTO.getCustomerUrl())));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        // given

        // when - then
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1"))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }

}