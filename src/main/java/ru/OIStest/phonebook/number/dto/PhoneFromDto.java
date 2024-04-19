package ru.OIStest.phonebook.number.dto;

import jakarta.validation.constraints.NotNull;
import ru.OIStest.phonebook.user.dto.UserFromDto;
import ru.OIStest.phonebook.user.model.User;

public class PhoneFromDto {
    private Long id;
    @NotNull
    private String name; // фио
    @NotNull
    private String number;

    private UserFromDto user;
    private String note;
}
