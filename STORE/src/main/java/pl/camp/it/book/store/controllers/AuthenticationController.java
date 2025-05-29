package pl.camp.it.book.store.controllers;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.book.store.exceptions.UserLoginExistException;
import pl.camp.it.book.store.exceptions.UserValidationException;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.services.IAuthenticationService;
import pl.camp.it.book.store.session.SessionObject;
import pl.camp.it.book.store.validators.UserValidator;

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
public class AuthenticationController {
    @Autowired
    IAuthenticationService authenticationService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("sessionObject", this.sessionObject);
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String login, @RequestParam String password) {
        try {
            UserValidator.validateLogin(login);
            UserValidator.validatePassword(password);
        } catch (UserValidationException e) {
            e.printStackTrace();
            return "redirect:/login";
        }
        this.authenticationService.authenticate(login, password);
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        return "redirect:/main";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("sessionObject", this.sessionObject);
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute User user,
                           @RequestParam String password2) {
        try {
            UserValidator.validateRegisterUser(user, password2);
            this.authenticationService.registerUser(user);
        } catch (UserValidationException e) {
            e.printStackTrace();
            this.sessionObject.setInfo(e.getInfo());
            return "redirect:/register";
        } catch (UserLoginExistException e) {
            e.printStackTrace();
            this.sessionObject.setInfo("Login zajęty");
            return "redirect:/register";
        }

        return "redirect:/login";
    }
}

