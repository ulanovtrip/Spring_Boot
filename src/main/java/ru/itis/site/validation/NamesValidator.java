package ru.itis.site.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// реализация валидатора для двух полей сразу
public class NamesValidator implements ConstraintValidator<NotSameNames, Object> {

    private String firstNameField;

    private String lastNameField;

    @Override
    public void initialize(NotSameNames constraintAnnotation) {
        // это позволяет сохранить в поля firstNameField и lastNameField название полей
        // то что указали в @NotSameNames(firstNameField = "firstName", lastNameField = "lastName" в SignUpForm
        this.firstNameField = constraintAnnotation.firstNameField();
        this.lastNameField = constraintAnnotation.lastNameField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // тут задача вытащить по названию поля, значение, которое лежит в форме (SignUpForm)
        Object firstNameValue = new BeanWrapperImpl(value).getPropertyValue(firstNameField); // тут вытаскиваем из private String firstName;

        Object lastNameValue = new BeanWrapperImpl(value).getPropertyValue(lastNameField); // из String lastName;

        // сравниваем поля между собой, если одинаковые, то ошибка
        return firstNameValue != null && !firstNameValue.equals(lastNameValue);
    }
}
