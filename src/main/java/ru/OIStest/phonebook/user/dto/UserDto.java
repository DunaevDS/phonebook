package ru.OIStest.phonebook.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.OIStest.phonebook.phone.dto.PhoneDto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.OIStest.phonebook.user.model.User}
 */
@Value
public class UserDto implements Serializable {
    Long id;
    @NotNull(message = "имя не может быть пустым")
    String name;
    List<PhoneDto> phones;
}