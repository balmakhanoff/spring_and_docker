package com.companiesapiservice.service;

import com.companiesapiservice.dto.CompanyPersonsDto;
import com.companiesapiservice.model.CompanyPersons;
import com.companiesapiservice.repository.CompanyPersonsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyPersonsService {
    private final CompanyPersonsRepository companyPersonsRepository;

    public CompanyPersonsService(CompanyPersonsRepository companyPersonRepository) {
        this.companyPersonsRepository = companyPersonRepository;
    }

    public CompanyPersonsDto savePersonId(CompanyPersonsDto companyPersonsDto) {
        CompanyPersons companyPersons = toEntity(companyPersonsDto);
        CompanyPersons savedCompanyPersons = companyPersonsRepository.save(companyPersons);
        return toDto(savedCompanyPersons);
    }

    public List<CompanyPersonsDto> getEmployeesByCompanyId(Long companyId) {
        List<CompanyPersons> employees = companyPersonsRepository.findByCompanyId(companyId);
        return employees.stream().map(this::toDto).collect(Collectors.toList());
    }

    public void deleteCompanyPerson(Long id) {
        companyPersonsRepository.deleteById(id);
    }

    private CompanyPersonsDto toDto(CompanyPersons companyPersons) {
        return new CompanyPersonsDto(companyPersons.getId(), companyPersons.getCompanyId(), companyPersons.getPersonId());
    }

    private CompanyPersons toEntity(CompanyPersonsDto companyPersonsDto) {
        return new CompanyPersons(companyPersonsDto.getId(), companyPersonsDto.getCompanyId(), companyPersonsDto.getPersonId());
    }
}
