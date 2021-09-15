package ru.itis.site.services;

import ru.itis.site.dto.AccountDto;
import ru.itis.site.dto.SearchAccountDto;
import ru.itis.site.forms.SignUpForm;

import java.util.List;

public interface AccountsService {

    List<AccountDto> getAll();

    void ban(Long userId);

    List<AccountDto> getUsers(int page, int size);
}
