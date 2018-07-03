package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.slf4j.LoggerFactory.getLogger;

public class DoEditMealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealService mealService = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String version = request.getParameter("version");
        if (id == null || version == null || id.isEmpty() || version.isEmpty()) {
            response.sendRedirect("/topjava/meals");
            return;
        }
        String dateTime = request.getParameter("dateTime");
        if (dateTime == null || dateTime.isEmpty()) {
            response.sendRedirect("/topjava/meals");
            return;
        }
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        if (description == null || calories == null) {
            response.sendRedirect("/topjava/meals");
            return;
        }
        Long idLong;
        Integer versionInt;
        Integer caloriesInt;
        LocalDateTime localDateTime;
        try {
            idLong = Long.parseLong(id);
            versionInt = Integer.parseInt(version);
            caloriesInt = Integer.parseInt(calories);
            localDateTime = LocalDateTime.parse(dateTime);
        } catch (NumberFormatException | DateTimeParseException ex) {
            response.sendRedirect("/topjava/meals");
            return;
        }
        Meal mealUpdated = new Meal(idLong, localDateTime, description, caloriesInt, versionInt);
        mealService.update(mealUpdated);
        response.sendRedirect("/topjava/meals");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}

