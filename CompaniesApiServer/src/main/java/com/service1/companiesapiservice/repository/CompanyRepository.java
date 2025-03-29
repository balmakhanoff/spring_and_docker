package com.service1.companiesapiservice.repository;

import com.service1.companiesapiservice.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
