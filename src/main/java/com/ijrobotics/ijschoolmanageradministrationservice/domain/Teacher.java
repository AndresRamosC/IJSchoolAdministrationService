package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    @OneToMany(mappedBy = "teacher")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ClassGroup> classGroups = new HashSet<>();

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

    public Teacher creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Teacher employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public Teacher classGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
        return this;
    }

    public Teacher addClassGroup(ClassGroup classGroup) {
        this.classGroups.add(classGroup);
        classGroup.setTeacher(this);
        return this;
    }

    public Teacher removeClassGroup(ClassGroup classGroup) {
        this.classGroups.remove(classGroup);
        classGroup.setTeacher(null);
        return this;
    }

    public void setClassGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher)) {
            return false;
        }
        return id != null && id.equals(((Teacher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
