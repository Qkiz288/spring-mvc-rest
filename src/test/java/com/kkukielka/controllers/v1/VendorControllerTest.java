package com.kkukielka.controllers.v1;

import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.services.VendorService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    @Mock
    private VendorService vendorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();
    }

    @Test
    public void getAllVendors() throws Exception {
        // given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("Test1");
        vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName("Test2");
        vendorDTO2.setVendorUrl(VendorController.BASE_URL + "/2");

        List<VendorDTO> returnDTOs = Arrays.asList(vendorDTO1, vendorDTO2);

        Mockito.when(vendorService.getAllVendors()).thenReturn(returnDTOs);

        // when - then
        mockMvc.perform(get(VendorController.BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }
}