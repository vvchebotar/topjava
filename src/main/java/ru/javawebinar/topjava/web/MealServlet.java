package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ServletConfig config;
    //private ConfigurableApplicationContext appCtx;// possible make ConfigurableApplicationContext variable
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.config = config;
        ServletContext servletContext = config.getServletContext();
        Object appContext = servletContext.getAttribute("SpringAppContext");
        if (appContext == null) {
            ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
            servletContext.setAttribute("SpringAppContext", appCtx);
            appContext = appCtx;
        }
        if (appContext instanceof ConfigurableApplicationContext) {
            mealRestController = ((ConfigurableApplicationContext) appContext).getBean(MealRestController.class);
        }
    }
    //todo pay attention to order
    @Override
    public void destroy() {
        ConfigurableApplicationContext appContext = (ConfigurableApplicationContext) config.getServletContext().getAttribute("SpringAppContext");
        appContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String responceURI = mealRestController.processEditForm(request);
        response.sendRedirect(responceURI);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.info(request.getRequestURI());
        log.info(request.getQueryString());
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
                request.getRequestDispatcher(mealRestController.create(request)).forward(request, response);
            case "update":
                request.getRequestDispatcher(mealRestController.update(request, getId(request))).forward(request, response);
                break;
            case "filter":
                request.setAttribute("meals",
                        mealRestController.getAllFiltered(
                                request.getParameter("startDate"),
                                request.getParameter("endDate"),
                                request.getParameter("startTime"),
                                request.getParameter("endTime")));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.getRequestDispatcher(mealRestController.getAll(request)).forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
