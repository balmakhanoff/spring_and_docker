package com.service1.companiesapiservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String companyName;
    private double budget;

    public Company() {
    }

    public Company(Long id, String companyName, double budget) {
        this.id = id;
        this.companyName = companyName;
        this.budget = budget;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getBudget() {
        return this.budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", budget=" + budget +
                '}';
    }
}

