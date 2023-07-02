package com.winterhold.controller.rest;


import com.winterhold.dto.author.AuthorFilterDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.sevice.implementation.AuthorService;
import com.winterhold.utility.MapperHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorRestController {

    @Autowired
    AuthorService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name){
        try {
            var queryResult = service.getAll( page, new AuthorFilterDTO(name));
            return MapperHelper.getResponse(queryResult,200);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id){ //Action Method
        try {
            var queryResult = service.getSingle(id);
            if(queryResult != null){
                return MapperHelper.getResponse(queryResult,200);
            }
            return MapperHelper.getError("Author not found", 400);
        }catch (Exception exception){
            return MapperHelper.getResponse(exception.getMessage(), 500);
        }

    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult){
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

    @PutMapping("{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult){
        try {
            if(!bindingResult.hasErrors()){
                if(service.isExist(id)){
                    dto.setId(id);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        try {
            return MapperHelper.getResponse(service.delete(id), 200);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Object> getBooks(@PathVariable Long id){
        try {
            if(service.isExist(id)){
                var queryResult = service.getBooksByAuthorId(id);
                return MapperHelper.getResponse(queryResult,200);
            }
            return MapperHelper.getError("Author not found", 400);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }



}
