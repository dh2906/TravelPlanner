package com.example.TravelPlanner.global.annotation.validation;

import com.example.TravelPlanner.global.validation.DateTimeRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateTimeRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateOrTimeRange {
    String message() default "종료 시점은 시작 시점보다 이후여야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
