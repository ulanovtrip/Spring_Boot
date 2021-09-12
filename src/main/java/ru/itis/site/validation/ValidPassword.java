package ru.itis.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// это кастомная аннотация
@Constraint(validatedBy = PasswordValidator.class) // это значит что её будет проверять PasswordValidator
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) // видна в момент запуска программы
public @interface ValidPassword {

    String message() default "invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
