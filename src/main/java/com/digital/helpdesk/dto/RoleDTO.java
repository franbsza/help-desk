package com.digital.helpdesk.dto;

import com.digital.helpdesk.models.Privilege;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;
    private String name;
    private List<Privilege> privileges;
}
