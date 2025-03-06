package com.gas.gasbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Méthode utilitaire pour créer un Comment factice
    private Comment createDummyComment() {
        User user = new User("John", "Doe", "john@example.com", "password");
        Comment comment = new Comment(user, "parent-comment", "Test comment content");
        comment.setID(UUID.randomUUID().toString());
        return comment;
    }

    // Méthode utilitaire pour créer un Skill factice
    private Skill createDummySkill() {
        Skill skill = new Skill("Java", "Circle");
        skill.setID(UUID.randomUUID().toString());
        return skill;
    }

    // Méthode utilitaire pour créer un User factice
    private User createDummyUser() {
        User user = new User("Alice", "Smith", "alice@example.com", "secret");
        user.setID(UUID.randomUUID().toString());
        return user;
    }

    // Méthode utilitaire pour créer un Slice factice
    private Slice createDummySlice() {
        Slice slice = new Slice("Test Slice");
        slice.setID(UUID.randomUUID().toString());
        return slice;
    }

    @Test
    public void testConstructorAndDefaults() {
        String projectName = "Project A";
        Project project = new Project(projectName);

        // Vérifie que l'ID a bien été généré par le constructeur
        assertNotNull(project.getID(), "L'ID doit être généré automatiquement");
        assertEquals(projectName, project.getName());
        assertEquals(0, project.getLikes(), "Les likes doivent être à 0 par défaut");
        assertTrue(project.getComments().isEmpty(), "Les commentaires doivent être vides");
        assertTrue(project.getSkillsUsed().isEmpty(), "Les skills utilisés doivent être vides");
        assertTrue(project.getCollaborators().isEmpty(), "Les collaborateurs doivent être vides");
        assertTrue(project.getSlices().isEmpty(), "Les slices doivent être vides");
    }

    @Test
    public void testAddMethods() {
        Project project = new Project("Project B");

        // Ajoute des commentaires (appel singulier)
        Comment comment1 = createDummyComment();
        Comment comment2 = createDummyComment();
        project.addComment(comment1);
        project.addComment(comment2);
        assertEquals(2, project.getComments().size(), "Il doit y avoir 2 commentaires");

        // Ajoute un skill utilisé
        Skill skill = createDummySkill();
        project.addSkillUsed(skill);
        assertEquals(1, project.getSkillsUsed().size(), "Il doit y avoir 1 skill utilisé");

        // Ajoute un collaborateur
        User user = createDummyUser();
        project.addCollaborator(user);
        assertEquals(1, project.getCollaborators().size(), "Il doit y avoir 1 collaborateur");
        // Vérification bidirectionnelle : le projet doit apparaître dans les projets du user
        assertTrue(user.getProjects().contains(project), "Le projet doit être présent dans la liste des projets du user");

        // Ajoute une slice
        Slice slice = createDummySlice();
        project.addSlice(slice);
        assertEquals(1, project.getSlices().size(), "Il doit y avoir 1 slice");
    }
/*
    @Test
    public void testJsonSerialization() throws Exception {
        Project project = new Project("Project C");
        project.setLikes(5);

        // Ajout de quelques données factices
        project.addComment(createDummyComment());
        project.addSkillUsed(createDummySkill());
        project.addCollaborator(createDummyUser());
        project.addSlice(createDummySlice());

        String json = objectMapper.writeValueAsString(project);

        // Vérifie que le JSON contient le nom, le nombre de likes et l'ID
        assertTrue(json.contains("\"name\":\"Project C\""), "Le JSON doit contenir le nom du projet");
        assertTrue(json.contains("\"likes\":5"), "Le JSON doit contenir le nombre de likes");
        // L'ID peut apparaître en minuscule (par défaut) ou tel quel selon la configuration Jackson
        assertTrue(json.contains("\"id\"") || json.contains("\"ID\""), "Le JSON doit contenir l'ID du projet");
    }
*/
    @Test
    public void testJsonDeserialization() throws Exception {
        // Exemple de JSON représentant un projet avec des ensembles vides
        String json = "{" +
                "\"ID\":\"proj-12345\"," +
                "\"name\":\"Deserialized Project\"," +
                "\"likes\":7," +
                "\"comments\":[]," +
                "\"skillsUsed\":[]," +
                "\"collaborators\":[]," +
                "\"slices\":[]" +
                "}";
        Project project = objectMapper.readValue(json, Project.class);
        assertEquals("proj-12345", project.getID());
        assertEquals("Deserialized Project", project.getName());
        assertEquals(7, project.getLikes());
        assertNotNull(project.getComments());
        assertTrue(project.getComments().isEmpty());
        assertNotNull(project.getSkillsUsed());
        assertTrue(project.getSkillsUsed().isEmpty());
        assertNotNull(project.getCollaborators());
        assertTrue(project.getCollaborators().isEmpty());
        assertNotNull(project.getSlices());
        assertTrue(project.getSlices().isEmpty());
    }
}
