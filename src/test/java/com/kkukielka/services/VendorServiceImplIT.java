package com.kkukielka.services;

import com.kkukielka.api.v1.mapper.VendorMapper;
import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.bootstrap.Bootstrap;
import com.kkukielka.controllers.v1.VendorController;
import com.kkukielka.domain.Vendor;
import com.kkukielka.repositories.CategoryRepository;
import com.kkukielka.repositories.CustomerRepository;
import com.kkukielka.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class VendorServiceImplIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    private VendorService vendorService;

    @Before
    public void setUp() {
        log.debug("Loading customer data");
        log.debug("Count: " + customerRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void patchVendor() {
        // given
        Long id = getVendorIdValue();
        String newName = "Updated";
        String vendorUrl = VendorController.BASE_URL + "/" + id;

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(newName);

        // when
        VendorDTO patchedVendor = vendorService.patchVendor(id, vendorDTO);

        //then
        assertEquals(newName, patchedVendor.getName());
        assertEquals(vendorUrl, patchedVendor.getVendorUrl());
    }

    @Test
    public void deleteVendor() {
        // given
        Long id = getVendorIdValue();

        // when
        vendorService.deleteVendorById(id);

        // then
        assertTrue(vendorRepository.findAll()
                .stream()
                .filter(vendor -> vendor.getId().equals(id))
                .collect(Collectors.toList())
                .isEmpty());
    }

    private Long getVendorIdValue(){
        List<Vendor> vendors = vendorRepository.findAll();

        System.out.println("Vendors Found: " + vendors.size());

        //return first id
        return vendors.get(0).getId();
    }

}
