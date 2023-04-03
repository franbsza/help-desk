package com.digital.helpdesk.service;

import com.digital.helpdesk.models.Role;
import com.digital.helpdesk.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Role findOne(Long id){
        return repository.findById(id).get();
    }

    public List<Role> findAll(){
       return repository.findAll();
    }
    public Role create(Role role){
        repository.save(role);
        return null;
    }

}
