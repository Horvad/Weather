package org.example.server.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.example.server.exceptions.ValidException;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
public @interface NameValidation {
    String message() default "Неверный введено имя";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class NameValidator implements ConstraintValidator<NameValidation, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null)
            throw new ValidException("По имя должно быть введено");
        if(s.isEmpty() || s.isBlank())
            throw new ValidException("По имя должно быть не пустым");
        ;
        if(s.trim().length() < 3 || s.trim().length() > 30)
            throw new ValidException("По имя должно быть содержать от 3 до 30 сиволов");
        return true;
    }
}
