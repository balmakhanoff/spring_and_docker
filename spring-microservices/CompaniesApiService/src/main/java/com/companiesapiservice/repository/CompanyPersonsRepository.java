package com.companiesapiservice.repository;

import com.companiesapiservice.model.CompanyPersons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyPersonsRepository extends JpaRepository<CompanyPersons, Long> {
    List<CompanyPersons> findByCompanyId(Long companyId);
}
