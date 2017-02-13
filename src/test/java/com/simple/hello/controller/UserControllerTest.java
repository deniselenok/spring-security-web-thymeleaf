package com.simple.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.hello.domain.User;
import com.simple.hello.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void addNewUserWebFormTestSuccess() throws Exception {
        User user = User.builder()
                .id(10L)
                .userName("Test")
                .password("TestPass")
                .build();
        when(userService.addNewUser(any())).thenReturn(user);
        mockMvc.perform(
                post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("username", "Test")
                        .param("password", "TestPass"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", is("Test")))
                .andExpect(jsonPath("$.id", is(10)));
        verify(userService, times(1)).addNewUser(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void addNewUserJsonTestSuccess() throws Exception {
        User user = User.builder()
                .id(10L)
                .userName("Test123")
                .password("TestPass123!")
                .build();
        when(userService.addNewUser(any())).thenReturn(user);
        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", is("Test123")))
                .andExpect(jsonPath("$.id", is(10)));
        verify(userService, times(1)).addNewUser(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getListOfUsers() throws Exception {
        User user1 = User.builder()
                .id(10L)
                .userName("Test123")
                .password("TestPass123!")
                .build();
        User user2 = User.builder()
                .id(11L)
                .userName("Test1234")
                .password("TestPass1234!")
                .build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(
                get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("Test123")))
                .andExpect(jsonPath("$[0].id", is(10)))
                .andExpect(jsonPath("$[1].username", is("Test1234")))
                .andExpect(jsonPath("$[1].id", is(11)));
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }
}
