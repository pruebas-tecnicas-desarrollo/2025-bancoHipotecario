package com.fsole.bh.infrastructure.adapter.in.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.requestId", notNullValue()),
                        jsonPath("$.data", hasSize(greaterThan(0))),
                        jsonPath("$.data[0].id", is(1)),
                        jsonPath("$.data[0].userId", is(1))
                );
    }

    @Test
    void shouldReturnFirstPost() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.requestId", notNullValue()),
                        jsonPath("$.data.id", is(1)),
                        jsonPath("$.data.userId", is(1)),
                        jsonPath("$.data.title", is("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")),
                        jsonPath("$.data.comments", hasSize(greaterThan(0)))
                );
    }

    @Test
    void shouldDeleteFirstPost() throws Exception {
        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());
    }
}
