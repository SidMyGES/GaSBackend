package com.gas.gasbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "slices")
@NoArgsConstructor
@Schema(description = "Represents a sub-part ('Slice') of a project showcasing specific skills")
public class Slice {

    @Id
    @Setter(AccessLevel.NONE)
    @Schema(description = "Unique identifier for the slice", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "The name of the slice", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "slice_skills",
            joinColumns = @JoinColumn(name = "slice_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Schema(description = "Skills demonstrated by this slice", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "slice_id")
    @Schema(description = "Comments left by users on this slice")
    private Set<Comment> comments = new HashSet<>();

    @Schema(description = "Number of likes this slice received")
    private int likes;

    /**
     * Constructor for Slice with mandatory name.
     *
     * @param name the name of the slice
     */
    public Slice(final String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.likes = 0;
    }

    /**
     * Adds multiple skills to the slice.
     *
     * @param skills the skills to add
     */
    public void addSkills(final Skill... skills) {
        this.skills.addAll(Arrays.asList(skills));
    }

    /**
     * Adds comments to the slice.
     *
     * @param comments comments to be added
     */
    public void addComments(final Comment... comments) {
        this.comments.addAll(Arrays.asList(comments));
    }
}
