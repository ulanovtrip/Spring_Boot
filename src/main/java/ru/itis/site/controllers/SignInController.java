package ru.itis.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.PermitAll;

/**
 * 03.07.2021
 * 40. Spring Boot
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Controller
public class SignInController {

    @PermitAll
    @GetMapping("/signIn")
    public String getSignInPage() {
        return "signIn";
    }
}
