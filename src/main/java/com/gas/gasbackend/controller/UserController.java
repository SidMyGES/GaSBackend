package com.gas.gasbackend.controller;


import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "This is the endpoint for managing users (pizzaiolos)")
public class UserController {

    private final Set<User> users = new HashSet<>();

    @PostMapping
    @Operation(summary = "Add a user", description = "Sets a new random ID to the user received and adds it to the database")
    public User createUser(@RequestBody final User user) {
        user.setID(UUID.randomUUID().toString());
        users.add(user);
        return user;
    }

    @GetMapping
    @Operation(summary = "Get users", description = "Retrieves all users of the platform")
    public Set<User> getUsers(){
        return users;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Returns the user that matches the ID, if none match it returns null")
    public User getUserByID (@PathVariable String ID) {
        return users.stream()
                .filter(user -> user.getID().equals(ID))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("by-skills")
    @Operation(summary = "Get users by skills" , description = ("Returns a set of users that have at least on of the skills requested"))
    public Set<User> getUsersBySkills(@RequestBody Set<String> skillIDs) {
        return users.stream()
                .filter(user -> user.getSkills().stream()
                        .map(Skill::getID)
                        .anyMatch(skillIDs::contains))
                .collect(Collectors.toSet());
    }

}
