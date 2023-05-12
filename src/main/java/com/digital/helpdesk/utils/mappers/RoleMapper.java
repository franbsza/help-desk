package com.digital.helpdesk.utils.mappers;

import com.digital.helpdesk.dto.RoleDTO;
import com.digital.helpdesk.models.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {

    public List<RoleDTO> map(List<Role> roles) {
        List<RoleDTO> usersDTO = roles.stream().
                map(o -> new RoleDTO(
                        o.getId(),
                        o.getName(),
                        o.getPrivileges()
                ))
                .collect(Collectors.toList());

        return usersDTO;
    }

    public Role mapToModel(RoleDTO roleDTO){
        return Role.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .privileges(roleDTO.getPrivileges())
                .build();
    }

    public RoleDTO mapToDTO(Role role){
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .privileges(role.getPrivileges())
                .build();
    }
}
