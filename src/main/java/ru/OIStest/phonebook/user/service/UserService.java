package ru.OIStest.phonebook.user.service;

import ru.OIStest.phonebook.user.dto.UserDto;
import ru.OIStest.phonebook.user.dto.UserToDto;

import java.util.List;

public interface UserService {
    UserToDto addUser(UserDto fromDto);

    List<UserToDto> findUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);

    UserToDto updateUser(Long userId, UserDto userDto);

    UserToDto updateUser(Long userId);
}
