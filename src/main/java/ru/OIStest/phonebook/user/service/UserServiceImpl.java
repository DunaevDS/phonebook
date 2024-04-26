package ru.OIStest.phonebook.user.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.OIStest.phonebook.exception.UserNotFoundException;
import ru.OIStest.phonebook.exception.NotFoundException;
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

    //добавление пользователя
    @Override
    @Transactional
    public UserToDto addUser(UserDto userDto) {
        if (userDto == null) {
            log.error("EmptyObjectException: User is null.");
            throw new NotFoundException("Пользователь не предоставлен");
        }
        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        log.info("Добавление нового пользователя");
        userRepository.save(user);
        log.info("сохранили в базу пользователя{}", user);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    //поиск пользователей по айдишникам
    @Override
    public List<UserToDto> findUsers(List<Long> ids, Integer from, Integer size) {
        List<User> users;

        //Пагинация
        Pageable pageRequest = PageRequest.of(from / size, size);
        if (ids == null || ids.isEmpty()) {
            users = userRepository.findAll(pageRequest).getContent();
        } else {
            users = userRepository.findByIdIn(ids, pageRequest);
        }
        log.info("Выполняется запрос на поиск пользователей. Выбранные id: {}", ids);
        return UserMapper.INSTANCE.userListToUserDTO(users);
    }

    //удаление пользователя
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        // проверяем существует ли пользователь с таким id
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("Пользователя с заданным id не существует");
        }
        userRepository.deleteById(userId);
        log.info("Пользователь с id {} удалён", userId);
    }

/*    // апдейт пользователя версия 1
    @Override
    @Transactional
    public UserToDto updateUser(Integer userId, UserDto userDto) {
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
    }*/
// апдейт пользователя версия 2
@Override
@Transactional
public UserToDto updateUser(Long userId, UserDto userDto) {
    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
            "NotFoundException: Пользователь с id= " + userId + " не найден."));
    if (userDto == null) {
        log.error("EmptyObjectException: User is null.");
        throw new NotFoundException("Пользователь не предоставлен");
    }
    return UserMapper.INSTANCE.userToUserDTO(user);
}
}
