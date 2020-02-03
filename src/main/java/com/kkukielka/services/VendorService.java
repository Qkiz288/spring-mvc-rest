package com.kkukielka.services;

import com.kkukielka.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();
    VendorDTO createNewVendor(VendorDTO vendorDTO);
    void deleteVendorById(Long id);
}
