package ru.OIStest.phonebook.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.OIStest.phonebook.user.dto.UserToDto;
import ru.OIStest.phonebook.user.model.User;
import ru.OIStest.phonebook.user.dto.UserDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserToDto toDto(User user);

    User toEntity(UserDto userDTO);

    List<UserToDto> userListToDTO(List<User> users);
}
