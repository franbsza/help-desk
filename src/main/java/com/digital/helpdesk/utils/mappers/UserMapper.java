package com.digital.helpdesk.utils.mappers;

import com.digital.helpdesk.models.User;
import com.digital.helpdesk.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public List<UserDTO> map(List<User> users) {
        List<UserDTO> usersDTO = users.stream().
                map(o -> new UserDTO(
                        o.getId(),
                        o.getName(),
                        o.getEmail(),
                        o.getPassword(),
                        o.getLastName(),
                        o.getNickname(),
                        o.isActive(),
                        o.getRoles()
                        ))
                .collect(Collectors.toList());

        return usersDTO;
    }

    public User mapToModel(UserDTO userDTO){
        return User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .lastName(userDTO.getLastName())
                .nickname(userDTO.getNickname())
                .build();
    }

    public UserDTO mapToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .lastName(user.getLastName())
                .nickname(user.getNickname())
                .active(user.isActive())
                .roles(user.getRoles())
                .build();
    }
}
