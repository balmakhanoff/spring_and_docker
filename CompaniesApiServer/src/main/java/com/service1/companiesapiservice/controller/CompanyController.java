package com.service1.companiesapiservice.controller;

import com.service1.companiesapiservice.dto.PersonDto;
import com.service1.companiesapiservice.model.Company;
import com.service1.companiesapiservice.service.CompanyService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final WebClient.Builder webClientBuilder;

    public CompanyController(CompanyService companyService, WebClient.Builder webClientBuilder) {
        this.companyService = companyService;
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping
    public List<Company> getAll() {
        return companyService.getAllCompany();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<PersonDto>> getEmployeesByCompanyId(@PathVariable Long id) {
        List<PersonDto> employees = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/persons/by-company/{id}", id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PersonDto>>() {})
                .block();

        return ResponseEntity.ok(employees);
    }
}
