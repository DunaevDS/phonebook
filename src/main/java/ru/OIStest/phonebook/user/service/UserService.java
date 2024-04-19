package ru.OIStest.phonebook.user.service;

import ru.OIStest.phonebook.user.dto.UserFromDto;
import ru.OIStest.phonebook.user.dto.UserToDto;

import java.util.List;

public interface UserService {
    UserToDto addUser(UserFromDto fromDto);

    List<UserFromDto> findUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long userId);

    UserFromDto updateUser(UserToDto inDto);
}
