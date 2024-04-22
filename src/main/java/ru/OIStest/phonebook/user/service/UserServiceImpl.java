package ru.OIStest.phonebook.user.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.OIStest.phonebook.exception.DataValidationException;
import ru.OIStest.phonebook.phone.model.Phone;
import ru.OIStest.phonebook.phone.reposiory.PhoneRepository;
import ru.OIStest.phonebook.user.dto.UserDto;
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
    private final PhoneRepository phoneRepository;

    @Override
    @Transactional
    public UserToDto addUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        log.info("Добавление нового пользователя");
        userRepository.save(user);
        log.info("сохранили в базу пользователя{}", user);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public List<UserDto> findUsers(List<Long> ids, Integer from, Integer size) {
        return List.of();
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new DataValidationException("Пользователя с заданным id не существует");
        }
        userRepository.deleteById(userId);
        log.info("Пользователь с id {} удалён", userId);
    }

    // апдейт пользователя
    @Override
    public UserToDto updateUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        log.info("Обновление данных пользователя {}", user);
        boolean numberExists = false;

        // Перебираем номера, проверяем по бд
        for (Phone phone : user.getPhones()) {
            if (userRepository.existsByPhones_Number(phone.getNumber())) {
                numberExists = true;
                break;
            }
        }
        // номер нашли по базе -> обновляем существующего пользователя
        if (numberExists) {
            user = userRepository.save(user);
            log.info("обновили в базе данные пользователя {}", user);
        } else {
            // Номер не нашли -> сохраняем в бд
            userRepository.save(user);
            log.info("сохранили в базе данные пользователя {}", user);
        }
        return UserMapper.INSTANCE.userToUserDTO(user);
    }
}
