package com.example.sms.controller;

import com.example.sms.model.Group;
import com.example.sms.model.Student;
import com.example.sms.repository.GroupRepository; // Додали репозиторій груп
import com.example.sms.service.StudentServiceLocal;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped; 
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped 
public class StudentController implements Serializable { 

    private static final long serialVersionUID = 1L; 

    @Inject
    private StudentServiceLocal studentService;

    @Inject
    private GroupRepository groupRepository; // Ін'єкція репозиторію груп

    private List<Student> students;
    private List<Group> allGroups; // Список для випадаючого меню
    private Student currentStudent; // Тут зберігається студент, якого редагуємо
    
    private Integer selectedGroupId; // Тут зберігається ID вибраної групи

    @PostConstruct
    public void init() {
        loadStudents();
        loadGroups();
    }

    public void loadStudents() {
        this.students = studentService.listStudents();
    }

    public void loadGroups() {
        this.allGroups = groupRepository.findAll();
    }

    // Метод для додавання (створює порожній об'єкт)
    public String prepareCreate() {
        this.currentStudent = new Student(); 
        this.selectedGroupId = null; // Очищаємо вибір групи
        return "student-edit?faces-redirect=true";
    }

    // Метод для редагування (запам'ятовує вибраного студента)
    public String prepareEdit(Student s) {
        this.currentStudent = s; // "кладемо" студента в пам'ять сесії
        
        // Якщо у студента є група, встановлюємо її ID у випадаючий список
        if (s.getGroup() != null) {
            this.selectedGroupId = s.getGroup().getId();
        } else {
            this.selectedGroupId = null;
        }
        
        return "student-edit?faces-redirect=true"; // І переходимо на сторінку
    }

    public String save() {
        // 1. Знаходимо об'єкт групи за вибраним ID
        if (selectedGroupId != null) {
            groupRepository.findById(selectedGroupId).ifPresent(g -> currentStudent.setGroup(g));
        } else {
            currentStudent.setGroup(null);
        }

        // 2. Зберігаємо студента
        if (currentStudent.getId() == null) {
            studentService.createStudent(currentStudent);
        } else {
            studentService.updateStudent(currentStudent.getId(), currentStudent);
        }
        
        loadStudents();
        // Після збереження можна очистити currentStudent, щоб не тримати сміття в сесії
        this.currentStudent = new Student(); 
        return "students-list?faces-redirect=true";
    }

    public void delete(Integer id) {
        studentService.deleteStudent(id);
        loadStudents();
    }

    // Геттери та сеттери
    public List<Student> getStudents() { return students; }
    public Student getCurrentStudent() { return currentStudent; }
    public void setCurrentStudent(Student currentStudent) { this.currentStudent = currentStudent; }
    
    public List<Group> getAllGroups() { return allGroups; } 
    
    public Integer getSelectedGroupId() { return selectedGroupId; }
    public void setSelectedGroupId(Integer selectedGroupId) { this.selectedGroupId = selectedGroupId; }
}