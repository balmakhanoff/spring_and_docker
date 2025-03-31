package com.companiesapiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private Long companyId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}