package com.digital.helpdesk.controllers;

import com.digital.helpdesk.models.User;
import com.digital.helpdesk.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("users")
public class UserController {

    private final UserService service;

    @GetMapping
    public String index(Model model){
        model.addAttribute("users", service.findAll());
        return "users/index";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/users/new";
        }
        service.create(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        User user = service.findOne(id).get();
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PutMapping("/edit")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){

        User roleUpdated = service.update(user);

        return "redirect:/users";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id, Model model){
        service.delete(id);
        return "redirect:/users";
    }
}
