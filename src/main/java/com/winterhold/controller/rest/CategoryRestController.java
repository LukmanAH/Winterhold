package com.winterhold.controller.rest;

import com.winterhold.dto.book.BookByCategoryFilterDTO;
import com.winterhold.dto.category.CategoryFilterDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.sevice.implementation.CategoryService;
import com.winterhold.utility.MapperHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestController {
    @Autowired
    CategoryService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name){
        try {
            var hasilQuery = service.getAll( page, new CategoryFilterDTO(name));
            return MapperHelper.getResponse(hasilQuery,200);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> get(@PathVariable String name){ //Action Method
        try {
            var hasilQuery = service.getSingle(name);
            if(hasilQuery != null){
                return MapperHelper.getResponse(hasilQuery,200);
            }
            return MapperHelper.getError("Category not found", 400);
        }catch (Exception exception){
            return MapperHelper.getResponse(exception.getMessage(), 500);
        }

    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertCategoryDTO dto, BindingResult bindingResult){
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

    @PutMapping("{name}")
    public ResponseEntity<Object> put(@PathVariable String name, @Valid @RequestBody UpdateCategoryDTO dto, BindingResult bindingResult){
        try {
            if(!bindingResult.hasErrors()){
                if(service.getSingle(name) == null){
                    return MapperHelper.getError("Author not found", 400);
                }
                var updatedData = service.save(dto);
                return MapperHelper.getResponse(updatedData, 201);
            }
            return MapperHelper.getErrors(bindingResult.getAllErrors(), 422);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete(@PathVariable String name){
        try {
            return MapperHelper.getResponse(service.delete(name), 200);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @GetMapping("/{name}/books")
    public ResponseEntity<Object> getBooks(@PathVariable String name,
                                           @RequestParam(defaultValue="") String authorName,
                                           @RequestParam(defaultValue="") String title
                                ){
        try {
            if(service.getSingle(name) == null){
                return MapperHelper.getError("Author not found", 400);
            }
            var hasilQuery = service.getBooksByCategoryName(name, new BookByCategoryFilterDTO(authorName, title));
            return MapperHelper.getResponse(hasilQuery,200);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }

    }


}
