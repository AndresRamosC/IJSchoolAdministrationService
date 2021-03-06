package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A ClassGroup.
 */
@Entity
@Table(name = "class_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "class_room")
    private String classRoom;

    @Column(name = "size")
    private Integer size;

    @OneToMany(mappedBy = "classGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "classGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Assignment> assignments = new HashSet<>();

    @OneToMany(mappedBy = "classGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GroupNotices> groupNotices = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "class_group_student",
               joinColumns = @JoinColumn(name = "class_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private Set<Student> students = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "class_group_class_schedule",
               joinColumns = @JoinColumn(name = "class_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "class_schedule_id", referencedColumnName = "id"))
    private Set<ClassSchedule> classSchedules = new HashSet<>();

    @OneToOne(mappedBy = "classGroup")
    @JsonIgnore
    private Grade grade;

    @ManyToOne
    @JsonIgnoreProperties("classGroups")
    private Subject subject;

    @ManyToOne
    @JsonIgnoreProperties("classGroups")
    private Teacher teacher;

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

    public ClassGroup creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public ClassGroup groupCode(String groupCode) {
        this.groupCode = groupCode;
        return this;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public ClassGroup classRoom(String classRoom) {
        this.classRoom = classRoom;
        return this;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getSize() {
        return size;
    }

    public ClassGroup size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public ClassGroup attendances(Set<Attendance> attendances) {
        this.attendances = attendances;
        return this;
    }

    public ClassGroup addAttendance(Attendance attendance) {
        this.attendances.add(attendance);
        attendance.setClassGroup(this);
        return this;
    }

    public ClassGroup removeAttendance(Attendance attendance) {
        this.attendances.remove(attendance);
        attendance.setClassGroup(null);
        return this;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public ClassGroup assignments(Set<Assignment> assignments) {
        this.assignments = assignments;
        return this;
    }

    public ClassGroup addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
        assignment.setClassGroup(this);
        return this;
    }

    public ClassGroup removeAssignment(Assignment assignment) {
        this.assignments.remove(assignment);
        assignment.setClassGroup(null);
        return this;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }

    public Set<GroupNotices> getGroupNotices() {
        return groupNotices;
    }

    public ClassGroup groupNotices(Set<GroupNotices> groupNotices) {
        this.groupNotices = groupNotices;
        return this;
    }

    public ClassGroup addGroupNotices(GroupNotices groupNotices) {
        this.groupNotices.add(groupNotices);
        groupNotices.setClassGroup(this);
        return this;
    }

    public ClassGroup removeGroupNotices(GroupNotices groupNotices) {
        this.groupNotices.remove(groupNotices);
        groupNotices.setClassGroup(null);
        return this;
    }

    public void setGroupNotices(Set<GroupNotices> groupNotices) {
        this.groupNotices = groupNotices;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public ClassGroup students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public ClassGroup addStudent(Student student) {
        this.students.add(student);
        student.getClassGroups().add(this);
        return this;
    }

    public ClassGroup removeStudent(Student student) {
        this.students.remove(student);
        student.getClassGroups().remove(this);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<ClassSchedule> getClassSchedules() {
        return classSchedules;
    }

    public ClassGroup classSchedules(Set<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
        return this;
    }

    public ClassGroup addClassSchedule(ClassSchedule classSchedule) {
        this.classSchedules.add(classSchedule);
        classSchedule.getClassGroups().add(this);
        return this;
    }

    public ClassGroup removeClassSchedule(ClassSchedule classSchedule) {
        this.classSchedules.remove(classSchedule);
        classSchedule.getClassGroups().remove(this);
        return this;
    }

    public void setClassSchedules(Set<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
    }

    public Grade getGrade() {
        return grade;
    }

    public ClassGroup grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public ClassGroup subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public ClassGroup teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassGroup)) {
            return false;
        }
        return id != null && id.equals(((ClassGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClassGroup{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", groupCode='" + getGroupCode() + "'" +
            ", classRoom='" + getClassRoom() + "'" +
            ", size=" + getSize() +
            "}";
    }
}
