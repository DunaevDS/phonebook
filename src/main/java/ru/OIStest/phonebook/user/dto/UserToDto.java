package ru.OIStest.phonebook.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToDto {
    private Long id;
    private String name;
    private String notes;
}
