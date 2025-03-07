package com.gas.gasbackend.model;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor  // Constructeur sans argument pour JPA
@Entity
@Table(name = "skills")
@Schema(description = "Represents a skill that a user have, a project requires, or a slice presents")
public class Skill {

    @Id
    @Schema(description = "Unique identifier for the skill", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the skill", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "The shape associated with the skill for visual representation",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String shapeName;

    public Skill(String name, String shapeName) {
        // Génération automatique de l'ID
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.shapeName = shapeName;
    }
}
