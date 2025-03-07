package com.gas.gasbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class SliceCreateDTO {

    private String targetId;
    private String projectId;
    private List<String> skillsIds;
}
