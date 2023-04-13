package com.digital.helpdesk.controllers;

import com.digital.helpdesk.models.Role;
import com.digital.helpdesk.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService service;

    @GetMapping
    public String index(Model model){
        model.addAttribute("roles", service.findAll());
        return "roles/index";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("role", new Role());
        return "roles/create";
    }
    @PostMapping
    public String save(@Valid @ModelAttribute("role") Role role, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/users/new";
        }
        Role roleCreated = service.create(role);
        return "redirect:/roles";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Role role = service.findOne(id).get();
        model.addAttribute("role", role);
        return "roles/edit";
    }

    @PutMapping("/edit")
    public String editRole(@Valid @ModelAttribute("role") Role role, BindingResult bindingResult, Model model){

        Role roleUpdated = service.update(role);

        return "redirect:/roles";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id, Model model){
        service.delete(id);
        return "redirect:/roles";
    }

}
