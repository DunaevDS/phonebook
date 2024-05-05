package ru.OIStest.phonebook.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.OIStest.phonebook.user.dto.UserDto;
import ru.OIStest.phonebook.user.dto.UserToDto;
import ru.OIStest.phonebook.user.model.User;
import ru.OIStest.phonebook.user.reposiory.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    private final User mockUser = new User(1L,"User", "юзвер");
    private final UserDto mockUserDto = new UserDto(1L, "User","юзвер");
    private final UserToDto mockUserToDto = new UserToDto(1L, "User","юзвер");
    private final int from = 0;
    private final int to = 10;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserToDto result = userServiceImpl.addUser(mockUserDto);
        Assertions.assertEquals(mockUserToDto, result);
    }

    @Test
    void testFindUsers() {
        when(userRepository.findByIdIn(any(List.class), any(Pageable.class))).thenReturn(List.of(mockUser));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(null);

        List<UserToDto> result = userServiceImpl.findUsers(List.of(1L), from, to);
        Assertions.assertEquals(List.of(mockUserToDto), result);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(any(Long.class))).thenReturn(true);

        userServiceImpl.deleteUser(1L);
        verify(userRepository).deleteById(any(Long.class));
    }

    @Test
    void testUpdateUser() {
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(mockUser));

        UserToDto result = userServiceImpl.updateUser(1L, mockUserDto);
        Assertions.assertEquals(mockUserToDto, result);
    }

    @Test
    void testGetUsersBySearchQuery() {
        List<User> mockUserList = new ArrayList<>();
        mockUserList.add(mockUser);
        Page<User> mockUserPage = new PageImpl<>(mockUserList);

        when(userRepository.getUsersBySearchQuery(anyString(), any(Pageable.class))).thenReturn(mockUserPage);

        List<UserToDto> result = userServiceImpl.getUsersBySearchQuery("text", from, to);
        Assertions.assertEquals(List.of(mockUserToDto), result);
    }
}