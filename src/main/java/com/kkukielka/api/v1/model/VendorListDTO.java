package com.kkukielka.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorListDTO {
    private MetaDTO metaDTO;
    private List<VendorDTO> vendors;

    public VendorListDTO(List<VendorDTO> vendorDTOs) {
        this.vendors = vendorDTOs;
    }
}
