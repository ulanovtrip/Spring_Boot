package ru.itis.site.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.site.models.Account;
import ru.itis.site.repositories.AccountsRepository;

import java.util.Optional;

/**
 * 08.07.2021
 * 40. Spring Boot
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@Service("accountUserDetailsService")
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountsRepository accountsRepository;

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
