package io.pivotal.management.user.service;

import io.pivotal.management.user.model.User;
import io.pivotal.management.user.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDataSeviceTest {

    UserRepository mockUserRepository;

    SecurityService mockSecurityService;

    UserDataSevice userDataSevice;

    @BeforeEach
    void setUp() {
        mockUserRepository = mock(UserRepository.class);
        mockSecurityService = mock(SecurityService.class);
        userDataSevice = new UserDataSevice(mockUserRepository, mockSecurityService);
    }


    @Test
    void givenUserId_whenUserExists_thenReturnUser() {
        // Given
        String fakeUserId = "1";
        User expectedUser = User.builder()
                .id(fakeUserId)
                .firstname("Joe")
                .lastname("Blow")
                .build();
        Optional<User> expectedOptionalUser = Optional.of(expectedUser);
        when(mockUserRepository.findById(fakeUserId)).thenReturn(expectedOptionalUser);
        // When
        User actualUser = userDataSevice.retrieveUser(fakeUserId);
        // Then
        assertEquals(expectedUser.getFirstname(), actualUser.getFirstname());
    }

    @Test
    void givenRequest_thenRetrieveAllUsers() {
        // Given
        String fakeUserId1 = "1";
        String fakeUserId2 = "2";
        User user1 = User.builder()
                .id(fakeUserId1)
                .password("secret")
                .firstname("Joe")
                .salt("123xyz")
                .lastname("Blow")
                .build();
        User user2 = User.builder()
                .id(fakeUserId2)
                .password("secret2")
                .firstname("Jane")
                .salt("123xyz")
                .lastname("Doe")
                .build();
        List<User> expectedUsers = Lists.list(user1, user2);
        when(mockUserRepository.findAll()).thenReturn(expectedUsers);
        // When
        List<User> actualUsers = userDataSevice.retrieveAllUsers();
        // Then
        assertNotNull(actualUsers);
        assertEquals(2, actualUsers.size());
    }

    @Test
    void givenCountRequest_thenReturnUserCount() {
        // Given
        when(mockUserRepository.count()).thenReturn(5L);
        // When
        long userCount = userDataSevice.getUserCount();
        // Then
        assertEquals(5L, userCount);
    }
}