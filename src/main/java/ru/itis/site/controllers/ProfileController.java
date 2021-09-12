package ru.itis.site.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.site.security.details.AccountUserDetails;

/**
 * 08.07.2021
 * 40. Spring Boot
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Controller
public class ProfileController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getProfilePage(Model model, @AuthenticationPrincipal AccountUserDetails userDetails) {
        model.addAttribute("email", userDetails.getUsername());
        return "profile";
    }
}
