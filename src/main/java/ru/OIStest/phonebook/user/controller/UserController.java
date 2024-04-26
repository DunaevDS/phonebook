package ru.OIStest.phonebook.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.OIStest.phonebook.user.dto.UserDto;
import ru.OIStest.phonebook.user.dto.UserToDto;
import ru.OIStest.phonebook.user.service.UserService;


import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/OIStest/users")
public class UserController {
    private static final String USER_ID = "X-Sharer-User-Id";

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserToDto addUser(@Valid @RequestBody UserDto user) {
        log.info("Получен запрос POST /OIStest/users на добавление нового пользователя {}", user.toString());
        return userService.addUser(user);
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@NotNull @PathVariable Long userId) {
        log.info(String.format("Получен запрос DELETE /OIStest/users/{userId} = %s на удаление пользователя", userId));
        userService.deleteUser(userId);
    }

    @GetMapping
    public List<UserToDto> findUsers(@RequestParam(required = false) List<Long> ids,
                                      @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                      @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info(String.format("Получен запрос GET /OIStest/users на получение списка пользователей с id = %s, " +
                "начиная с %s, по %s на странице", ids, from, size));
        return userService.findUsers(ids, from, size);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserToDto updateUser(@RequestHeader(USER_ID) Long userId,
                                @Valid @RequestBody UserDto user) {
        log.info("Получен запрос PUT /OIStest/users на обновление данных пользователя {}", user.toString());
        return userService.updateUser(userId,user);
    }
}
