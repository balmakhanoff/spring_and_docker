package com.companiesapiservice.service;

import com.companiesapiservice.exception.CompanyNotFoundException;
import com.companiesapiservice.model.Company;
import com.companiesapiservice.dto.CompanyDto;
import com.companiesapiservice.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    //    public List<CompanyDto> getAllCompany() {
//        log.info("Получение списка всех компаний");
//        List<CompanyDto> companies = companyRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
//        log.info("Найдено {} компаний", companies.size());
//        return companies;
//    }
    public Page<CompanyDto> getAllCompany(Pageable pageable) {
        log.info("Получение списка всех кампаний, страница: {}, размер страницы: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Company> companyPage = companyRepository.findAll(pageable);
        Page<CompanyDto> companyDtoPage = companyPage.map(this::toDto);
        log.info("Найдено {} кампаний на текущей странице", companyDtoPage.getNumberOfElements());
        return companyDtoPage;
    }

    public Optional<CompanyDto> getCompanyById(Long id) {
        log.info("Поиск компании по ID: {}", id);
        Optional<CompanyDto> company = companyRepository.findById(id).map(this::toDto);
        if (company.isPresent()) {
            log.info("Компания с ID {} найдена", id);
        } else {
            log.warn("Компания с ID {} не найдена", id);
        }
        return company;
    }

    public CompanyDto createCompany(CompanyDto companyDto) {
        log.info("Создание новой компании с именем: {}", companyDto.getCompanyName());
        Company company = toEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        log.info("Компания с ID {} успешно создана", savedCompany.getId());
        return toDto(savedCompany);
    }

    @Transactional
    public CompanyDto updateCompany(Long id, CompanyDto updates) {
        log.info("Обновление компании с ID: {}", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Компания с ID {} не найдена", id);
                    return new CompanyNotFoundException("Company not found with id: " + id);
                });

        if (updates.getCompanyName() != null) {
            log.info("Обновление имени компании на: {}", updates.getCompanyName());
            company.setCompanyName(updates.getCompanyName());
        }

        if (updates.getBudget() != 0.0) {
            log.info("Обновление бюджета компании на: {}", updates.getBudget());
            company.setBudget(updates.getBudget());
        }

        companyRepository.save(company);
        log.info("Компания с ID {} успешно обновлена", id);
        return toDto(company);
    }

    public void deleteCompany(Long id) {
        log.info("Удаление компании с ID: {}", id);
        companyRepository.deleteById(id);
        log.info("Компания с ID {} успешно удалена", id);
    }

    private CompanyDto toDto(Company company) {
        return new CompanyDto(company.getId(), company.getCompanyName(), company.getBudget());
    }

    private Company toEntity(CompanyDto companyDto) {
        return new Company(companyDto.getId(), companyDto.getCompanyName(), companyDto.getBudget());
    }
}