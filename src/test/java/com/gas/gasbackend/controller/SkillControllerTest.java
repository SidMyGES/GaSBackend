package com.gas.gasbackend.controller;

import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.repository.SkillRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SkillController.class)
public class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillRepository skillRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Méthode utilitaire pour créer un Skill factice
    private Skill createDummySkill() {
        Skill skill = new Skill("Java", "Circle");
        // Génère un ID aléatoire
        skill.setID(UUID.randomUUID().toString());
        return skill;
    }

    @Test
    public void testGetAllSkills() throws Exception {
        Skill skill1 = createDummySkill();
        Skill skill2 = createDummySkill();
        Mockito.when(skillRepository.findAll()).thenReturn(Arrays.asList(skill1, skill2));

        mockMvc.perform(get("/skills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetSkillByIdFound() throws Exception {
        Skill skill = createDummySkill();
        String id = skill.getID();

        Mockito.when(skillRepository.findById(id)).thenReturn(Optional.of(skill));

        mockMvc.perform(get("/skills/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ID").value(id))
                .andExpect(jsonPath("$.name").value(skill.getName()))
                .andExpect(jsonPath("$.shapeName").value(skill.getShapeName()));
    }

    @Test
    public void testGetSkillByIdNotFound() throws Exception {
        String id = UUID.randomUUID().toString();

        Mockito.when(skillRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/skills/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSkill() throws Exception {
        // Création d'un Skill sans ID pour simuler la création (l'ID sera généré dans le contrôleur)
        Skill skill = new Skill("Spring", "Square");
        skill.setID(null);

        Mockito.when(skillRepository.save(any(Skill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String json = objectMapper.writeValueAsString(skill);

        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ID").isNotEmpty())
                .andExpect(jsonPath("$.name").value(skill.getName()))
                .andExpect(jsonPath("$.shapeName").value(skill.getShapeName()));
    }

    @Test
    public void testUpdateSkill() throws Exception {
        Skill existingSkill = createDummySkill();
        String id = existingSkill.getID();

        Mockito.when(skillRepository.findById(id)).thenReturn(Optional.of(existingSkill));

        Skill updatedSkill = new Skill("UpdatedName", "UpdatedShape");
        updatedSkill.setID(id);
        Mockito.when(skillRepository.save(any(Skill.class))).thenReturn(updatedSkill);

        String json = objectMapper.writeValueAsString(updatedSkill);

        mockMvc.perform(put("/skills/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ID").value(id))
                .andExpect(jsonPath("$.name").value("UpdatedName"))
                .andExpect(jsonPath("$.shapeName").value("UpdatedShape"));
    }

    @Test
    public void testDeleteSkillFound() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(skillRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(skillRepository).deleteById(id);

        mockMvc.perform(delete("/skills/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteSkillNotFound() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(skillRepository.existsById(id)).thenReturn(false);

        mockMvc.perform(delete("/skills/{id}", id))
                .andExpect(status().isNotFound());
    }
}
