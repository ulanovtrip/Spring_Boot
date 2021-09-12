package ru.itis.site.services;

import ru.itis.site.dto.AccountDto;
import ru.itis.site.dto.SearchAccountDto;
import ru.itis.site.forms.SignUpForm;

import java.util.List;

/**
 * 29.05.2021
 * 36. Java Servlet Application
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface AccountsService {

    List<AccountDto> getAll();

    void ban(Long userId);

    List<AccountDto> getUsers(int page, int size);
}
