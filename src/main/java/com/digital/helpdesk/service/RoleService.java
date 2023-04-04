package com.digital.helpdesk.service;

import com.digital.helpdesk.models.Role;
import com.digital.helpdesk.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        role.setName(role.getName().toUpperCase());
        repository.save(role);
        return null;
    }

    public void delete(Long id) {
        Optional<Role> role = repository.findById(id);
        if(role.isPresent()){
            repository.delete(role.get());
        }
    }
}
