package ru.OIStest.phonebook.phone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.OIStest.phonebook.exception.NotFoundException;
import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.dto.PhoneToDto;
import ru.OIStest.phonebook.phone.mapper.PhoneMapper;
import ru.OIStest.phonebook.phone.model.Phone;
import ru.OIStest.phonebook.phone.reposiory.PhoneRepository;


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
        if (phoneDto == null) {
            log.error("EmptyObjectException: User is null.");
            throw new NotFoundException("Телефон не предоставлен");
        }
        Phone phone = PhoneMapper.INSTANCE.toEntity(phoneDto);
        log.info("Добавление нового телефона");

        phoneRepository.save(phone);

        log.info("сохранили в базу телефон {}", phone);
        return PhoneMapper.INSTANCE.toDto(phone);
    }
}
