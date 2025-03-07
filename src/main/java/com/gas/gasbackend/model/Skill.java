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
@Schema(description = "Represents a skill possessed by a user")
public class Skill {

    @Id
    @Schema(description = "Unique identifier for the skill", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the skill", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Shape name related to the skill")
    private String shapeName;

    public Skill(String name, String shapeName) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.shapeName = shapeName;
    }

    public String getShapeName() {
        return shapeName;
    }
}
