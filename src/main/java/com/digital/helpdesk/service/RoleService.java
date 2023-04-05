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

    public Optional<Role> findOne(Long id){
        Optional<Role> result = repository.findById(id);
        return result;
    }

    public List<Role> findAll(){
       return repository.findAll();
    }

    public Role create(Role role){
        role.setName(role.getName().toUpperCase());
        repository.save(role);
        return null;
    }

    public Role update(Role role){
        if(repository.existsById(role.getId())){
            return repository.save(role);
        }
        return null;
    }

    public Boolean delete(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
