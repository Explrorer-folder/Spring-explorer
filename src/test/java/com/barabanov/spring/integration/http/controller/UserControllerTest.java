package com.barabanov.spring.integration.http.controller;

import com.barabanov.spring.database.entity.Role;
import com.barabanov.spring.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.barabanov.spring.dto.UserCreateEditDto.Fields.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase
{

    private final MockMvc mockMvc;

    @BeforeEach
    void init()
    {
//        List<GrantedAuthority> roles = Arrays.asList(ADMIN, USER);
//        var testUser = new User("test@gmail.com", "test", roles);
//        var testingAuthenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);
//
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(testingAuthenticationToken);
//        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findAll() throws Exception
    {
        mockMvc.perform(get("/users")
                        .with(user("test@gmail.com").authorities(Role.ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void create() throws Exception
    {
        mockMvc.perform(post("/users")
                .param(username, "test@gmail.com")
                .param(firstname, "test")
                .param(lastname, "tes")
                .param(role, "ADMIN")
                .param(companyId, "1")
                .param(birthDate, "2000-01-01"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

}