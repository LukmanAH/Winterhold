package com.winterhold.controller.mvc;

import com.basilisk.Basilisk.dto.account.RegisterDTO;
import com.winterhold.sevice.implementation.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService service;

    @GetMapping("/loginForm")
    public String loginForm(){

        return "account/login-form";
    }

    @GetMapping("/registerForm")
    public String registerForm(Model model){
        var dto = new RegisterDTO();
        model.addAttribute("dto", dto);
        return "account/register-form";
    }

    @PostMapping("/submitRegister")
    public String submitRegisater(@Valid @ModelAttribute("dto") RegisterDTO dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){

            return "account/register-form";
        }

        service.registerAccount(dto);
        return "redirect:/account/loginform";
    }

    @RequestMapping(value = "/accessDenied" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String accessDenied(){
            return "account/access-denied";
    }

}
