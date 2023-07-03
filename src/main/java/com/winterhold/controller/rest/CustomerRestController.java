package com.winterhold.controller.rest;

import com.winterhold.dto.category.CategoryFilterDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.dto.customer.CustomerFilterDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.sevice.implementation.CustomerService;
import com.winterhold.utility.MapperHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    @Autowired
    CustomerService service;

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue="") String name,
                                         @RequestParam(defaultValue="") String membershipNumber){

        try {
            var queryResult = service.getAll( page, new CustomerFilterDTO(membershipNumber,name));
            return MapperHelper.getResponse(queryResult,200);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @GetMapping("/{membershipNumber}")
    public ResponseEntity<Object> getSingle(@PathVariable String membershipNumber){
        try {
            var queryResult = service.getSingle(membershipNumber);
            if(queryResult != null){
                return MapperHelper.getResponse(queryResult,200);
            }
            return MapperHelper.getError("Customer not found", 400);
        }catch (Exception exception){
            return MapperHelper.getResponse(exception.getMessage(), 500);
        }

    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertCustomerDTO dto, BindingResult bindingResult){
        try {
            if(!bindingResult.hasErrors()){
                var insertedData = service.save(dto);
                return MapperHelper.getResponse(insertedData, 201);
            }
            return MapperHelper.getErrors(bindingResult.getAllErrors(), 422);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }


    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateCustomerDTO dto, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                return MapperHelper.getErrors(bindingResult.getAllErrors(), 422);
            }

            if(!service.isExist(dto.getMembershipNumber())){
                return MapperHelper.getError("Customer not found", 400);
            }
            var updatedData = service.save(dto);
            return MapperHelper.getResponse(updatedData, 201);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @DeleteMapping("/{membershipNumber}")
    public ResponseEntity<Object> delete(@PathVariable String membershipNumber){
        try {
            var totalDependent = service.totalDependentLoan(membershipNumber);
            if(totalDependent==0){
                service.delete(membershipNumber);
                return MapperHelper.getResponse("Successfully deleted customer", 200);
            }
            return MapperHelper.getError("Delete failed, Customer has links to "+ totalDependent.toString() +" loan", 400);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @PutMapping("/{membershipNumber}/extend")
    public ResponseEntity<Object> extend(@PathVariable String membershipNumber){
        try {
            if(!service.isExist(membershipNumber)){
                return MapperHelper.getError("Customer not found", 400);
            }
            var extendedData = service.extendMembership(membershipNumber);
            return MapperHelper.getResponse(extendedData, 201);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }
}
