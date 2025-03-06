package com.gas.gasbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testConstructorGeneratesID() {
        // Création d'un utilisateur factice (les valeurs sont arbitraires)
        User user = new User("Alice", "Smith", "alice@example.com", "secret");
        Comment comment = new Comment(user, "parent-123", "Test comment content");

        // L'ID doit être généré automatiquement par le constructeur
        assertNotNull(comment.getID(), "L'ID doit être généré automatiquement");
        assertEquals("parent-123", comment.getParentID());
        assertEquals("Test comment content", comment.getContent());
        assertEquals(user, comment.getWriter());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User("Bob", "Jones", "bob@example.com", "pass123");
        Comment comment = new Comment();

        String expectedId = "fixed-id-001";
        comment.setID(expectedId);
        comment.setParentID("parent-456");
        comment.setContent("Another test comment");
        comment.setWriter(user);

        assertEquals(expectedId, comment.getID());
        assertEquals("parent-456", comment.getParentID());
        assertEquals("Another test comment", comment.getContent());
        assertEquals(user, comment.getWriter());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        // Création d'un utilisateur factice avec un ID fixe pour la prévisibilité
        User user = new User("Carol", "White", "carol@example.com", "mypassword");
        user.setID("user-id-001");
        Comment comment = new Comment(user, "parent-789", "Serialization test comment");
        // Pour ce test, fixons l'ID pour être prévisible
        comment.setID("comment-id-001");

        String json = objectMapper.writeValueAsString(comment);
        // Selon la configuration de Jackson, la propriété peut être "ID" ou "id"
        assertTrue(json.contains("\"ID\":\"comment-id-001\"") || json.contains("\"id\":\"comment-id-001\""),
                "Le JSON doit contenir l'ID généré");
        assertTrue(json.contains("\"parentID\":\"parent-789\""));
        assertTrue(json.contains("\"content\":\"Serialization test comment\""));
        // Vérifie que les informations de l'utilisateur sont présentes
        assertTrue(json.contains("\"name\":\"Carol\""));
        assertTrue(json.contains("\"lastName\":\"White\""));
        assertTrue(json.contains("\"email\":\"carol@example.com\""));
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        // Exemple de JSON représentant un commentaire
        String json = "{" +
                "\"ID\":\"comment-id-002\"," +
                "\"parentID\":\"parent-abc\"," +
                "\"writer\":{" +
                "\"name\":\"David\"," +
                "\"lastName\":\"Brown\"," +
                "\"email\":\"david@example.com\"," +
                "\"password\":\"pass456\"," +
                "\"ID\":\"user-id-002\"" +
                "}," +
                "\"content\":\"Deserialization test comment\"" +
                "}";

        Comment comment = objectMapper.readValue(json, Comment.class);
        assertEquals("comment-id-002", comment.getID());
        assertEquals("parent-abc", comment.getParentID());
        assertEquals("Deserialization test comment", comment.getContent());
        assertNotNull(comment.getWriter());
        assertEquals("David", comment.getWriter().getName());
        assertEquals("Brown", comment.getWriter().getLastName());
        assertEquals("david@example.com", comment.getWriter().getEmail());
        // Selon votre logique, le mot de passe peut être présent dans l'objet
        assertEquals("pass456", comment.getWriter().getPassword());
    }
}
