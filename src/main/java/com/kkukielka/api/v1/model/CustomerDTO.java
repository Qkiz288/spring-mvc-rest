
package com.kkukielka.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @JsonProperty("customer_url")
    private String customerUrl;
    private String firstname;
    private String lastname;
}
