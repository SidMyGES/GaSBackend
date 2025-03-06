package com.gas.gasbackend.service;

import com.gas.gasbackend.dto.UserDTO;
import com.gas.gasbackend.model.User;
import com.gas.gasbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addUser(final UserDTO userDTO) {
        final User user = mapDtoToUser(userDTO);
        userRepository.save(user);
    }

    public void deleteUser(final String userID) {
        userRepository.findById(userID).ifPresent(userRepository::delete);
    }

    public UserDTO getUserByID(final String userID) {
        final User user = userRepository.findById(userID).orElse(null);
        assert user != null;
        return mapUserToDto(user);
    }

    public Set<UserDTO> getAllUserDTOs() {
        final List<UserDTO> userDTOs = userRepository.findAll().stream().map(UserService::mapUserToDto).toList();
        return new HashSet<>(userDTOs);
    }

    public Set<UserDTO> getUsersDTOsBySkill(List<String> skillIds) {
        final List<UserDTO> userDTOs = userRepository.findBySkills(skillIds).stream().map(UserService::mapUserToDto).toList();
        return new HashSet<>(userDTOs);
    }

    private static UserDTO mapUserToDto(final User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    private User mapDtoToUser(final UserDTO userDTO){
        return new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                ""
        );
    }
}
