package ru.OIStest.phonebook.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.OIStest.phonebook.number.dto.PhoneFromDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFromDto {
    @NotBlank
    @Size(min = 2, max = 150, message = "Длина имени должна быть от 2 до 150")
    private String name;
    private List<PhoneFromDto>phones;
}
