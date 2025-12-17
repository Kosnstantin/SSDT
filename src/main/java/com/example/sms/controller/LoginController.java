package com.example.sms.controller;

import com.example.sms.model.Person;
import com.example.sms.repository.UserRepository;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserRepository userRepository;

    private String username;
    private String password;
    private Person currentUser;

    // Метод входу
    public String login() {
        // 1. Шукаємо користувача
        return userRepository.findByUsername(username)
            .map(u -> {
                // 2. Перевіряємо пароль (поки що просто текст)
                if (u.getPassword().equals(password)) {
                    this.currentUser = u;
                    return "index?faces-redirect=true"; // Успіх -> на Головну
                } else {
                    errorMessage("Invalid password");
                    return null; // Помилка -> залишитися тут
                }
            })
            .orElseGet(() -> {
                errorMessage("User not found");
                return null;
            });
    }

    // Метод виходу
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    private void errorMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
    }

    // Геттери та Сеттери
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Person getCurrentUser() { return currentUser; }


    // --- HELPER METHODS FOR UI (Role Checks) ---

    public boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == com.example.sms.model.Role.ADMIN;
    }

    public boolean isDean() {
        return currentUser != null && currentUser.getRole() == com.example.sms.model.Role.DEAN;
    }

    public boolean isTeacher() {
        return currentUser != null && currentUser.getRole() == com.example.sms.model.Role.TEACHER;
    }

    public boolean isStudent() {
        return currentUser != null && currentUser.getRole() == com.example.sms.model.Role.STUDENT;
    }
}