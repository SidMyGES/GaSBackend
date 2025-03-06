package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Schema(description = "Represents a skill that a user have, a project requires, or a slice presents")
public class Skill {

    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the skill",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "The name of the skill",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final String name;

    @Schema(description = "The shape associated with the skill for visual representation",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final String shapeName;

    public Skill(final String name, final String shapeName) {
        this.name = name;
        this.shapeName = shapeName;
    }
}
