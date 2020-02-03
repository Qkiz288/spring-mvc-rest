package com.kkukielka.services;

import com.kkukielka.api.v1.mapper.VendorMapper;
import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.controllers.v1.VendorController;
import com.kkukielka.domain.Vendor;
import com.kkukielka.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;
    private VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository
                .findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + vendor.getId());
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);

        savedVendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + savedVendor.getId());

        return savedVendorDTO;
    }
}
