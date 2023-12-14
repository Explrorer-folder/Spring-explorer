package com.barabanov.spring.service.service;

import com.barabanov.spring.database.entity.Role;
import com.barabanov.spring.dto.UserCreateEditDto;
import com.barabanov.spring.integration.IntegrationTestBase;
import com.barabanov.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase
{
    private static final Long USER_1 = 1L;
    private static final Integer COMPANY_1 = 1;

    private final UserService userService;


    @Test
    void findAll()
    {
        var result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById()
    {
        var mayBeUser = userService.findById(USER_1);
        assertTrue(mayBeUser.isPresent());
        mayBeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }

    @Test
    void create()
    {
        var userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                null,
                "test",
                "test",
                Role.ADMIN,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])
        );

        var actualResult = userService.create(userDto);
        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        assertEquals(userDto.getLastname(), actualResult.getLastname());
        assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());
        assertSame(userDto.getRole(), actualResult.getRole());
    }

    @Test
    void update()
    {
        var userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                null,
                "test",
                "test",
                Role.ADMIN,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])
        );

        var actualResult = userService.update(USER_1, userDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(userDto.getUsername(), user.getUsername());
            assertEquals(userDto.getBirthDate(), user.getBirthDate());
            assertEquals(userDto.getFirstname(), user.getFirstname());
            assertEquals(userDto.getLastname(), user.getLastname());
            assertEquals(userDto.getCompanyId(), user.getCompany().id());
            assertSame(userDto.getRole(), user.getRole());
        });
    }

    @Test
    void delete()
    {
        assertTrue(userService.delete(USER_1));
        assertFalse(userService.delete(-124L));
    }

}
