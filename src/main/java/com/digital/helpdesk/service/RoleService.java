package com.digital.helpdesk.service;

import com.digital.helpdesk.dto.RoleDTO;
import com.digital.helpdesk.models.Role;
import com.digital.helpdesk.repository.RoleRepository;
import com.digital.helpdesk.utils.mappers.RoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public RoleDTO findOne(Long id){

        Role role = repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("RoleID: "+ id +" Not Found!"));
        RoleMapper roleMapper = new RoleMapper();
        return roleMapper.mapToDTO(role);
    }

    public RoleDTO create(RoleDTO roleDTO){
        roleDTO.setName(roleDTO.getName().toUpperCase());
        RoleMapper roleMapper = new RoleMapper();
        Role roleCreated = repository.save(roleMapper.mapToModel(roleDTO));
        return roleMapper.mapToDTO(roleCreated);
    }

    public List<RoleDTO> findAll(){
        List<Role> roles = repository.findAll();
        RoleMapper roleMapper = new RoleMapper();
        return roleMapper.map(roles);
    }

    public Boolean delete(Long id) {
        try {
            repository.deleteById(id);
            return true;
        }catch (EntityNotFoundException e){
            return false;
        }
    }

    public RoleDTO update(Long id, RoleDTO roleDTO){
        repository.findById(id).orElseThrow( () ->
                new EntityNotFoundException("RoleID: "+ id +" Not Found!"));

        RoleMapper roleMapper = new RoleMapper();
        roleDTO.setName(roleDTO.getName().toUpperCase());
        Role role = repository.save(roleMapper.mapToModel(roleDTO));
        return roleMapper.mapToDTO(role);
    }
}
