package com.gas.gasbackend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO for receiving user information from the front")
public class UserCreateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
