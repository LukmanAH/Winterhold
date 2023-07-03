package com.winterhold.controller.mvc;

import com.winterhold.dto.author.AuthorFilterDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.loan.LoanFilterDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.sevice.implementation.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    LoanService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue="") String bookTitle,
                        @RequestParam(defaultValue="") String customerName,
                        Model model){
        var dataTable = service.getAll(page, new LoanFilterDTO(bookTitle, customerName));
        var totalPages = dataTable.getTotalPages();

        model.addAttribute("dataTable", dataTable);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("customerName", customerName);

        return "loan/loan-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) Long id, Model model){
        var dto = new UpsertLoanDTO();

        if (id != null){
            dto = new UpsertLoanDTO(service.getSingle(id));
        }

        model.addAttribute("dto", dto);
        model.addAttribute("customerDropdown", service.getActiveMemberDropdownList());
        model.addAttribute("bookDropdown", service.getAvailableBookDropdownList());

        return "loan/upsert-loan-form";
    }

    @PostMapping("/saveUpsert")
    public String saveUpsert(@Valid @ModelAttribute("dto") UpsertLoanDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("dto", dto);
            model.addAttribute("customerDropdown", service.getActiveMemberDropdownList());
            model.addAttribute("bookDropdown", service.getAvailableBookDropdownList());

            return "loan/upsert-loan-form";
        }

        service.save(dto);
        return "redirect:/loan/index";
    }

    @GetMapping("/return")
    public String returnBook(@RequestParam(required = true) Long id){
        service.returnBook(id);
        return "redirect:/loan/index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) Long id, Model model){

         model.addAttribute("data", service.getSingle(id));
         return "loan/loan-detail";
    }
}
