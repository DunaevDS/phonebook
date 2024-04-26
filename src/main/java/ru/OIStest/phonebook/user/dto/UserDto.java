package ru.OIStest.phonebook.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import ru.OIStest.phonebook.phone.dto.PhoneDto;

import java.io.Serializable;
import java.util.List;

@Value
public class UserDto implements Serializable {
    @NotBlank(message = "имя не может быть пустым")
    String name;

    List<PhoneDto> phones;
}