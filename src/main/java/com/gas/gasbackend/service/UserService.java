package com.gas.gasbackend.service;

import com.gas.gasbackend.dto.user.UserDTO;
import com.gas.gasbackend.model.User;
import com.gas.gasbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return Liste de tous les utilisateurs.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id Identifiant de l'utilisateur.
     * @return Optional contenant l'utilisateur si trouvé.
     */
    public Optional<User> getUserById(final String id) {
        return userRepository.findById(id);
    }

    /**
     * Récupère un utilisateur par son email.
     *
     * @param email Email de l'utilisateur.
     * @return Optional contenant l'utilisateur si trouvé.
     */
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Crée un nouvel utilisateur en chiffrant son mot de passe.
     *
     * @param user Utilisateur à créer.
     * @return Utilisateur créé.
     */
    public User createUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Met à jour un utilisateur existant.
     *
     * @param id Identifiant de l'utilisateur.
     * @param updatedUser Données mises à jour de l'utilisateur.
     * @return Optional contenant l'utilisateur mis à jour ou vide s'il n'est pas trouvé.
     */
    public Optional<User> updateUser(final String id, final User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            existingUser.setLikes(updatedUser.getLikes());
            return userRepository.save(existingUser);
        });
    }

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id Identifiant de l'utilisateur.
     */
    public void deleteUser(final String id) {
        userRepository.deleteById(id);
    }

    // ================== Méthodes pour le mapping DTO ==================

    /**
     * Récupère tous les utilisateurs sous forme de DTO.
     *
     * @return Ensemble de UserDTO.
     */
    public Set<UserDTO> getAllUserDTOs() {
        return getAllUsers().stream()
                .map(User::mapUserToDto)
                .collect(Collectors.toSet());
    }

    /**
     * Récupère un utilisateur par son identifiant et le transforme en DTO.
     *
     * @param id Identifiant de l'utilisateur.
     * @return UserDTO correspondant ou null si non trouvé.
     */
    public UserDTO getUserByID(final String id) {
        return getUserById(id)
                .map(User::mapUserToDto)
                .orElse(null);
    }

    /**
     * Récupère les utilisateurs possédant au moins une des compétences demandées.
     *
     * @param skillIds Liste des identifiants de compétences.
     * @return Ensemble de UserDTO correspondant.
     */
    public Set<UserDTO> getUsersDTOsBySkill(final List<String> skillIds) {
        List<User> users = userRepository.findBySkills(skillIds);
        return users.stream()
                .map(User::mapUserToDto)
                .collect(Collectors.toSet());
    }
}
