package com.winterhold.validation;


import com.winterhold.sevice.implementation.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueBookCodeValidator implements ConstraintValidator<UniqueBookCode, String> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return !categoryService.isExistBook(code);
    }

}
