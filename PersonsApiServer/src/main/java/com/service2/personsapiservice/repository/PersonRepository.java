package com.service2.personsapiservice.repository;

import com.service2.personsapiservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByCompanyId(Long companyId);
}
