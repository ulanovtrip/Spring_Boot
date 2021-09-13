package ru.itis.site.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.site.security.details.AccountUserDetails;

@Controller
public class ProfileController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    // Model model, @AuthenticationPrincipal AccountUserDetails userDetails - так можно узнать, кто сейчас текущий пользователь
    public String getProfilePage(Model model, @AuthenticationPrincipal AccountUserDetails userDetails) {
        // можно положить в model юзера, это нужно чтобы на форме вывести с помощью <h2>${email}</h2>
        model.addAttribute("email", userDetails.getUsername());
        return "profile";
    }
}
