package com.gas.gasbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SliceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Méthode utilitaire pour créer un Skill factice
    private Skill createDummySkill() {
        Skill skill = new Skill("Java", "Circle");
        // Génère un ID aléatoire pour la prévisibilité
        skill.setID(UUID.randomUUID().toString());
        return skill;
    }

    // Méthode utilitaire pour créer un Comment factice
    private Comment createDummyComment() {
        // Création d'un utilisateur factice (valeurs arbitraires)
        User user = new User("John", "Doe", "john@example.com", "password");
        user.setID(UUID.randomUUID().toString());
        Comment comment = new Comment(user, "parent-id", "Test comment content");
        comment.setID(UUID.randomUUID().toString());
        return comment;
    }

    @Test
    public void testConstructorDefaults() {
        String sliceName = "Test Slice";
        Slice slice = new Slice(sliceName);

        // Vérifie que l'ID est généré et que les valeurs par défaut sont correctes
        assertNotNull(slice.getID(), "L'ID doit être généré automatiquement");
        assertEquals(sliceName, slice.getName());
        assertEquals(0, slice.getLikes(), "Les likes doivent être initialisés à 0");
        assertTrue(slice.getSkills().isEmpty(), "L'ensemble skills doit être vide par défaut");
        assertTrue(slice.getComments().isEmpty(), "L'ensemble comments doit être vide par défaut");
    }

    @Test
    public void testAddMethods() {
        Slice slice = new Slice("Test Slice");

        // Test de l'ajout de skills
        Skill skill1 = createDummySkill();
        Skill skill2 = createDummySkill();
        slice.addSkills(skill1, skill2);
        assertEquals(2, slice.getSkills().size(), "Il doit y avoir 2 skills ajoutés");

        // Test de l'ajout de comments
        Comment comment1 = createDummyComment();
        Comment comment2 = createDummyComment();
        slice.addComments(comment1, comment2);
        assertEquals(2, slice.getComments().size(), "Il doit y avoir 2 comments ajoutés");
    }

    @Test
    public void testJsonSerialization() throws Exception {
        Slice slice = new Slice("Serialized Slice");
        // Fixer l'ID pour la prévisibilité dans le test
        slice.setID("slice-id-001");
        slice.setLikes(10);

        // Ajout d'un skill et d'un comment pour tester la sérialisation des collections
        Skill skill = createDummySkill();
        skill.setID("skill-id-001");
        slice.addSkills(skill);

        Comment comment = createDummyComment();
        comment.setID("comment-id-001");
        slice.addComments(comment);

        String json = objectMapper.writeValueAsString(slice);

        // Vérifie que le JSON contient bien les propriétés attendues
        assertTrue(json.contains("\"ID\":\"slice-id-001\""), "Le JSON doit contenir la propriété 'ID' avec la valeur 'slice-id-001'");
        assertTrue(json.contains("\"name\":\"Serialized Slice\""), "Le JSON doit contenir le nom du slice");
        assertTrue(json.contains("\"likes\":10"), "Le JSON doit contenir le nombre de likes");
        // Vérification pour les collections (vérifie la présence d'un skill et d'un comment)
        assertTrue(json.contains("\"ID\":\"skill-id-001\"") || json.contains("\"id\":\"skill-id-001\""), "Le JSON doit contenir l'ID du skill ajouté");
        assertTrue(json.contains("\"ID\":\"comment-id-001\"") || json.contains("\"id\":\"comment-id-001\""), "Le JSON doit contenir l'ID du comment ajouté");
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        // Exemple de JSON représentant un slice avec des ensembles vides
        String json = "{" +
                "\"ID\":\"slice-id-002\"," +
                "\"name\":\"Deserialized Slice\"," +
                "\"likes\":5," +
                "\"skills\":[]," +
                "\"comments\":[]" +
                "}";
        Slice slice = objectMapper.readValue(json, Slice.class);
        assertEquals("slice-id-002", slice.getID());
        assertEquals("Deserialized Slice", slice.getName());
        assertEquals(5, slice.getLikes());
        assertNotNull(slice.getSkills(), "L'ensemble skills ne doit pas être null");
        assertNotNull(slice.getComments(), "L'ensemble comments ne doit pas être null");
    }
}
