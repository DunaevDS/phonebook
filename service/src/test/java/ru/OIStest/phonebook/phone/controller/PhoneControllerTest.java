package ru.OIStest.phonebook.phone.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.dto.PhoneToDto;
import ru.OIStest.phonebook.phone.service.PhoneService;
import ru.OIStest.phonebook.user.model.User;

import java.util.List;

import static org.mockito.Mockito.*;

class PhoneControllerTest {
    @Mock
    PhoneService phoneService;
    private final User mockUser = new User(1L,"User", "юзвер");
    private final PhoneDto mockPhoneDto = new PhoneDto("101010", mockUser, "тестовый телефон");
    private final PhoneToDto mockPhoneToDto = new PhoneToDto(1L, "101010", "тестовый телефон");
    private final int from = 0;
    private final int to = 10;
    @InjectMocks
    PhoneController phoneController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPhone() {
        when(phoneService.addPhone(any(PhoneDto.class))).thenReturn(mockPhoneToDto);

        PhoneToDto result = phoneController.addPhone(mockPhoneDto);
        Assertions.assertEquals(mockPhoneToDto, result);
    }

    @Test
    void testDeletePhone() {
        phoneController.deletePhone(1L);
        verify(phoneService).deletePhone(anyLong());
    }

    @Test
    void testFindPhones() {
        when(phoneService.findPhones(any(List.class), anyInt(), anyInt())).thenReturn(List.of(mockPhoneToDto));

        List<PhoneToDto> result = phoneController.findPhones(List.of(1L), from, to);
        Assertions.assertEquals(List.of(mockPhoneToDto), result);
    }

    @Test
    void testFindPhonesByUserIds() {
        when(phoneService.findPhonesByUserIds(any(List.class), anyInt(), anyInt())).thenReturn(List.of(mockPhoneToDto));

        List<PhoneToDto> result = phoneController.findPhonesByUserIds(List.of(1L), from, to);
        Assertions.assertEquals(List.of(mockPhoneToDto), result);
    }

    @Test
    void testGetPhonesBySearchQuery() {
        when(phoneService.getPhonesBySearchQuery(anyString(), anyInt(), anyInt())).thenReturn(List.of(mockPhoneToDto));

        List<PhoneToDto> result = phoneController.getPhonesBySearchQuery("text", from, to);
        Assertions.assertEquals(List.of(mockPhoneToDto), result);
    }

    @Test
    void testUpdatePhone() {
        when(phoneService.updatePhone(anyLong(), any(PhoneDto.class))).thenReturn(mockPhoneToDto);

        PhoneToDto result = phoneController.updatePhone(1L, new PhoneDto("number", new User(1L, "name", "notes"), "notes"));
        Assertions.assertEquals(mockPhoneToDto, result);
    }
}