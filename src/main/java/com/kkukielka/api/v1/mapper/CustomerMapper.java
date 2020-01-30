package com.kkukielka.api.v1.mapper;

import com.kkukielka.api.v1.model.CustomerDTO;
import com.kkukielka.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

}
