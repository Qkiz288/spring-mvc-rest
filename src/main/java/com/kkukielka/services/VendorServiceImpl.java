package com.kkukielka.services;

import com.kkukielka.api.v1.mapper.VendorMapper;
import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.controllers.v1.VendorController;
import com.kkukielka.domain.Vendor;
import com.kkukielka.repositories.VendorRepository;
import com.kkukielka.services.exceptions.ResourceNotFoundException;
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
    public VendorDTO getVendorById(Long id) {
        return vendorRepository
                .findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + vendor.getId());
                    return vendorDTO;
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Vendor with ID = %s not found", id)));
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);

        savedVendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + savedVendor.getId());

        return savedVendorDTO;
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnVendorDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if (vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }

                    Vendor patchedVendor = vendorRepository.save(vendor);

                    VendorDTO patchedVendorDTO = vendorMapper.vendorToVendorDTO(patchedVendor);
                    patchedVendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + vendor.getId());

                    return patchedVendorDTO;
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Vendor with ID = %s not found", id)));
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnVendorDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);

        savedVendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + savedVendor.getId());

        return savedVendorDTO;
    }
}
