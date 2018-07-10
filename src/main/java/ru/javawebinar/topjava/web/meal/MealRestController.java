package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Controller
//@RequestMapping("/meals")
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService mealService;

    @Autowired
    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }

    //      @RequestMapping(value = "/meal/{id}", method = RequestMethod.POST)
    public String processEditForm(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        } catch (DateTimeParseException ex) {
            return "meals";
        }
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), dateTime,
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealService.save(meal, SecurityUtil.authUser());
        return "meals";
    }

    //@RequestMapping(value = "/meal/", method = RequestMethod.DELETE)
    public void delete(int id) {
        log.info("Delete {}", id);
        checkNotFoundWithId(mealService.delete(id, SecurityUtil.authUser()), id);
    }

    public String getAll(HttpServletRequest request) {
        log.info("getAll");
        request.setAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(SecurityUtil.authUser()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
        return "/meals.jsp";
    }

    public List<MealWithExceed> getAllFiltered(String starDate, String endDate, String starTime, String endTime) {
        log.info("getAllFiltered");
        return mealService.getAllFiltered(starDate, endDate, starTime, endTime, SecurityUtil.authUser());
    }

    //  @RequestMapping(value = "/meal/", method = RequestMethod.GET)
    public String create(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        return "/mealForm.jsp";
    }

    //@RequestMapping(value = "/meal/{id}", method = RequestMethod.GET)
    public String update(HttpServletRequest request, int id) {
        Meal meal = mealService.get(id, SecurityUtil.authUser());
        checkNotFoundWithId(meal, id);
        request.setAttribute("meal", meal);
        return "/mealForm.jsp";
    }

    public Meal get(int id) {
        Meal meal = mealService.get(id, SecurityUtil.authUser());
        checkNotFoundWithId(meal, id);
        return meal;
    }

    public String filter(HttpServletRequest request) {
        request.setAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(SecurityUtil.authUser()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
        return "/meals.jsp";
    }
}