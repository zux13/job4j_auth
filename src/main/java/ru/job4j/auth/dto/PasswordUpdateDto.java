package ru.job4j.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDto {
    @NotBlank(message = "password cannot be blank")
    @Size(min = 3, message = "password must be at least 3 characters long")
    private String password;
}
