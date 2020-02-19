package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private Long zipCode;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(mappedBy = "contacts")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Company> companies = new HashSet<>();

    @ManyToMany(mappedBy = "contacts")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Person> people = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Contact creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String geteMail() {
        return eMail;
    }

    public Contact eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getState() {
        return state;
    }

    public Contact state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public Contact city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public Contact zipCode(Long zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public Contact address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contact phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Contact companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Contact addCompany(Company company) {
        this.companies.add(company);
        company.getContacts().add(this);
        return this;
    }

    public Contact removeCompany(Company company) {
        this.companies.remove(company);
        company.getContacts().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public Contact people(Set<Person> people) {
        this.people = people;
        return this;
    }

    public Contact addPerson(Person person) {
        this.people.add(person);
        person.getContacts().add(this);
        return this;
    }

    public Contact removePerson(Person person) {
        this.people.remove(person);
        person.getContacts().remove(this);
        return this;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", eMail='" + geteMail() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode=" + getZipCode() +
            ", address='" + getAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
