package ru.OIStest.phonebook.phone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.OIStest.phonebook.exception.DataDuplicateException;
import ru.OIStest.phonebook.exception.NotFoundException;
import ru.OIStest.phonebook.exception.PhoneNotFoundException;
import ru.OIStest.phonebook.exception.UserNotFoundException;
import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.dto.PhoneToDto;
import ru.OIStest.phonebook.phone.mapper.PhoneMapper;
import ru.OIStest.phonebook.phone.model.Phone;
import ru.OIStest.phonebook.phone.reposiory.PhoneRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    //добавление телефона
    @Override
    @Transactional
    public PhoneToDto addPhone(PhoneDto phoneDto) {
        //проверяем входящие данные на 0
        if (phoneDto == null) {
            log.error("EmptyObjectException: User is null.");
            throw new NotFoundException("Телефон не предоставлен");
        }
        Phone phone = PhoneMapper.INSTANCE.toEntity(phoneDto);

        //убираем у входящего номера телефона все знаки, оставляем только цифры
        phone.setNumber(phone.getNumber().replaceAll("\\D+", ""));

        //сохраняем в бд только уникальные номера
        log.info("Добавление нового телефона");
        try {
            Phone savedPhone = phoneRepository.save(phone);
            log.info("сохранили в базу телефон {}", savedPhone);
            return PhoneMapper.INSTANCE.toDto(savedPhone);
        } catch (Exception e) {
            if (e.getMessage().contains("повторяющееся значение ключа")) {
                throw new DataDuplicateException("такой номер телефона уже имеется в бд");
            } else {
                throw e;
            }
        }
    }

    //поиск списка телефонов по заданным id
    @Override
    public List<PhoneToDto> findPhones(List<Long> ids, Integer from, Integer size) {
        List<Phone> phones;

        //Пагинация c сортировкой по id на выходе
        Pageable pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        if (ids == null || ids.isEmpty()) {
            phones = phoneRepository.findAll(pageRequest).getContent();
        } else {
            phones = phoneRepository.findByIdIn(ids, pageRequest);
        }

        log.info("Выполняется запрос на поиск телефонов. Выбранные id: {}", ids);
        return PhoneMapper.INSTANCE.phoneListToDto(phones);
    }

    // поиск списка телефонов по заданным id пользователей
    @Override
    public List<PhoneToDto> findPhonesByUserIds(List<Long> userIds, Integer from, Integer size) {
        List<Phone> phones;

        //Пагинация c сортировкой по id на выходе
        Pageable pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        if (userIds == null || userIds.isEmpty()) {
            phones = phoneRepository.findAll(pageRequest).getContent();
        } else {
            phones = phoneRepository.findByUser_IdIn(userIds, pageRequest);
        }
        log.info("Выполняется запрос на поиск телефонов у пользователей. Выбранные id пользователей: {}", userIds);

        return PhoneMapper.INSTANCE.phoneListToDto(phones);
    }


    @Override
    @Transactional
    public void deletePhone(Long phoneId) {
        //проверяем существует ли телефон в бд
        if (!phoneRepository.existsById(phoneId)) {
            throw new UserNotFoundException("Телефона с заданным id не существует");
        }
        //удаляем телефон
        phoneRepository.deleteById(phoneId);
        log.info("Телефон с id {} удалён", phoneId);
    }

    @Override
    @Transactional
    public PhoneToDto updatePhone(Long phoneId, PhoneDto phoneDto) {
        //проверяем входящие данные на 0
        if (phoneId == null) {
            log.error("EmptyObjectException: Phone is null.");
            throw new NotFoundException("Телефон не предоставлен");
        }
        //достаем телефон по id
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(() -> new PhoneNotFoundException(
                "NotFoundException: Телефон с id = " + phoneId + " не найден."));

        //проверяем входящие данные на 0 и заменяем текущие
        if (phoneDto.getNumber() != null) {
            //убираем у входящего номера телефона все знаки, оставляем только цифры
            phone.setNumber(phoneDto.getNumber().replaceAll("\\D+", ""));
        }
        if (phoneDto.getUser() != null) {
            phone.setUser(phoneDto.getUser());
        }
        if (phoneDto.getNotes() != null) {
            phone.setNotes(phoneDto.getNotes());
        }

        //сохраняем в бд только уникальные номера
        try {
            Phone updatedPhone = phoneRepository.save(phone);
            log.info("сохранили в базу телефон {}", updatedPhone);
            return PhoneMapper.INSTANCE.toDto(updatedPhone);
        } catch (Exception e) {
            if (e.getMessage().contains("повторяющееся значение ключа")) {
                throw new DataDuplicateException("такой номер телефона уже имеется в бд");
            } else {
                throw e;
            }
        }
    }

    //поиск пользователей по ФИО и заметкам
    @Override
    public List<PhoneToDto> getPhonesBySearchQuery(String text, Integer from, Integer size) {
        List<PhoneToDto> listPhoneDto = new ArrayList<>();

        if ((text != null) && (!text.isEmpty()) && (!text.isBlank())) {
            //текст поисковой строки приводим к нижнему регистру
            text = text.toLowerCase();

            Pageable pageable = PageRequest.of(from, size);

            //ищем в БД
            Page<Phone> page = phoneRepository.getPhonesBySearchQuery(text, pageable);

            listPhoneDto = PhoneMapper.INSTANCE.phoneListToDto(page.getContent());

            //обрезаем результат для постраничного отображения
            listPhoneDto = listPhoneDto.stream().limit(size).collect(toList());
        }
        return listPhoneDto;
    }
}
