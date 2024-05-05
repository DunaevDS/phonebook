package ru.OIStest.phonebook.user.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.OIStest.phonebook.user.dto.UserDto;
import ru.OIStest.phonebook.user.dto.UserToDto;
import ru.OIStest.phonebook.user.model.User;
import ru.OIStest.phonebook.user.service.UserService;

import java.util.List;

import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    UserService userService;
    private final UserDto mockUserDto = new UserDto(1L, "User","юзвер");
    private final UserToDto mockUserToDto = new UserToDto(1L, "User","юзвер");
    private final int from = 0;
    private final int to = 10;
    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        when(userService.addUser(any(UserDto.class))).thenReturn(mockUserToDto);

        UserToDto result = userController.addUser(mockUserDto);
        Assertions.assertEquals(mockUserToDto, result);
    }

    @Test
    void testDeleteUser() {
        userController.deleteUser(1L);
        verify(userService).deleteUser(anyLong());
    }

    @Test
    void testFindUsers() {
        when(userService.findUsers(any(List.class), anyInt(), anyInt())).thenReturn(List.of(mockUserToDto));

        List<UserToDto> result = userController.findUsers(List.of(1L), from, to);
        Assertions.assertEquals(List.of(mockUserToDto), result);
    }

    @Test
    void testGetUsersBySearchQuery() {
        when(userService.getUsersBySearchQuery(anyString(), anyInt(), anyInt())).thenReturn(List.of(mockUserToDto));

        List<UserToDto> result = userController.getUsersBySearchQuery("text", from, to);
        Assertions.assertEquals(List.of(mockUserToDto), result);
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(mockUserToDto);

        UserToDto result = userController.updateUser(1L, mockUserDto);
        Assertions.assertEquals(mockUserToDto, result);
    }
}
