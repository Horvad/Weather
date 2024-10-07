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
@Constraint(validatedBy = UuidValidator.class)
public @interface UuidValidation {

    String message() default "Имя должно быть не пустое";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class UuidValidator implements ConstraintValidator<UuidValidation, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!s.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"))
            throw new ValidException("Не верно введен UUID");
        return true;

    }
}
