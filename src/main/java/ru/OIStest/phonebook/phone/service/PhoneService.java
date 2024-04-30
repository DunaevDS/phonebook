package ru.OIStest.phonebook.phone.service;

import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.dto.PhoneToDto;

import java.util.List;

public interface PhoneService {

    PhoneToDto addPhone(PhoneDto phone);

    List<PhoneToDto> findPhones(List<Long> ids, Integer from, Integer size);

    void deletePhone(Long phoneId);

    PhoneToDto updatePhone(Long phoneId, PhoneDto phoneDto);

    List<PhoneToDto> findPhonesByUserIds(List<Long> userIds, Integer from, Integer size);

    List<PhoneToDto> getPhonesBySearchQuery(String text, Integer from, Integer size);
}
