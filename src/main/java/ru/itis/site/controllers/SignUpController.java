package ru.itis.site.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.site.forms.SignUpForm;
import ru.itis.site.services.SignUpService;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PermitAll // это регулирует доступ к странице и избаляет от необходимости писать конфиг в SecurityConfig
    @GetMapping("/signUp")
    public String getSignUpPage(Model model) {
        // положим пустую форму и атрибут фримаркера
        model.addAttribute("signUpForm", new SignUpForm());
        return "signUp";
    }

    @PermitAll
    @PostMapping("/signUp")
    // bindingResult - хранит результаты валидации, model - данные которые мы на страницу отдаём
    public String signUpUser(@Valid SignUpForm form, BindingResult bindingResult, Model model) {
        // если есть ошибки
        if (bindingResult.hasErrors()) {
            if (bindingResult.getAllErrors()
                    .stream()
                    .anyMatch(error -> error.getCodes()[0].equals("signUpForm.NotSameNames"))) {
                model.addAttribute("namesError", new Object());
            }
            // кладем эту форму с ошибками обратно на страницу
            model.addAttribute("signUpForm", form);
            // возвращаем страницу
            return "signUp";
        }
        // если все ок
        signUpService.signUp(form);
        return "redirect:/signIn";
    }
}
