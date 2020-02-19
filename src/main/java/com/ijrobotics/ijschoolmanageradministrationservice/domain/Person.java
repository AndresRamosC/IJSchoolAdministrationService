package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.Genders;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.BlodTypes;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Genders gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BlodTypes bloodGroup;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Lob
    @Column(name = "photograph")
    private byte[] photograph;

    @Column(name = "photograph_content_type")
    private String photographContentType;

    @Column(name = "assigned")
    private Boolean assigned;

    @OneToOne
    @JoinColumn(unique = true)
    private UserExtend userExtend;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_contact",
               joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"))
    private Set<Contact> contacts = new HashSet<>();

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Guardian guardian;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Student student;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Employee employee;

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

    public Person creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Genders getGender() {
        return gender;
    }

    public Person gender(Genders gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public BlodTypes getBloodGroup() {
        return bloodGroup;
    }

    public Person bloodGroup(BlodTypes bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(BlodTypes bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Person dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public byte[] getPhotograph() {
        return photograph;
    }

    public Person photograph(byte[] photograph) {
        this.photograph = photograph;
        return this;
    }

    public void setPhotograph(byte[] photograph) {
        this.photograph = photograph;
    }

    public String getPhotographContentType() {
        return photographContentType;
    }

    public Person photographContentType(String photographContentType) {
        this.photographContentType = photographContentType;
        return this;
    }

    public void setPhotographContentType(String photographContentType) {
        this.photographContentType = photographContentType;
    }

    public Boolean isAssigned() {
        return assigned;
    }

    public Person assigned(Boolean assigned) {
        this.assigned = assigned;
        return this;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public UserExtend getUserExtend() {
        return userExtend;
    }

    public Person userExtend(UserExtend userExtend) {
        this.userExtend = userExtend;
        return this;
    }

    public void setUserExtend(UserExtend userExtend) {
        this.userExtend = userExtend;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Person contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Person addContact(Contact contact) {
        this.contacts.add(contact);
        contact.getPeople().add(this);
        return this;
    }

    public Person removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.getPeople().remove(this);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public Person guardian(Guardian guardian) {
        this.guardian = guardian;
        return this;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public Student getStudent() {
        return student;
    }

    public Person student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Person employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", photograph='" + getPhotograph() + "'" +
            ", photographContentType='" + getPhotographContentType() + "'" +
            ", assigned='" + isAssigned() + "'" +
            "}";
    }
}
