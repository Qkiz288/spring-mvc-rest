package com.kkukielka.services;

import com.kkukielka.api.v1.mapper.VendorMapper;
import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.controllers.v1.VendorController;
import com.kkukielka.domain.Vendor;
import com.kkukielka.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    @Mock
    private VendorRepository vendorRepository;

    private VendorMapper vendorMapper = VendorMapper.INSTANCE;

    private VendorService vendorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() {
        // given
        List<Vendor> vendors = Arrays.asList(
                new Vendor(1L, "Test1"),
                new Vendor(2L, "Test2"),
                new Vendor(3L, "Test3"));

        Mockito.when(vendorRepository.findAll()).thenReturn(vendors);

        // when
        List<VendorDTO> returnVendorsDTO = vendorService.getAllVendors();

        // then
        assertEquals(vendors.size(), returnVendorsDTO.size());
        assertEquals(VendorController.BASE_URL + "/1", returnVendorsDTO.get(0).getVendorUrl());
        Mockito.verify(vendorRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getVendorById() {
        // given
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Test");

        Optional<Vendor> vendorOptional = Optional.of(vendor);

        when(vendorRepository.findById(anyLong())).thenReturn(vendorOptional);

        // when
        VendorDTO returnVendorDTO = vendorService.getVendorById(1L);

        // then
        assertEquals(vendor.getName(), returnVendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", returnVendorDTO.getVendorUrl());
        verify(vendorRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createNewVendor() {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");

        Vendor returnVendor = new Vendor();
        returnVendor.setId(1L);
        returnVendor.setName("Test");

        Mockito.when(vendorRepository.save(any(Vendor.class))).thenReturn(returnVendor);

        // when
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        // then
        assertEquals(returnVendor.getName(), savedVendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedVendorDTO.getVendorUrl());
        Mockito.verify(vendorRepository, Mockito.times(1)).save(any(Vendor.class));
    }

    @Test
    public void deleteCustomerById() {
        // given
        Long idToDelete = 1L;

        doNothing().when(vendorRepository).deleteById(anyLong());

        // when
        vendorService.deleteVendorById(idToDelete);

        // then
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }

}