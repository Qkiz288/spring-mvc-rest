package com.kkukielka.controllers.v1;

import com.kkukielka.api.v1.model.VendorDTO;
import com.kkukielka.api.v1.model.VendorListDTO;
import com.kkukielka.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

}
