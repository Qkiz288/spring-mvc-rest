
package com.kkukielka.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @JsonProperty("customer_url")
    private String customerUrl;

    @ApiModelProperty(value = "This is customer's first name", required = true)
    private String firstname;
    private String lastname;
}
