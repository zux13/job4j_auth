package ru.job4j.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.job4j.auth.marker.Operation;

@Data
public class PersonDto {

    @Positive(message = "id must be positive", groups = {Operation.OnUpdate.class})
    private int id;

    @NotBlank(message = "login cannot be blank", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    @Size(min = 3, message = "login must be at least 3 characters long", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String login;

    @NotBlank(message = "password cannot be blank", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    @Size(min = 3, message = "password must be at least 3 characters long", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class})
    private String password;
}
