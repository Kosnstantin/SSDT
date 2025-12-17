package com.example.sms.filter;

import com.example.sms.controller.LoginController;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Захищаємо всі сторінки (*.xhtml)
@WebFilter(urlPatterns = {"*.xhtml"})
public class AuthFilter implements Filter {

    @Inject
    private LoginController loginController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        // 1. Дозволяємо вхід на сторінку логіну та ресурси (css, js)
        // Також важливо пускати jakarta.faces.resource, інакше стилі не завантажаться
        if (path.contains("/login.xhtml") || path.contains("jakarta.faces.resource")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Якщо користувач залогінений -> ласкаво просимо
        if (loginController != null && loginController.isLoggedIn()) {
            chain.doFilter(request, response);
        } else {
            // 3. Інакше -> геть на сторінку входу
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        }
    }
}