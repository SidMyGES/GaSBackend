package com.gas.gasbackend.controller;

import com.gas.gasbackend.model.Comment;
import com.gas.gasbackend.model.User;
import com.gas.gasbackend.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Méthode utilitaire pour créer un Comment factice
    private Comment createDummyComment() {
        // Création d'un utilisateur factice (les valeurs sont arbitraires)
        User user = new User("John", "Doe", "john@example.com", "password");
        Comment comment = new Comment(user, "parent-id", "This is a comment");
        comment.setID(UUID.randomUUID().toString());
        return comment;
    }

    @Test
    public void testGetAllComments() throws Exception {
        Comment comment1 = createDummyComment();
        Comment comment2 = createDummyComment();

        Mockito.when(commentRepository.findAll()).thenReturn(Arrays.asList(comment1, comment2));

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetCommentByIdFound() throws Exception {
        Comment comment = createDummyComment();
        String id = comment.getID();

        Mockito.when(commentRepository.findById(id)).thenReturn(Optional.of(comment));

        mockMvc.perform(get("/comments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ID").value(id))
                .andExpect(jsonPath("$.content").value(comment.getContent()));
    }

    @Test
    public void testGetCommentByIdNotFound() throws Exception {
        String id = UUID.randomUUID().toString();

        Mockito.when(commentRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/comments/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateComment() throws Exception {
        // On prépare un comment sans ID pour simuler la création (l'ID sera généré par le contrôleur)
        Comment comment = createDummyComment();
        comment.setID(null);

        // Lorsque repository.save est appelé, on retourne l'objet avec l'ID généré
        Mockito.when(commentRepository.save(any(Comment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String json = objectMapper.writeValueAsString(comment);

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ID").isNotEmpty())
                .andExpect(jsonPath("$.content").value(comment.getContent()));
    }

    @Test
    public void testUpdateComment() throws Exception {
        // Préparation d'un comment existant
        Comment existingComment = createDummyComment();
        String id = existingComment.getID();

        Mockito.when(commentRepository.findById(id)).thenReturn(Optional.of(existingComment));
        Mockito.when(commentRepository.existsById(id)).thenReturn(true);

        // Préparation d'un comment mis à jour
        Comment updatedComment = createDummyComment();
        updatedComment.setID(id); // Conserver le même ID

        String json = objectMapper.writeValueAsString(updatedComment);

        // Simuler la suppression (pour correspondre à la logique de votre PUT)
        Mockito.doNothing().when(commentRepository).deleteById(eq(id));

        mockMvc.perform(put("/comments/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ID").value(id))
                .andExpect(jsonPath("$.content").value(updatedComment.getContent()));
    }

    @Test
    public void testDeleteCommentFound() throws Exception {
        String id = UUID.randomUUID().toString();

        Mockito.when(commentRepository.existsById(id)).thenReturn(true);
        Mockito.doNothing().when(commentRepository).deleteById(id);

        mockMvc.perform(delete("/comments/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCommentNotFound() throws Exception {
        String id = UUID.randomUUID().toString();

        Mockito.when(commentRepository.existsById(id)).thenReturn(false);

        mockMvc.perform(delete("/comments/{id}", id))
                .andExpect(status().isNotFound());
    }
}
