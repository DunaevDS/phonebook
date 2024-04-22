package ru.OIStest.phonebook.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.OIStest.phonebook.user.dto.UserDto;
import ru.OIStest.phonebook.user.dto.UserToDto;
import ru.OIStest.phonebook.user.model.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserToDto userToUserDTO(User user);

    User userDtoToUser(UserDto userDTO);
}
