package com.kkukielka.controllers.v1;

import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.controllers.RestResponseEntityExceptionHandler;
import com.kkukielka.services.VendorService;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    @Mock
    private VendorService vendorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
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

        when(vendorService.getAllVendors()).thenReturn(returnDTOs);

        // when - then
        mockMvc.perform(get(VendorController.BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        // when - then
        mockMvc.perform(get(VendorController.BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Test")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));

    }

    @Test
    public void vendorNotFound() throws Exception {
        // given
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        // when - then
        mockMvc.perform(get(VendorController.BASE_URL + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createNewVendor() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");

        VendorDTO savedVendorDTO = new VendorDTO();
        savedVendorDTO.setName("Test");
        savedVendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        when(vendorService.createNewVendor(vendorDTO)).thenReturn(savedVendorDTO);

        // when - then
        mockMvc.perform(post(VendorController.BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Test")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void updateVendor() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Updated");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendorDTO.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        when(vendorService.updateVendor(1L, vendorDTO)).thenReturn(returnDTO);

        // when - then
        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(returnDTO.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(returnDTO.getVendorUrl())));

    }

    @Test
    public void patchVendor() throws Exception {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Patch");

        VendorDTO patchVendor = new VendorDTO();
        patchVendor.setName(vendorDTO.getName());
        patchVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(patchVendor);

        // when - then
        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(vendorDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", equalTo(patchVendor.getName())))
                        .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));

    }

    @Test
    public void deleteVendorById() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1"))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

}