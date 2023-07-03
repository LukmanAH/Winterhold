package com.winterhold.validation;


import com.winterhold.sevice.implementation.CategoryService;
import com.winterhold.sevice.implementation.CustomerService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueCustomerMembershipNumberValidator implements ConstraintValidator<UniqueCustomerMembershipNumber, String> {
    @Autowired
    private CustomerService customerService;

    @Override
    public boolean isValid(String membershipNumber, ConstraintValidatorContext context) {
        return !customerService.isExist(membershipNumber);
    }

}
