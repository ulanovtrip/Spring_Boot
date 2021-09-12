package ru.itis.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NamesValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSameNames {

    String message() default "Names are same";

    String firstNameField();

    String lastNameField();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
