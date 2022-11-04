package com.dut.team92.userservice.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUsernameValidator.class)
@Documented
public @interface ValidUsername {
    String message() default "Username must be between 6 to 30 characters, " +
            "only contains alphanumeric";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
