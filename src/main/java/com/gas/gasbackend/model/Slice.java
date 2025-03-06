package com.gas.gasbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor  // Nécessaire pour JPA
@Entity
@Table(name = "slices")
@Schema(description = "Represents a 'Slice' of a project, which is a sub-part showcasing specific skills")
public class Slice {

    @Id
    @JsonProperty("ID")
    @Schema(description = "Unique identifier for the slice", accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "The name of the slice", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    // Une slice peut démontrer plusieurs skills et inversement
    @ManyToMany
    @JoinTable(
            name = "slice_skills",
            joinColumns = @JoinColumn(name = "slice_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Schema(description = "The set of skills demonstrated in this slice", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Skill> skills = new HashSet<>();

    // Une slice possède plusieurs comments ; on cascade les opérations pour supprimer les commentaires liés
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "slice_id")  // Ajoute une colonne slice_id dans la table des comments
    @Schema(description = "Comments left by users on this slice")
    private Set<Comment> comments = new HashSet<>();

    @Schema(description = "Number of likes this slice has received")
    private int likes;

    // Constructeur avec argument : on génère automatiquement l'ID
    public Slice(String name) {
        this.ID = UUID.randomUUID().toString();
        this.name = name;
        this.likes = 0;
    }

    // Ajoute des skills à la slice
    public void addSkills(Skill... skills) {
        for (Skill skill : skills) {
            this.skills.add(skill);
        }
    }

    // Ajoute des comments à la slice
    public void addComments(Comment... comments) {
        for (Comment comment : comments) {
            this.comments.add(comment);
        }
    }
}
