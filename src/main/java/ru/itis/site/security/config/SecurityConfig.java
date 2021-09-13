package ru.itis.site.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// WebSecurityConfigurerAdapter - тут описаны базовые методы безопасности
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // это и есть доступ к БД
    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier(value = "accountUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // здесь указываем, что используем свои userDetailsService и passwordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    // здесь выполняется конфигурация spring security, это главные метод, здесь разрешается доступ
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/signUp/**").permitAll()
                .antMatchers("users/confirm/**").permitAll()
                .antMatchers("/profile/**").authenticated() // доступ имеет любой аутентифицированный пользователь
                .antMatchers("/users/**").hasAuthority("ADMIN")
                .and()
                .rememberMe().rememberMeParameter("remember-me")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60 * 60 * 24 * 365)
                .and()
                .formLogin() // описание страницы логина
                .loginPage("/signIn") // это будет использовать нашу кастомную страницу вместо встроенной
                .usernameParameter("email") // укажем параметр где смотреть email, так он будет брать его с signIn.ftlh
                .passwordParameter("password")
                .defaultSuccessUrl("/profile") // если вход был успешным, то вернуть эту страницу
                .failureUrl("/signIn?error").permitAll() // если вход был неуспешным, то вернуть такую страницу
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/signIn")
                .deleteCookies("JSESSIONID", "remember-me")
                .invalidateHttpSession(true);

    }

    // этот бин отвечает за сохранение сессии в БД
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // JdbcTokenRepositoryImpl ищет таблицу persistent_logins, которая создаться в schema.sql
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // инжектим доступ к БД, т.е. говорим к какой бд нужно подключаться
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
