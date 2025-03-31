package com.personsapiservice.repository;

import com.personsapiservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByCompanyId(Long companyId);
}
