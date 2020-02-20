package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "admissin_date")
    private LocalDate admissinDate;

    @Column(name = "academic_year")
    private Integer academicYear;

    @Column(name = "control_number")
    private Long controlNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Grade> grades = new HashSet<>();

    @OneToMany(mappedBy = "student")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Assignment> assignments = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "student_guardian",
               joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "guardian_id", referencedColumnName = "id"))
    private Set<Guardian> guardians = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "student_class_group",
               joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "class_group_id", referencedColumnName = "id"))
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

    public Student creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getAdmissinDate() {
        return admissinDate;
    }

    public Student admissinDate(LocalDate admissinDate) {
        this.admissinDate = admissinDate;
        return this;
    }

    public void setAdmissinDate(LocalDate admissinDate) {
        this.admissinDate = admissinDate;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public Student academicYear(Integer academicYear) {
        this.academicYear = academicYear;
        return this;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public Long getControlNumber() {
        return controlNumber;
    }

    public Student controlNumber(Long controlNumber) {
        this.controlNumber = controlNumber;
        return this;
    }

    public void setControlNumber(Long controlNumber) {
        this.controlNumber = controlNumber;
    }

    public Person getPerson() {
        return person;
    }

    public Student person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public Student attendances(Set<Attendance> attendances) {
        this.attendances = attendances;
        return this;
    }

    public Student addAttendance(Attendance attendance) {
        this.attendances.add(attendance);
        attendance.setStudent(this);
        return this;
    }

    public Student removeAttendance(Attendance attendance) {
        this.attendances.remove(attendance);
        attendance.setStudent(null);
        return this;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public Student grades(Set<Grade> grades) {
        this.grades = grades;
        return this;
    }

    public Student addGrade(Grade grade) {
        this.grades.add(grade);
        grade.setStudent(this);
        return this;
    }

    public Student removeGrade(Grade grade) {
        this.grades.remove(grade);
        grade.setStudent(null);
        return this;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public Student assignments(Set<Assignment> assignments) {
        this.assignments = assignments;
        return this;
    }

    public Student addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        assignment.setStudent(this);
        return this;
    }

    public Student removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
        assignment.setStudent(null);
        return this;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }

    public Set<Guardian> getGuardians() {
        return guardians;
    }

    public Student guardians(Set<Guardian> guardians) {
        this.guardians = guardians;
        return this;
    }

    public Student addGuardian(Guardian guardian) {
        this.guardians.add(guardian);
        guardian.getStudents().add(this);
        return this;
    }

    public Student removeGuardian(Guardian guardian) {
        this.guardians.remove(guardian);
        guardian.getStudents().remove(this);
        return this;
    }

    public void setGuardians(Set<Guardian> guardians) {
        this.guardians = guardians;
    }

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public Student classGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
        return this;
    }

    public Student addClassGroup(ClassGroup classGroup) {
        this.classGroups.add(classGroup);
        classGroup.getStudents().add(this);
        return this;
    }

    public Student removeClassGroup(ClassGroup classGroup) {
        this.classGroups.remove(classGroup);
        classGroup.getStudents().remove(this);
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
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", admissinDate='" + getAdmissinDate() + "'" +
            ", academicYear=" + getAcademicYear() +
            ", controlNumber=" + getControlNumber() +
            "}";
    }
}
