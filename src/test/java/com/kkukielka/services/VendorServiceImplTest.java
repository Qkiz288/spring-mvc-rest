package com.kkukielka.services;

import com.kkukielka.api.v1.mapper.VendorMapper;
import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.domain.Vendor;
import com.kkukielka.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        Mockito.when(vendorRepository.findAll()).thenReturn(vendors);

        // when
        List<VendorDTO> returnVendorsDTO = vendorService.getAllVendors();

        // then
        assertEquals(vendors.size(), returnVendorsDTO.size());
        Mockito.verify(vendorRepository, Mockito.times(1)).findAll();
    }
}