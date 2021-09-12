package ru.itis.site.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class LocalizationConfig implements WebMvcConfigurer {

    // говорит, как правильно валидировать коды ошибок
    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
        codesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
        return codesResolver;
    }

    // регистрируем наш перехватчик локализации
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    // нам же нужно запомнить, какой язык выбрал пользователь?
    // это определитель, какая сейчас стоит локаль
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("locale");
        // запомнит куку на год
        localeResolver.setCookieMaxAge(60 * 60 * 24 * 365);
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    // отслеживает, когда нужно сменить язык
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // сменить язык нужно тогда, когда направляется запрос с параметром GET /signUp?lang=ru
        interceptor.setParamName("lang");
        return interceptor;
    }

    // бин, который указывает, где брать файлы с локализацией
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    // объявление бина самого валидатора, который валидирует
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        // здесь указываем валидатору откуда доставать коды ошибок
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
