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
 * A Guardian.
 */
@Entity
@Table(name = "guardian")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Guardian implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "work_adress")
    private String workAdress;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @ManyToMany(mappedBy = "guardians")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

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

    public Guardian creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public Guardian educationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
        return this;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getOccupation() {
        return occupation;
    }

    public Guardian occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getWorkAdress() {
        return workAdress;
    }

    public Guardian workAdress(String workAdress) {
        this.workAdress = workAdress;
        return this;
    }

    public void setWorkAdress(String workAdress) {
        this.workAdress = workAdress;
    }

    public Person getPerson() {
        return person;
    }

    public Guardian person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Guardian students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public Guardian addStudent(Student student) {
        this.students.add(student);
        student.getGuardians().add(this);
        return this;
    }

    public Guardian removeStudent(Student student) {
        this.students.remove(student);
        student.getGuardians().remove(this);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Guardian)) {
            return false;
        }
        return id != null && id.equals(((Guardian) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Guardian{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", educationLevel='" + getEducationLevel() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", workAdress='" + getWorkAdress() + "'" +
            "}";
    }
}
