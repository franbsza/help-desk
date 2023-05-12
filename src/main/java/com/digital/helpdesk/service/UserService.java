package com.digital.helpdesk.service;

import com.digital.helpdesk.models.Role;
import com.digital.helpdesk.models.User;
import com.digital.helpdesk.dto.UserDTO;
import com.digital.helpdesk.repository.RoleRepository;
import com.digital.helpdesk.repository.UserRepository;
import com.digital.helpdesk.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;

    public UserDTO findOne(Long id){

        User user = repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("UserID: "+ id +" Not Found!"));
        UserMapper userMapper = new UserMapper();
        return userMapper.mapToDTO(user);
    }

    public UserDTO create(UserDTO userDTO){
        Role role = roleRepository.findByName("ROLE_USER");

        userDTO.setRoles(Arrays.asList(role));
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

        UserMapper userMapper = new UserMapper();
        User userCreated = repository.save(userMapper.mapToModel(userDTO));
        return userMapper.mapToDTO(userCreated);
    }

    public List<UserDTO> findAll(){
        List<User> users = repository.findAll();
        UserMapper userMapper = new UserMapper();
        return userMapper.map(users);
    }

    public boolean delete(Long id) {
        try {
            repository.deleteById(id);
            return true;
        }catch (EntityNotFoundException e){
            return false;
        }
    }

    public UserDTO update(Long id, UserDTO userDTO) {

        repository.findById(id).orElseThrow( () ->
                        new EntityNotFoundException("UserID: "+ id +" Not Found!"));

        UserMapper userMapper = new UserMapper();
        User user = repository.save(userMapper.mapToModel(userDTO));
        return userMapper.mapToDTO(user);
    }
}
