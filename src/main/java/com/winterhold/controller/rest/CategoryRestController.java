package com.winterhold.controller.rest;

import com.winterhold.dto.book.BookByCategoryFilterDTO;
import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
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
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name){
        try {
            var queryResult = service.getAll( page, new CategoryFilterDTO(name));
            return MapperHelper.getResponse(queryResult,200);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getSingle(@PathVariable String name){
        try {
            var queryResult = service.getSingle(name);
            if(queryResult != null){
                return MapperHelper.getResponse(queryResult,200);
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

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateCategoryDTO dto, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                return MapperHelper.getErrors(bindingResult.getAllErrors(), 422);
            }

            if(!service.isExist(dto.getName())){
                return MapperHelper.getError("Category not found", 400);
            }
            var updatedData = service.save(dto);
            return MapperHelper.getResponse(updatedData, 201);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete(@PathVariable String name){
        try {
            var totalDependent = service.totalDependentBook(name);
            if (totalDependent == 0){
                service.delete(name);
                return MapperHelper.getResponse( "Successfully deleted category", 200);
            }
            return MapperHelper.getError("Delete failed, Category has links to "+ totalDependent.toString() +" books", 400);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @GetMapping("/{name}/books")
    public ResponseEntity<Object> getAllBooks(@PathVariable String name,
                                           @RequestParam(defaultValue="") String authorName,
                                           @RequestParam(defaultValue="") String title
                                ){
        try {
            if(service.isExist(name)){
                var queryResult = service.getBooksByCategoryName(name, new BookByCategoryFilterDTO(authorName, title));
                return MapperHelper.getResponse(queryResult,200);
            }
            return MapperHelper.getError("Category not found", 400);

        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }

    }

    @GetMapping("/{name}/books/{code}")
    public ResponseEntity<Object> getSingleBook(@PathVariable String name, @PathVariable String code){ //Action Method
        try {
            if(!service.isExist(name)){
                return MapperHelper.getError("Category not found", 400);
            }

            var queryResult = service.getSingleBook(code);
            if(queryResult == null){
                return MapperHelper.getError("Book not found", 400);
            }

            return MapperHelper.getResponse(queryResult,200);
        }catch (Exception exception){
            return MapperHelper.getResponse(exception.getMessage(), 500);
        }

    }

    @PostMapping("/{name}/books")
    public ResponseEntity<Object> postBook(@PathVariable String name, @Valid @RequestBody InsertBookDTO dto, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                return MapperHelper.getErrors(bindingResult.getAllErrors(), 422);
            }

            if(!service.isExist(name)){
                return MapperHelper.getError("Category not found", 400);
            }

            var insertedData = service.saveBook(dto);
            return MapperHelper.getResponse(insertedData, 201);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @PutMapping("/{name}/books")
    public ResponseEntity<Object> putBook(@PathVariable String name, @Valid @RequestBody UpdateBookDTO dto, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                return MapperHelper.getErrors(bindingResult.getAllErrors(), 422);
            }

            if(!service.isExist(name)){
                return MapperHelper.getError("Category not found", 400);
            }

            if(!service.isExistBook(dto.getCode())){
                return MapperHelper.getError("Book not found", 400);
            }

            var updatedData = service.saveBook(dto);
            return MapperHelper.getResponse(updatedData, 201);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }

    @DeleteMapping("/{name}/books/{code}")
    public ResponseEntity<Object> deleteBook(@PathVariable String code){
        try {
            var totalDependent = service.totalDependentLoan(code);

            if(totalDependent==0){
                service.deleteBook(code);
                return MapperHelper.getResponse("Successfully deleted book", 200);
            }
            return MapperHelper.getError("Delete failed, Book has links to "+ totalDependent.toString() +" loan", 400);
        }catch (Exception exception){
            return MapperHelper.getError(exception.getMessage(), 500);
        }
    }


}
