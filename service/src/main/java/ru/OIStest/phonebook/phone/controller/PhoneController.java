package ru.OIStest.phonebook.phone.controller;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.dto.PhoneToDto;
import ru.OIStest.phonebook.phone.service.PhoneService;


import java.util.List;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/OIStest/phones")
public class PhoneController {

    private final PhoneService phoneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneToDto addPhone(@Valid @RequestBody PhoneDto phone) {
        log.info("Получен запрос POST /OIStest/phones на добавление нового телефона {} для пользователя", phone.toString());
        return phoneService.addPhone(phone);
    }

    @DeleteMapping(value = "/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhone(@NotNull @PathVariable Long phoneId) {
        log.info(String.format("Получен запрос DELETE /OIStest/phones/{phoneId} = %s на удаление пользователя", phoneId));
        phoneService.deletePhone(phoneId);
    }

    @GetMapping
    public List<PhoneToDto> findPhones(@RequestParam(required = false) List<Long> ids,
                                       @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                       @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info(String.format("Получен запрос GET /OIStest/phones на получение списка телефонов с id = %s, " +
                "начиная с %s, по %s на странице", ids, from, size));
        return phoneService.findPhones(ids, from, size);
    }

    @GetMapping(value = "/{userId}")
    public List<PhoneToDto> findPhonesByUserIds(@RequestParam(required = false) List<Long> userIds,
                                                @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                                @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info(String.format("Получен запрос GET /OIStest/phones/{userId на получение списка пользователей с id = %s, " +
                "начиная с %s, по %s на странице", userIds, from, size));
        return phoneService.findPhonesByUserIds(userIds, from, size);
    }

    @GetMapping("/search")
    public List<PhoneToDto> getPhonesBySearchQuery(@RequestParam String text,
                                                   @RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получен запрос GET /OIStest/phones/search' на поиск телефонов среди номеров и заметок по тексту = {}", text);

        return phoneService.getPhonesBySearchQuery(text, from, size);
    }

    @PatchMapping(value = "/{phoneId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneToDto updatePhone(@NotNull @PathVariable Long phoneId,
                                  @Valid @RequestBody PhoneDto phone) {
        log.info("Получен запрос PATCH /OIStest/phones/{phoneId на обновление данных телефона {}", phone.toString());
        return phoneService.updatePhone(phoneId, phone);
    }
}
