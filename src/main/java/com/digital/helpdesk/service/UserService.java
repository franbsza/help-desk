package com.digital.helpdesk.service;

import com.digital.helpdesk.models.Role;
import com.digital.helpdesk.models.User;
import com.digital.helpdesk.repository.RoleRepository;
import com.digital.helpdesk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findOne(Long id){
        Optional<User> result = repository.findById(id);
        return result;
    }

    public User create(User user){

        Role roleName = roleRepository.findByName("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(roleName)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public User update(User user) {
        if(repository.existsById(user.getId())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return repository.save(user);
        }
        return null;
    }
}
