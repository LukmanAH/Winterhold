package com.winterhold.controller.rest;

import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.customer.CustomerFilterDTO;
import com.winterhold.dto.loan.LoanFilterDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.sevice.implementation.CustomerService;
import com.winterhold.sevice.implementation.LoanService;
import com.winterhold.utility.MapperHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/loans")
public class LoanRestController {
    @Autowired
    LoanService service;

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue="") String customerName,
                                         @RequestParam(defaultValue="") String bookTitle){

        try {
            var queryResult = service.getAll( page, new LoanFilterDTO(bookTitle,customerName));
            return MapperHelper.getResponse(queryResult,200);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSingle(@PathVariable Long id){
        try {
            var queryResult = service.getSingle(id);
            if(queryResult != null){
                return MapperHelper.getResponse(queryResult,200);
            }
            return MapperHelper.getError("Loan not found", 400);
        }catch (Exception exception){
            return MapperHelper.getResponse(exception.getMessage(), 500);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertLoanDTO dto, BindingResult bindingResult){
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertLoanDTO dto, BindingResult bindingResult){
        try {
            if(!bindingResult.hasErrors()){
                if(service.isExist(dto.getId())){
                    var updatedData = service.save(dto);
                    return MapperHelper.getResponse(updatedData, 201);
                }
                return MapperHelper.getError("Author not found", 400);
            }
            return MapperHelper.getErrors(bindingResult.getAllErrors(), 422);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }
}
