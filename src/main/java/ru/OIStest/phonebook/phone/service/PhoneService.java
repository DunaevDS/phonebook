package ru.OIStest.phonebook.phone.service;

import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.dto.PhoneToDto;

public interface PhoneService {

    PhoneToDto addPhone(PhoneDto phone);
}
