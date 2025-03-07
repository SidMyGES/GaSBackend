package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "skills")
@NoArgsConstructor
@Schema(description = "Represents a particular skill possessed by a user or showcased in a slice/project")
public class Skill {

    @Id
    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the skill", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the skill", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    public Skill(String name, String shapeName) {
        // Génération automatique de l'ID
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.getId());
    }
}
