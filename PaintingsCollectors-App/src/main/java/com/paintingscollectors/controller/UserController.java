package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.UserLoginDTO;
import com.paintingscollectors.model.dto.UserRegisterDTO;
import com.paintingscollectors.service.UserService;
import com.paintingscollectors.vallidation.UserValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserSession userSession;
    private UserService userService;
    private UserValidation validation;



    public UserController(UserService userService, UserValidation validation, UserSession userSession) {
        this.userService = userService;
        this.validation = validation;
        this.userSession = userSession;
    }

    //Register:

    @ModelAttribute("registrationData")
    public UserRegisterDTO createUserRegisterDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String viewRegister(Model model) {
        if(userSession.userIsLogged()){
            return "redirect:/home";
        }
        return "/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRegisterDTO userData,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model
    ) {
        if(userSession.userIsLogged()){
            return "redirect:/home";
        }
        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("UsernameMissMatch", validation.usernameIsNotValid(userData.getUsername()));
            redirectAttributes.addFlashAttribute("UserEmailMissMatch", validation.emailIsEmpty(userData.getEmail()));
            redirectAttributes.addFlashAttribute("FieldMissMatch", validation.passwordIsNotValid(userData.getPassword()));
            redirectAttributes.addFlashAttribute("FieldMissMatch", validation.passwordIsNotValid(userData.getConfirmPassword()));

            redirectAttributes.addFlashAttribute("registrationData", userData);
            return "redirect:/register";
        }

        boolean success = userService.registerUser(userData);

        if(!success){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paintingData", bindingResult);
            return "redirect:/register";
        }
        return "redirect:/login";
    }

    //Login:

    @ModelAttribute("loginData")
    public UserLoginDTO createUserLoginDTO() {
        return new UserLoginDTO();
    }


    @GetMapping("/login")
    public String viewLogin(Model model) {
        if(userSession.userIsLogged()){
            return "redirect:/home";
        }
        return "login";
    }


    @PostMapping("/login")
    public String loginUser(@Valid UserLoginDTO userData,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model
    ) {

        if(userSession.userIsLogged()){
            return "redirect:/home";
        }
        if(bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);

            redirectAttributes.addFlashAttribute("loginData", userData);

            return "redirect:/login";
        }

        boolean success = userService.loginUser(userData);

        if(!success){

            model.addAttribute("logError","Invalid username or password");
            redirectAttributes.addFlashAttribute("loginData", userData);
            return "redirect:/login";

        }
        return "redirect:/home";
    }

    //Logout
    @PostMapping("/logout")
    public String logout(){
        userService.logout();

        return "redirect:/";
    }
}

