package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "skills")
@Schema(description = "Represents a particular skill possessed by a user or showcased in a slice/project")
public class Skill {

    @Id
    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the skill", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the skill", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    public Skill(final String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
