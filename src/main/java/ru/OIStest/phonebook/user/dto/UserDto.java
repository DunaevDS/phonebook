package ru.OIStest.phonebook.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import ru.OIStest.phonebook.phone.dto.PhoneDto;

import java.io.Serializable;
import java.util.List;

@Value
public class UserDto implements Serializable {
    Long id;

    @NotBlank(message = "имя не может быть пустым")
    String name;
}