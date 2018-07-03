package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class DoDeleteMealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealService mealService = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        log.debug("redirect to meals");
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect("/topjava/meals");
            return;
        }
        Long idLong;
        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            response.sendRedirect("/topjava/meals");
            return;
        }
        mealService.deleteById(idLong);
        response.sendRedirect("/topjava/meals");
    }
}

