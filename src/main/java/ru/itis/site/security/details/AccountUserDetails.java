package ru.itis.site.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.site.models.Account;

import java.util.Collection;
import java.util.Collections;

// UserDetails - этот класс нужен, чтобы подружить spring security и модель Account, т.е. адаптировать
// т.е. AccountUserDetails это описание нашего пользователя в контексте spring
public class AccountUserDetails implements UserDetails {

    private Account account;

    public AccountUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // берём роль аккаунта, преобразуем в строку, так спринг поймёт что за роль
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getRole().name());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return account.getHashPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account.getState().equals(Account.State.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getState().equals(Account.State.CONFIRMED);
    }
}
