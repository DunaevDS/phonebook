package ru.OIStest.phonebook.user.service;

import ru.OIStest.phonebook.user.dto.UserToDto;
import ru.OIStest.phonebook.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserToDto addUser(UserDto fromDto);

    List<UserToDto> findUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);

    UserToDto updateUser(Long userId, UserDto userDto);

    List<UserToDto> getUsersBySearchQuery(String text, Integer from, Integer size);
}
