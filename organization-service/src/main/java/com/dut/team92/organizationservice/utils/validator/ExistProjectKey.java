package com.dut.team92.organizationservice.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistProjectKetValidator.class)
@Documented
public @interface ExistProjectKey {
    String message() default "Project key already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
