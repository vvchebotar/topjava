package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.web.SecurityUtil.authUser;

@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUser().getId());
    }

    public void delete() {
        super.delete(authUser().getId());
    }

    public void update(User user) {
        super.update(user, authUser().getId());
    }
}