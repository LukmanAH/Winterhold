package com.winterhold.controller.mvc;

import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.customer.CustomerContextDTO;
import com.winterhold.dto.customer.CustomerFilterDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.sevice.implementation.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue="") String name,
                        @RequestParam(defaultValue="") String number,
                        Model model){
        var dataTable = service.getAll(page, new CustomerFilterDTO(number, name));
        var totalPages = dataTable.getTotalPages();

        model.addAttribute("dataTable", dataTable);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        model.addAttribute("membershipNumber", number);

        return "customer/customer-index";
    }

    @GetMapping("/context")
    public String context(@RequestParam(required = true) String membershipNumber, Model model){
        var dto = new CustomerContextDTO(service.getSingle(membershipNumber));

        model.addAttribute("dto", dto);
        return "customer/customer-context";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) String membershipNumber, Model model){

        if (membershipNumber != null){
            var dto = new UpdateCustomerDTO(service.getSingle(membershipNumber));
            model.addAttribute("type", "update");
            model.addAttribute("dto", dto);
        }else{
            var dto = new InsertCustomerDTO();
            model.addAttribute("type", "insert");
            model.addAttribute("dto", dto);
        }

        return "customer/upsert-customer-form";
    }


    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("dto") InsertCustomerDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("type", "insert");
            model.addAttribute("dto", dto);
            return "customer/upsert-customer-form";
        }
        dto.setMembershipExpireDate(service.setMembershipExpire());
        service.save(dto);
        return "redirect:/customer/index";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("dto") UpdateCustomerDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("type", "update");
            model.addAttribute("dto", dto);
            return "customer/upsert-customer-form";
        }
        service.save(dto);
        return "redirect:/customer/index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String membershipNumber, Model model){
        var totalDependent = service.totalDependentLoan(membershipNumber);

        if(totalDependent == 0){
            service.delete(membershipNumber);
            return "redirect:/customer/index";
        }

        model.addAttribute("totalDependent", totalDependent);
        model.addAttribute("entity", "Customer");
        model.addAttribute("otherEntity", "Loan");
        model.addAttribute("pageIndex", "index");

        return "delete-validation";
    }

    @GetMapping("/extend")
    public String extendMembership(@RequestParam(required = true) String membershipNumber){
        service.extendMembership(membershipNumber);
        return "redirect:/customer/index";
    }
}
