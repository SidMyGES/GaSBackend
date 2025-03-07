package com.gas.gasbackend.dto;

import com.gas.gasbackend.dto.user.UserDTO;
import com.gas.gasbackend.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
public class SliceDTO {
    private String id;
    private String name;
    private Set<Skill> skills;
    private UserDTO sourceUser;
    private UserDTO targetUser;
    private ProjectDTO project;
}
