package com.gas.gasbackend.controller;

import com.gas.gasbackend.model.Slice;
import com.gas.gasbackend.repository.SliceRepository;
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

@WebMvcTest(SliceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SliceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SliceRepository sliceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Méthode utilitaire pour créer un Slice factice
    private Slice createDummySlice() {
        // Création d'une slice avec un nom défini.
        Slice slice = new Slice("Sample Slice");
        // Génère et affecte un ID aléatoire
        slice.setID(UUID.randomUUID().toString());
        // On laisse skills et comments vides par défaut et likes à 0
        return slice;
    }

    @Test
    public void testGetAllSlices() throws Exception {
        Slice slice1 = createDummySlice();
        Slice slice2 = createDummySlice();

        Mockito.when(sliceRepository.findAll()).thenReturn(Arrays.asList(slice1, slice2));

        mockMvc.perform(get("/slices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Vérifie qu'on obtient 2 éléments dans la réponse
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetSliceByIdFound() throws Exception {
        Slice slice = createDummySlice();
        String id = slice.getID();

        Mockito.when(sliceRepository.findById(id)).thenReturn(Optional.of(slice));

        mockMvc.perform(get("/slices/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Vérifie que le champ "id" dans le JSON correspond à l'ID de la slice
                .andExpect(jsonPath("$.ID").value(id))
                .andExpect(jsonPath("$.name").value(slice.getName()))
                .andExpect(jsonPath("$.likes").value(slice.getLikes()));
    }

    @Test
    public void testGetSliceByIdNotFound() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(sliceRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/slices/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateSlice() throws Exception {
        // On prépare une slice sans ID, l'ID sera généré dans le contrôleur
        Slice slice = createDummySlice();
        slice.setID(null);

        Mockito.when(sliceRepository.save(any(Slice.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String json = objectMapper.writeValueAsString(slice);

        mockMvc.perform(post("/slices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                // Vérifie que le JSON retourné contient un "id" non vide
                .andExpect(jsonPath("$.ID").isNotEmpty())
                .andExpect(jsonPath("$.name").value(slice.getName()))
                .andExpect(jsonPath("$.likes").value(slice.getLikes()));
    }

    @Test
    public void testUpdateSlice() throws Exception {
        // Création d'une slice existante
        Slice existingSlice = createDummySlice();
        String id = existingSlice.getID();
        Mockito.when(sliceRepository.findById(id)).thenReturn(Optional.of(existingSlice));

        // Création d'une slice mise à jour avec de nouvelles valeurs
        Slice updatedSlice = createDummySlice();
        updatedSlice.setID(id); // Conserver le même ID
        updatedSlice.setName("Updated Slice Name");
        updatedSlice.setLikes(10);
        // On suppose ici que les ensembles skills et comments sont mis à jour (pour cet exemple, on les laisse vides)
        Mockito.when(sliceRepository.save(any(Slice.class))).thenReturn(updatedSlice);

        String json = objectMapper.writeValueAsString(updatedSlice);

        mockMvc.perform(put("/slices/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ID").value(id))
                .andExpect(jsonPath("$.name").value("Updated Slice Name"))
                .andExpect(jsonPath("$.likes").value(10));
    }

    @Test
    public void testDeleteSliceFound() throws Exception {
        String id = UUID.randomUUID().toString();

        Mockito.when(sliceRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(sliceRepository).deleteById(id);

        mockMvc.perform(delete("/slices/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteSliceNotFound() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.when(sliceRepository.existsById(id)).thenReturn(false);

        mockMvc.perform(delete("/slices/{id}", id))
                .andExpect(status().isNotFound());
    }
}
