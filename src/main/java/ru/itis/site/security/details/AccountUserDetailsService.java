package ru.itis.site.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.site.models.Account;
import ru.itis.site.repositories.AccountsRepository;

import java.util.Optional;

// данный класс определяет как нужно загрузить пользователя откуда-то
@Service("accountUserDetailsService")
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountsRepository accountsRepository;

    // этот метод "учит" spring загружать пользователей из БД по email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Account> accountOptional = accountsRepository.findByEmail(email);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return new AccountUserDetails(account);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
