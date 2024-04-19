package ru.OIStest.phonebook.user.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.OIStest.phonebook.user.dto.UserFromDto;
import ru.OIStest.phonebook.user.dto.UserToDto;
import ru.OIStest.phonebook.user.mapper.UserMapper;
import ru.OIStest.phonebook.user.model.User;
import ru.OIStest.phonebook.user.reposiory.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserToDto addUser(UserFromDto UserFromDto) {
        User user = UserMapper.INSTANCE.userDTOToUser(UserFromDto);
        log.info("Добавление нового пользователя");
        userRepository.save(user);
        log.info("сохранили в базу пользователя{}", user);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public List<UserFromDto> findUsers(List<Long> ids, Integer from, Integer size) {
        return List.of();
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public UserFromDto updateUser(UserToDto inDto) {
        return null;
    }
}
