package ru.OIStest.phonebook.phone.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import ru.OIStest.phonebook.user.model.User;

import java.io.Serializable;


@Value
public class PhoneDto implements Serializable {
    Long id;
    @NotNull(message = "телефону должно соответстовать имя пользователя")
    String name;
    @NotNull(message = "номер телефона не может быть пустым")
    @Size(min = 6, max = 15)
    String number;
    User user;
    String notes;
}