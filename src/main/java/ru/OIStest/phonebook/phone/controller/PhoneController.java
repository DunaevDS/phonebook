package ru.OIStest.phonebook.phone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.dto.PhoneToDto;
import ru.OIStest.phonebook.phone.service.PhoneService;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/OIStest/phone")
public class PhoneController {

    private final PhoneService phoneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneToDto addPhone(@Valid @RequestBody PhoneDto phone) {
        log.info("Получен запрос POST /OIStest/phones на добавление нового телефона {} для пользователя", phone.toString());
        return phoneService.addPhone(phone);
    }
}
