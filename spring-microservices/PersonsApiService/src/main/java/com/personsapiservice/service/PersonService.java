package com.personsapiservice.service;

import com.personsapiservice.dto.PersonDto;
import com.personsapiservice.exception.PersonNotFoundException;
import com.personsapiservice.model.Person;
import com.personsapiservice.repository.PersonRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final RestClient restClient;

    public PersonService(PersonRepository personRepository, RestClient restClient) {
        this.personRepository = personRepository;
        this.restClient = restClient;
    }

    //    public List<PersonDto> getAllPersons() {
//        log.info("Получение списка всех пользователей");
//        List<PersonDto> persons = personRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
//        log.info("Найдено {} пользователей", persons.size());
//        return persons;
//    }
    public Page<PersonDto> getAllPersons(Pageable pageable) {
        log.info("Получение списка всех пользователей, страница: {}, размер страницы: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Person> personPage = personRepository.findAll(pageable);
        Page<PersonDto> personDtoPage = personPage.map(this::toDto);
        log.info("Найдено {} пользователей на текущей странице", personDtoPage.getNumberOfElements());
        return personDtoPage;
    }

    public Optional<PersonDto> getPersonById(Long id) {
        log.info("Поиск пользователя по ID: {}", id);
        Optional<PersonDto> person = personRepository.findById(id).map(this::toDto);
        if (person.isPresent()) {
            log.info("Пользователь с ID {} найден", id);
        } else {
            log.warn("Пользователь с ID {} не найден", id);
        }
        return person;
    }

    public PersonDto createPerson(PersonDto personDto) {
        log.info("Создание нового пользователя с данными: {}", personDto);
        Person person = toEntity(personDto);
        Person savedPerson = personRepository.save(person);
        PersonDto savedPersonDto = toDto(savedPerson);
        log.info("Пользователь создан с ID: {}", savedPersonDto.getId());

        sendPersonIdToCompanyService(savedPersonDto.getCompanyId(), savedPersonDto.getId());

        return savedPersonDto;
    }

    @Transactional
    public PersonDto updatePerson(Long id, PersonDto updates) {
        log.info("Обновление пользователя с ID: {}", id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", id);
                    return new PersonNotFoundException("Person not found with id: " + id);
                });

        if (updates.getFirstName() != null) {
            log.info("Обновление имени пользователя на: {}", updates.getFirstName());
            person.setFirstName(updates.getFirstName());
        }
        if (updates.getLastName() != null) {
            log.info("Обновление фамилии пользователя на: {}", updates.getLastName());
            person.setLastName(updates.getLastName());
        }
        if (updates.getPhoneNumber() != null) {
            log.info("Обновление телефона пользователя на: {}", updates.getPhoneNumber());
            person.setPhoneNumber(updates.getPhoneNumber());
        }
        if (updates.getCompanyId() != null) {
            log.info("Обновление айди кампаний пользователя на: {}", updates.getCompanyId());
            person.setCompanyId(updates.getCompanyId());
        }

        personRepository.save(person);
        log.info("Пользователь с ID {} успешно обновлен", id);
        return toDto(person);
    }

    public void deletePerson(Long id) {
        log.info("Удаление пользователя с ID: {}", id);
        personRepository.deleteById(id);
        log.info("Пользователь с ID {} удален", id);
    }

    public List<PersonDto> getPersonsByCompanyId(Long companyId) {
        log.info("Получение пользователей по Company ID: {}", companyId);
        List<PersonDto> persons = personRepository.findByCompanyId(companyId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} пользователей для Company ID {}", persons.size(), companyId);
        return persons;
    }

    private PersonDto toDto(Person person) {
        return new PersonDto(person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getPhoneNumber(),
                person.getCompanyId());
    }

    private Person toEntity(PersonDto personDto) {
        return new Person(personDto.getId(),
                personDto.getFirstName(),
                personDto.getLastName(),
                personDto.getPhoneNumber(),
                personDto.getCompanyId());
    }

    private void sendPersonIdToCompanyService(long companyId, long personId) {
        String companiesApiUrl = "http://localhost:8082/companies/employee";
        log.info("Отправка ID пользователя {} в компанию с ID {}", personId, companyId);

        CompanyEmployeeRequest request = new CompanyEmployeeRequest();
        request.setCompanyId(companyId);
        request.setPersonId(personId);

        ResponseEntity<Void> response = restClient.post()
                .uri(companiesApiUrl)
                .body(request)
                .retrieve()
                .toBodilessEntity();

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("ID пользователя успешно добавлен в список компании");
        } else {
            log.error("Ошибка при отправке ID пользователя в список компании: статус {}", response.getStatusCode());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CompanyEmployeeRequest {
        private Long companyId;
        private Long personId;
    }
}