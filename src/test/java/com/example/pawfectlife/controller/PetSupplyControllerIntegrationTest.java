package com.example.pawfectlife.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetSupplyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testGetProducts() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetProductDetail() throws Exception {
        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk());
    }
}