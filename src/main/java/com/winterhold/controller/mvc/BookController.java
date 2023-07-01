package com.winterhold.controller.mvc;

import com.winterhold.dto.author.AuthorFilterDTO;
import com.winterhold.dto.author.AuthorHeaderDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.book.BookByCategoryFilterDTO;
import com.winterhold.dto.category.CategoryFilterDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.sevice.implementation.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    CategoryService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue="") String categoryName,
                        Model model){
        var pageCollection = service.getAll(page, new CategoryFilterDTO(categoryName));
        var totalPages = pageCollection.getTotalPages();

        model.addAttribute("dataTable", pageCollection);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("categoryName", categoryName);

        return "book/book-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) String name, Model model){

        if (name != null){
            var dto = new UpdateCategoryDTO(service.getSingle(name));
            model.addAttribute("type", "update");
            model.addAttribute("dto", dto);
        }else{
            var dto = new InsertCategoryDTO();
            model.addAttribute("type", "insert");
            model.addAttribute("dto", dto);
        }

        return "book/upsert-category-form";
    }

    @PostMapping("/insert")
    public String saveUpsert(@Valid @ModelAttribute("dto") InsertCategoryDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "book/upsert-category-form";
        }
        service.save(dto);
        return "redirect:/book/index";
    }

    @PostMapping("/update")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpdateCategoryDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "book/upsert-category-form";
        }
        service.save(dto);
        return "redirect:/book/index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String name, Model model){
        var totalDependent = service.totalDependentBook(name);

        if(totalDependent == 0){
            service.delete(name);
            return "redirect:/book/index";
        }

        model.addAttribute("totalDependent", totalDependent);
        model.addAttribute("entity", "Kategori");
        model.addAttribute("otherEntity", "Buku");
        model.addAttribute("pageIndex", "index");

        return "delete-validation";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) String name,
                         @RequestParam(defaultValue="") String title,
                         @RequestParam(defaultValue="") String author,
                         Model model){

        var dataTable = service.getBooksByCategoryName(name,new BookByCategoryFilterDTO(author, title));


        model.addAttribute("dataTable", dataTable);
        model.addAttribute("name", name);
        model.addAttribute("author", author);
        model.addAttribute("title", title);

        return "book/category-books";
    }
}
