package com.winterhold.controller.mvc;

import com.winterhold.dto.book.BookByCategoryFilterDTO;
import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    CategoryService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue="") String categoryName,
                        Model model){
        var dataTable = service.getAll(page, new CategoryFilterDTO(categoryName));
        var totalPages = dataTable.getTotalPages();

        model.addAttribute("dataTable", dataTable);
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
    public String insert(@Valid @ModelAttribute("dto") InsertCategoryDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("type", "insert");
            model.addAttribute("dto", dto);
            return "book/upsert-category-form";
        }
        service.save(dto);
        return "redirect:/book/index";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("dto") UpdateCategoryDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("type", "update");
            model.addAttribute("dto", dto);
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

    @GetMapping("/upsertDetailForm")
    public String upsertDetailForm(@RequestParam(required = true) String categoryName,
                                   @RequestParam(required = false) String code,
                                   Model model){

        if (code != null){
            var dto = new UpdateBookDTO(service.getSingleBook(code));
            model.addAttribute("type", "updateDetail");
            model.addAttribute("dto", dto);
        }else{
            var dto = new InsertBookDTO();
            dto.setCategoryName(categoryName);
            model.addAttribute("type", "insertDetail");
            model.addAttribute("dto", dto);
        }

        model.addAttribute("authorDropdown", service.getAuthorDropdownList());

        return "book/upsert-book-form";
    }

    @PostMapping("/insertDetail")
    public String insertDetail(@Valid @ModelAttribute("dto") InsertBookDTO dto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("type", "insertDetail");
            model.addAttribute("dto", dto);
            model.addAttribute("authorDropdown", service.getAuthorDropdownList());
            return "book/upsert-book-form";
        }
        service.saveBook(dto);

        redirectAttributes.addAttribute("name", dto.getCategoryName());
        return "redirect:/book/detail";
    }

    @PostMapping("/updateDetail")
    public String updateDetail(@Valid @ModelAttribute("dto") UpdateBookDTO dto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("type", "updateDetail");
            model.addAttribute("dto", dto);
            model.addAttribute("authorDropdown", service.getAuthorDropdownList());
            return "book/upsert-book-form";
        }
        service.saveBook(dto);

        redirectAttributes.addAttribute("name", dto.getCategoryName());
        return "redirect:/book/detail";
    }
}
