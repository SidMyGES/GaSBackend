package com.gas.gasbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SkillTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testConstructorGeneratesID() {
        Skill skill = new Skill("Java", "Circle");
        assertNotNull(skill.getID(), "L'ID doit être généré automatiquement");
        assertEquals("Java", skill.getName());
        assertEquals("Circle", skill.getShapeName());
    }

    @Test
    public void testSettersAndGetters() {
        Skill skill = new Skill();
        String expectedId = "fixed-id-001";
        skill.setID(expectedId);
        skill.setName("Spring Boot");
        skill.setShapeName("Square");

        assertEquals(expectedId, skill.getID());
        assertEquals("Spring Boot", skill.getName());
        assertEquals("Square", skill.getShapeName());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        Skill skill = new Skill("Hibernate", "Triangle");
        // Fixer l'ID pour la prévisibilité
        skill.setID("skill-id-001");

        String json = objectMapper.writeValueAsString(skill);
        // Grâce à @JsonProperty("ID"), la propriété devrait s'appeler "ID" dans le JSON
        assertTrue(json.contains("\"ID\":\"skill-id-001\""), "Le JSON doit contenir la propriété ID avec la valeur générée");
        assertTrue(json.contains("\"name\":\"Hibernate\""), "Le JSON doit contenir le nom");
        assertTrue(json.contains("\"shapeName\":\"Triangle\""), "Le JSON doit contenir shapeName");
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{" +
                "\"ID\":\"skill-id-002\"," +
                "\"name\":\"JUnit\"," +
                "\"shapeName\":\"Hexagon\"" +
                "}";
        Skill skill = objectMapper.readValue(json, Skill.class);
        assertEquals("skill-id-002", skill.getID());
        assertEquals("JUnit", skill.getName());
        assertEquals("Hexagon", skill.getShapeName());
    }
}
