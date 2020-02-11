package com.kkukielka.api.v1.mapper;

import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    private VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        // given
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Test");

        // when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // then
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendor() {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Test");
        vendorDTO.setVendorUrl("/api/test");

        // when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        // then
        assertEquals(vendorDTO.getName(), vendor.getName());
    }

}
