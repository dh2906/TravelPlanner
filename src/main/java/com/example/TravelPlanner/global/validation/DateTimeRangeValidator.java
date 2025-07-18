package com.example.TravelPlanner.global.validation;

import com.example.TravelPlanner.global.annotation.validation.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeRangeValidator implements ConstraintValidator<ValidDateOrTimeRange, Object> {
    @Override
    public boolean isValid(
        Object o,
        ConstraintValidatorContext constraintValidatorContext
    ) {
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalTime startTime = null;
        LocalTime endTime = null;

        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (field.isAnnotationPresent(StartDate.class)) {
                    startDate = (LocalDate) field.get(o);
                } else if (field.isAnnotationPresent(EndDate.class)) {
                    endDate = (LocalDate) field.get(o);
                } else if (field.isAnnotationPresent(StartTime.class)) {
                    startTime = (LocalTime) field.get(o);
                } else if (field.isAnnotationPresent(EndTime.class)) {
                    endTime = (LocalTime) field.get(o);
                }
            }
        } catch (IllegalAccessException e) {
            return false;
        }

        if (startDate != null && endDate != null) {
            return startDate.isBefore(endDate);
        } else if (startTime != null & endTime != null) {
            return startTime.isBefore(endTime);
        }

        return true;
    }
}
