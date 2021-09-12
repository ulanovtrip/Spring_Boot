package ru.itis.site.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.site.dto.AccountDto;
import ru.itis.site.forms.SignUpForm;
import ru.itis.site.services.AccountsService;

import java.util.List;

/**
 * 14.06.2021
 * 37. MVC
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Controller
public class UsersController {

    @Autowired
    private AccountsService accountsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public String getUsersPage(Model model) {
        List<AccountDto> accounts = accountsService.getAll();
        model.addAttribute("accounts", accounts);
        return "users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users/{user-id}/ban")
    public String banUser(@PathVariable("user-id") Long userId) {
        accountsService.ban(userId);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountDto addUser(@RequestBody SignUpForm form) {
        return AccountDto.builder()
                .email(form.getEmail())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AccountDto> getUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return accountsService.getUsers(page, size);
    }
}
