package ru.itis.site.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.site.models.Account;
import ru.itis.site.repositories.AccountsRepository;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Transactional
    @Override
    public boolean confirm(String confirmId) {

        Optional<Account> accountOptional = accountsRepository.findByConfirmId(confirmId);

        // если аккаунт с таким confirm-id есть
        if (accountOptional.isPresent()) {
            // достаём аккаунт из бд
            Account account = accountOptional.get();
            // устанавливаем новое состояние
            account.setState(Account.State.CONFIRMED);
            // сохраняем
            accountsRepository.save(account);
            // и говорим, что всё ок
            return true;
        } else {
            // или не ок
            return false;
        }
    }
}
