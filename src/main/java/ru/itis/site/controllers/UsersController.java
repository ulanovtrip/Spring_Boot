package ru.itis.site.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.site.dto.AccountDto;
import ru.itis.site.forms.SignUpForm;
import ru.itis.site.services.AccountsService;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private AccountsService accountsService;

    @PreAuthorize("hasAuthority('ADMIN')") // позволяет зайти только со статусом ADMIN
    @GetMapping("/users")
    public String getUsersPage(Model model) {
        List<AccountDto> accounts = accountsService.getAll();
        model.addAttribute("accounts", accounts);
        return "users";
    }

    @PreAuthorize("hasAuthority('ADMIN')") // позволяет зайти только со статусом ADMIN
    @PostMapping("/users/{user-id}/ban")
    public String banUser(@PathVariable("user-id") Long userId) {
        accountsService.ban(userId);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')") // позволяет зайти только со статусом ADMIN
    @PostMapping(
            value = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE, // принимаем только json
            produces = MediaType.APPLICATION_JSON_VALUE // создаём только json
    )
    @ResponseBody // т.к. в ответ придёт json, то нужна такая аннотация
    public AccountDto addUser(@RequestBody SignUpForm form) { // т.к. на вход json, то ставим @RequestBody
        return AccountDto.builder()
                .email(form.getEmail())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .build();
    }

    // получить всех пользователей с пагинацией
    // вызвать это можно так: http://localhost:8080/api/users?page=1&size=1
    /*
    Например у нас 4 юзера в БД
    http://localhost:8080/api/users?page=0&size=2 - это выведет первых двух на одной страницу
    http://localhost:8080/api/users?page=1&size=2 - это выведет следующих двух на другой странице
    size= меняет количество выводимых на странице пользователей
     */
    @PreAuthorize("hasAuthority('ADMIN')") // позволяет зайти только со статусом ADMIN
    @GetMapping(
            value = "/api/users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AccountDto> getUsers(
            @RequestParam("page") int page, // номер страницы
            @RequestParam("size") int size // размер страницы
    ) {
        return accountsService.getUsers(page, size);
    }
}
