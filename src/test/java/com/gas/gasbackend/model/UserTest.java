package com.gas.gasbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Méthode utilitaire pour créer un Skill factice
    private Skill createDummySkill() {
        Skill skill = new Skill("Java", "Circle");
        skill.setID(UUID.randomUUID().toString());
        return skill;
    }

    // Méthode utilitaire pour créer un Project factice
    private Project createDummyProject() {
        Project project = new Project("Dummy Project");
        project.setID(UUID.randomUUID().toString());
        return project;
    }

    // Méthode utilitaire pour créer un Comment factice
    private Comment createDummyComment() {
        // Création d'un utilisateur factice pour le writer
        User writer = new User("John", "Doe", "john@example.com", "password");
        writer.setID(UUID.randomUUID().toString());
        Comment comment = new Comment(writer, "parent-123", "Test comment content");
        comment.setID(UUID.randomUUID().toString());
        return comment;
    }

    @Test
    public void testConstructorAndDefaults() {
        User user = new User("Alice", "Smith", "alice@example.com", "secret");

        // Vérifie que l'ID est généré automatiquement et que les collections sont initialisées
        assertNotNull(user.getID(), "L'ID doit être généré automatiquement");
        assertEquals("Alice", user.getName());
        assertEquals("Smith", user.getLastName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("secret", user.getPassword());
        assertEquals(0, user.getLikes(), "Les likes doivent être initialisés à 0");
        assertNotNull(user.getSkills());
        assertTrue(user.getSkills().isEmpty());
        assertNotNull(user.getProjects());
        assertTrue(user.getProjects().isEmpty());
        assertNotNull(user.getComments());
        assertTrue(user.getComments().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();
        String expectedId = "fixed-id-001";
        user.setID(expectedId);
        user.setName("Bob");
        user.setLastName("Jones");
        user.setEmail("bob@example.com");
        user.setPassword("pass123");
        user.setLikes(5);

        assertEquals(expectedId, user.getID());
        assertEquals("Bob", user.getName());
        assertEquals("Jones", user.getLastName());
        assertEquals("bob@example.com", user.getEmail());
        assertEquals("pass123", user.getPassword());
        assertEquals(5, user.getLikes());
    }

    @Test
    public void testAddMethods() {
        User user = new User("Charlie", "Brown", "charlie@example.com", "mypassword");

        Skill skill = createDummySkill();
        Project project = createDummyProject();
        Comment comment = createDummyComment();

        user.addSkills(skill);
        user.addProject(project);
        user.addComments(comment);

        assertEquals(1, user.getSkills().size(), "Il doit y avoir 1 skill ajouté");
        assertEquals(1, user.getProjects().size(), "Il doit y avoir 1 project ajouté");
        assertEquals(1, user.getComments().size(), "Il doit y avoir 1 comment ajouté");
        // Vérification bidirectionnelle : le projet doit contenir le user en collaborateur
        assertTrue(project.getCollaborators().contains(user), "Le projet doit contenir l'utilisateur en tant que collaborateur");
    }
/*
    @Test
    public void testJsonSerialization() throws Exception {
        User user = new User("David", "White", "david@example.com", "pwd123");
        user.setID("user-id-001");
        user.setLikes(10);

        // Ajout d'un skill
        Skill skill = createDummySkill();
        skill.setID("skill-id-001");
        user.addSkills(skill);

        // Ajout d'un project
        Project project = createDummyProject();
        project.setID("project-id-001");
        user.addProject(project);

        // Ajout d'un comment
        Comment comment = createDummyComment();
        comment.setID("comment-id-001");
        user.addComments(comment);

        String json = objectMapper.writeValueAsString(user);

        // Vérifie que le JSON contient bien la propriété "ID" telle que définie par @JsonProperty("ID")
        assertTrue(json.contains("\"ID\":\"user-id-001\""),
                "Le JSON doit contenir la propriété 'ID' avec la valeur 'user-id-001'");
        assertTrue(json.contains("\"name\":\"David\""));
        assertTrue(json.contains("\"lastName\":\"White\""));
        assertTrue(json.contains("\"email\":\"david@example.com\""));
        assertTrue(json.contains("\"password\":\"pwd123\""));
        assertTrue(json.contains("\"likes\":10"));
        // Vérification dans les collections, par exemple pour le skill
        assertTrue(json.contains("\"ID\":\"skill-id-001\"") || json.contains("\"id\":\"skill-id-001\""),
                "Le JSON doit contenir l'ID du skill ajouté");
        // Pour le project
        assertTrue(json.contains("\"ID\":\"project-id-001\"") || json.contains("\"id\":\"project-id-001\""),
                "Le JSON doit contenir l'ID du project ajouté");
        // Pour le comment
        assertTrue(json.contains("\"ID\":\"comment-id-001\"") || json.contains("\"id\":\"comment-id-001\""),
                "Le JSON doit contenir l'ID du comment ajouté");
    }
*/
    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{" +
                "\"ID\":\"user-id-002\"," +
                "\"name\":\"Eva\"," +
                "\"lastName\":\"Green\"," +
                "\"email\":\"eva@example.com\"," +
                "\"password\":\"secret456\"," +
                "\"likes\":3," +
                "\"skills\":[]," +
                "\"projects\":[]," +
                "\"comments\":[]" +
                "}";
        User user = objectMapper.readValue(json, User.class);
        assertEquals("user-id-002", user.getID());
        assertEquals("Eva", user.getName());
        assertEquals("Green", user.getLastName());
        assertEquals("eva@example.com", user.getEmail());
        assertEquals("secret456", user.getPassword());
        assertEquals(3, user.getLikes());
        assertNotNull(user.getSkills());
        assertTrue(user.getSkills().isEmpty());
        assertNotNull(user.getProjects());
        assertTrue(user.getProjects().isEmpty());
        assertNotNull(user.getComments());
        assertTrue(user.getComments().isEmpty());
    }
}
