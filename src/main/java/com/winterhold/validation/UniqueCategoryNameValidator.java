package com.winterhold.validation;


import com.winterhold.dao.CategoryRepository;
import com.winterhold.sevice.implementation.CategoryService;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, String> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !categoryService.isExist(name);
    }

}
