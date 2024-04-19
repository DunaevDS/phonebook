package ru.OIStest.phonebook.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import ru.OIStest.phonebook.number.model.Phone;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToDto {
    private Long id;
    private String name;
    private List<Phone> phones;
}
