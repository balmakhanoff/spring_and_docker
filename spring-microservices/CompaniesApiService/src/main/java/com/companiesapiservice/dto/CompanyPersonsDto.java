package com.companiesapiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyPersonsDto {
    private Long id;
    private Long companyId;
    private Long personId;
}
