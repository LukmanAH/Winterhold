package com.winterhold.controller.mvc;


import com.winterhold.dto.author.AuthorFilterDTO;
import com.winterhold.dto.author.AuthorHeaderDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.sevice.implementation.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue="") String name,
                        Model model){
        var pageCollection = service.getAll(page, new AuthorFilterDTO(name));
        var totalPages = pageCollection.getTotalPages();

        model.addAttribute("dataTable", pageCollection);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);

        return "author/author-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) Long id, Model model){
        var dto = new UpsertAuthorDTO();

        if (id != null){
            dto = new UpsertAuthorDTO(service.getSingle(id));
        }

        model.addAttribute("dto", dto);

        return "author/upsert-author-form";
    }

    @PostMapping("/saveUpsert")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpsertAuthorDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "author/upsert-author-form";
        }

        service.save(dto);
        return "redirect:/author/index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model){
        var totalDependent = service.totalDependentBook(id);

        if(totalDependent == 0){
            service.delete(id);
            return "redirect:/author/index";
        }

        model.addAttribute("totalDependent", totalDependent);
        model.addAttribute("entity", "Author");
        model.addAttribute("otherEntity", "Buku");
        model.addAttribute("pageIndex", "index");

        return "delete-validation";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) Long id, Model model){

        var header = new AuthorHeaderDTO(service.getSingle(id));
        var dataTable = service.getBooksByAuthorId(id);


        model.addAttribute("header", header);
        model.addAttribute("dataTable", dataTable);

        return "author/author-books";
    }

}
