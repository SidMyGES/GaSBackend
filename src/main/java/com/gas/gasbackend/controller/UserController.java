package com.gas.gasbackend.controller;


import java.util.List;
import java.util.Set;

import com.gas.gasbackend.dto.user.UserCreateDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gas.gasbackend.dto.user.UserDTO;
import com.gas.gasbackend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "This is the endpoint for managing users (pizzaiolos)")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Add a user", description = "Sets a new random ID to the user received and adds it to the database")
    public void createUser(@RequestBody final UserCreateDTO userDTO) {
        userService.addUser(userDTO);
    }

    @GetMapping
    @Operation(summary = "Get users", description = "Retrieves all users of the platform")
    public Set<UserDTO> getUsers(){
        return userService.getAllUserDTOs();
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a user by ID", description = "Returns the user that matches the ID, if none match it returns null")
    public UserDTO getUserByID (@PathVariable("id") String ID) {
        return userService.getUserByID(ID);
    }

    @GetMapping("/by-skills")
    @Operation(summary = "Get users by skills" , description = ("Returns a set of users that have at least on of the skills requested"))
    public Set<UserDTO> getUsersBySkills(@RequestParam List<String> skillIDs) {
        return userService.getUsersDTOsBySkill(skillIDs);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user using its id")
    public void deleteUser(final String userID){
        userService.deleteUser(userID);
    }



}
