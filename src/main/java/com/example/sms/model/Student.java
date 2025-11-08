package com.example.sms.model;

public class Student extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
    private Integer groupId;

    public Student() {}

    public Student(Integer id, String firstName, String lastName, String email, String dob, Integer groupId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.groupId = groupId;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public Integer getGroupId() { return groupId; }
    public void setGroupId(Integer groupId) { this.groupId = groupId; }

    public String getFullName() { return firstName + " " + lastName; }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + getFullName() + ", email=" + email + '}';
    }
}