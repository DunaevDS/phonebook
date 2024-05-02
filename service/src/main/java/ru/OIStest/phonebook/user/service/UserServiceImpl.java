package ru.OIStest.phonebook.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.OIStest.phonebook.user.model.User;
import ru.OIStest.phonebook.exception.UserNotFoundException;
import ru.OIStest.phonebook.exception.NotFoundException;
import ru.OIStest.phonebook.user.dto.UserDto;
import ru.OIStest.phonebook.user.dto.UserToDto;
import ru.OIStest.phonebook.user.mapper.UserMapper;
import ru.OIStest.phonebook.user.reposiory.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
        User user = UserMapper.INSTANCE.toEntity(userDto);
        log.info("Добавление нового пользователя");
        userRepository.save(user);
        log.info("сохранили в базу пользователя {}", user);
        return UserMapper.INSTANCE.toDto(user);
    }

    //поиск пользователей по айдишникам
    @Override
    public List<UserToDto> findUsers(List<Long> ids, Integer from, Integer size) {
        List<User> users;

        //Пагинация c сортировкой по id на выходе
        Pageable pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        if (ids == null || ids.isEmpty()) {
            users = userRepository.findAll(pageRequest).getContent();
        } else {
            users = userRepository.findByIdIn(ids, pageRequest);
        }
        log.info("Выполняется запрос на поиск пользователей. Выбранные id: {}", ids);
        return UserMapper.INSTANCE.userListToDTO(users);
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

    // апдейт пользователя
    @Override
    @Transactional
    public UserToDto updateUser(Long userId, UserDto userDto) {
        if (userDto == null) {
            log.error("EmptyObjectException: User is null.");
            throw new NotFoundException("Пользователь не предоставлен");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                "NotFoundException: Пользователь с id= " + userId + " не найден."));

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        userRepository.save(user);

        return UserMapper.INSTANCE.toDto(user);
    }

    //поиск по заметкам и номерам телефона
    @Override
    public List<UserToDto> getUsersBySearchQuery(String text, Integer from, Integer size) {
        List<UserToDto> listUserDto = new ArrayList<>();

        if ((text != null) && (!text.isEmpty()) && (!text.isBlank())) {
            //текст поисковой строки приводим к нижнему регистру
            text = text.toLowerCase();

            Pageable pageable = PageRequest.of(from, size);

            //ищем в БД
            Page<User> page = userRepository.getUsersBySearchQuery(text, pageable);

            listUserDto = UserMapper.INSTANCE.userListToDTO(page.getContent());

            //обрезаем результат для постраничного отображения
            listUserDto = listUserDto.stream().limit(size).collect(toList());
        }
        return listUserDto;
    }
}
