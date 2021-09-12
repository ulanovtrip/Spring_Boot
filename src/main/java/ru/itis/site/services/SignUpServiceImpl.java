package ru.itis.site.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.site.forms.SignUpForm;
import ru.itis.site.models.Account;
import ru.itis.site.repositories.AccountsRepository;
import ru.itis.site.utils.EmailUtil;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailUtil emailUtil;

    // конфигурация фримаркера
    @Autowired
    // т.к. у спринга есть свой, то нужно поменять название
    @Qualifier("forMailsFreemarkerConfiguration")
    private Configuration configuration;

    @Transactional
    @Override
    public void signUp(SignUpForm form) {
        Account account = Account.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .state(Account.State.NOT_CONFIRMED)
                .confirmId(UUID.randomUUID().toString())
                .build();

        sendConfirmMail(account);
        // сохраним аккаунт
        accountsRepository.save(account);
    }

    private void sendConfirmMail(Account account) {
        try {
            // грузим шаблон письма
            Template template = configuration.getTemplate("mails/confirm_mail.ftlh");

            // теперь в шаблон нужно вставить confirm_id человека
            Map<String, String> attributes = Collections.singletonMap("confirm_id", account.getConfirmId());

            // и отправим всё это на почту
            StringWriter writer = new StringWriter();
            template.process(attributes, writer);
            emailUtil.sendMail(writer.toString(), "Регистрация", account.getEmail());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
