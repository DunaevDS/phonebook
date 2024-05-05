package ru.OIStest.phonebook.phone.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.dto.PhoneToDto;
import ru.OIStest.phonebook.phone.model.Phone;
import ru.OIStest.phonebook.phone.reposiory.PhoneRepository;
import ru.OIStest.phonebook.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PhoneServiceImplTest {
    @Mock
    PhoneRepository phoneRepository;
    private final User mockUser = new User(1L,"User", "тестовый юзвер");
    private final Phone mockPhone = new Phone(1L,"101010",mockUser, "тестовый телефон");
    private final PhoneDto mockPhoneDto = new PhoneDto("101010", mockUser, "тестовый телефон");
    private final PhoneToDto mockPhoneToDto = new PhoneToDto(1L, "101010", "тестовый телефон");
    private final int from = 0;
    private final int to = 10;
    @InjectMocks
    PhoneServiceImpl phoneServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPhone() {
        when(phoneRepository.save(any(Phone.class))).thenReturn(mockPhone);

        PhoneToDto result = phoneServiceImpl.addPhone(mockPhoneDto);
        Assertions.assertEquals(mockPhoneToDto, result);
    }

    @Test
    void testFindPhones() {
        when(phoneRepository.findByIdIn(any(List.class), any(Pageable.class))).thenReturn(List.of(mockPhone));
        when(phoneRepository.findAll(any(Pageable.class))).thenReturn(null);

        List<PhoneToDto> result = phoneServiceImpl.findPhones(List.of(1L), from, to);
        Assertions.assertEquals(List.of(mockPhoneToDto), result);
    }

    @Test
    void testFindPhonesByUserIds() {
        when(phoneRepository.findByUser_IdIn(any(List.class), any(Pageable.class))).thenReturn(List.of(mockPhone));
        when(phoneRepository.findAll(any(Pageable.class))).thenReturn(null);

        List<PhoneToDto> result = phoneServiceImpl.findPhonesByUserIds(List.of(1L), from, to);
        Assertions.assertEquals(List.of(mockPhoneToDto), result);
    }

    @Test
    void testDeletePhone() {
        when(phoneRepository.existsById(any(Long.class))).thenReturn(true);

        phoneServiceImpl.deletePhone(1L);
        verify(phoneRepository).deleteById(any(Long.class));
    }

    @Test
    void testUpdatePhone() {
        when(phoneRepository.save(any(Phone.class))).thenReturn(mockPhone);
        when(phoneRepository.findById(any(Long.class))).thenReturn(Optional.of(mockPhone));

        PhoneToDto result = phoneServiceImpl.updatePhone(1L, mockPhoneDto);
        Assertions.assertEquals(mockPhoneToDto, result);
    }

    @Test
    void testGetPhonesBySearchQuery() {
        List<Phone> mockPhoneList = new ArrayList<>();
        mockPhoneList.add(mockPhone);
        Page<Phone> mockPhonePage = new PageImpl<>(mockPhoneList);

        when(phoneRepository.getPhonesBySearchQuery(anyString(), any(Pageable.class))).thenReturn(mockPhonePage);

        List<PhoneToDto> result = phoneServiceImpl.getPhonesBySearchQuery("text", from, to);
        Assertions.assertEquals(List.of(mockPhoneToDto), result);
    }
}