
package com.kkukielka.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerListDTO {
    private List<CustomerDTO> customers;
    private MetaDTO meta;

    public CustomerListDTO(List<CustomerDTO> customers) {
        this.customers = customers;
    }
}
