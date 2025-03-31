package com.companiesapiservice.controller;

import java.util.List;
import java.util.Optional;

import com.companiesapiservice.dto.CompanyDto;
import com.companiesapiservice.dto.CompanyPersonsDto;
import com.companiesapiservice.exception.CompanyNotFoundException;
import com.companiesapiservice.service.CompanyPersonsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.companiesapiservice.service.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyPersonsService companyPersonsService;

    public CompanyController(CompanyService companyService, CompanyPersonsService companyPersonsService) {
        this.companyService = companyService;
        this.companyPersonsService = companyPersonsService;
    }

    @GetMapping
    public Page<CompanyDto> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return companyService.getAllCompany(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id) {
        Optional<CompanyDto> company = companyService.getCompanyById(id);
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto company) {
        return companyService.createCompany(company);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable("id") Long id, @RequestBody CompanyDto updates) {
        try {
            CompanyDto updatedCompany = companyService.updateCompany(id, updates);
            return ResponseEntity.ok(updatedCompany);
        } catch (CompanyNotFoundException error) {
            return ResponseEntity.notFound().build();
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/employee")
    public CompanyPersonsDto saveEmployee(@RequestBody CompanyPersonsDto request) {
        return companyPersonsService.savePersonId(request);
    }

    @GetMapping("/{companyId}/employees")
    public ResponseEntity<List<CompanyPersonsDto>> getEmployeesByCompanyId(@PathVariable("companyId") Long companyId) {
        List<CompanyPersonsDto> employees = companyPersonsService.getEmployeesByCompanyId(companyId);
        return ResponseEntity.ok(employees);
    }
}