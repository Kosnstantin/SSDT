package com.example.sms.controller;

import com.example.sms.model.Group;
import com.example.sms.model.Role;
import com.example.sms.model.Student;
import com.example.sms.repository.GroupRepository;
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
    private GroupRepository groupRepository;

    private List<Student> students;
    private List<Group> allGroups;
    private Student currentStudent;

    private Integer selectedGroupId;

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

    // Підготовка до створення
    public String prepareCreate() {
        this.currentStudent = new Student();
        this.selectedGroupId = null;
        return "student-edit?faces-redirect=true";
    }

    // Підготовка до редагування
    public String prepareEdit(Student s) {
        this.currentStudent = s;

        if (s.getGroup() != null) {
            this.selectedGroupId = s.getGroup().getId();
        } else {
            this.selectedGroupId = null;
        }

        return "student-edit?faces-redirect=true";
    }

    // ЗБЕРЕЖЕННЯ
    public String save() {
        // 1. Встановлюємо групу
        if (selectedGroupId != null) {
            groupRepository.findById(selectedGroupId).ifPresent(g -> currentStudent.setGroup(g));
        } else {
            currentStudent.setGroup(null);
        }

        // 2. Встановлюємо роль
        currentStudent.setRole(Role.STUDENT);

        // 3. ЛОГІКА ПАРОЛЯ
        if (currentStudent.getId() == null) {
            // СТВОРЕННЯ: Просто зберігаємо (пароль тут обов'язковий через форму)
            studentService.createStudent(currentStudent);
        } else {
            // РЕДАГУВАННЯ: Перевіряємо пароль
            if (currentStudent.getPassword() == null || currentStudent.getPassword().isEmpty()) {
                // Якщо поле пусте - дістаємо старий пароль з БД
                Student oldVersion = studentService.findStudent(currentStudent.getId());
                if (oldVersion != null) {
                    currentStudent.setPassword(oldVersion.getPassword());
                }
            }
            // Якщо поле НЕ пусте - значить користувач ввів новий пароль, він запишеться сам

            studentService.updateStudent(currentStudent.getId(), currentStudent);
        }

        loadStudents();
        this.currentStudent = new Student();
        return "students-list?faces-redirect=true";
    }

    public void delete(Integer id) {
        studentService.deleteStudent(id);
        loadStudents();
    }

    // Геттери та сеттери
    public List<Student> getStudents() {
        return students;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public List<Group> getAllGroups() {
        return allGroups;
    }

    public Integer getSelectedGroupId() {
        return selectedGroupId;
    }

    public void setSelectedGroupId(Integer selectedGroupId) {
        this.selectedGroupId = selectedGroupId;
    }
}