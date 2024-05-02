package ru.OIStest.phonebook.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

@Value
public class UserDto implements Serializable {
    Long id;

    @NotBlank(message = "имя не может быть пустым")
    String name;

    String notes; // заметки о пользователе
}