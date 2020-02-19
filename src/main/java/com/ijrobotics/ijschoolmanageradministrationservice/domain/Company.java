package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "name")
    private String name;

    @Column(name = "bussiness_field")
    private String bussinessField;

    @Column(name = "web_page")
    private String webPage;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_contact",
               joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"))
    private Set<Contact> contacts = new HashSet<>();

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

    public Company creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBussinessField() {
        return bussinessField;
    }

    public Company bussinessField(String bussinessField) {
        this.bussinessField = bussinessField;
        return this;
    }

    public void setBussinessField(String bussinessField) {
        this.bussinessField = bussinessField;
    }

    public String getWebPage() {
        return webPage;
    }

    public Company webPage(String webPage) {
        this.webPage = webPage;
        return this;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Company contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Company addContact(Contact contact) {
        this.contacts.add(contact);
        contact.getCompanies().add(this);
        return this;
    }

    public Company removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.getCompanies().remove(this);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", name='" + getName() + "'" +
            ", bussinessField='" + getBussinessField() + "'" +
            ", webPage='" + getWebPage() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
