package com.gas.gasbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor  // Constructeur sans argument pour JPA
@Entity
@Table(name = "comments")
@Schema(description = "Represents user comments left under: other users, projects, or slices")
public class Comment {

    @Id
    @JsonProperty("ID")
    @Setter  // Si vous souhaitez pouvoir setter l'ID (sinon, vous pouvez le générer dans le constructeur)
    @Schema(description = "This field represents a unique identifier for a comment",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String ID;

    @Schema(description = "if the comment is a reply, this field will contain the ID of the parent comment, else it would be empty",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentID;

    // Ici, on suppose que User est également une entité ou un simple objet (attention à la relation)
    @ManyToOne
    @Schema(description = "The user who wrote the comment",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private User writer;

    @Schema(description = "The content of the comment",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    // Constructeur avec argument
    public Comment(User writer, String parentID, String content) {
        // On peut générer l'ID automatiquement
        this.ID = java.util.UUID.randomUUID().toString();
        this.writer = writer;
        this.parentID = parentID;
        this.content = content;
    }
}
