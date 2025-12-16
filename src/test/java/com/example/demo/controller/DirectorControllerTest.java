package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.repository.CompanyRepository;
import com.example.demo.service.DirectorService;

@WebMvcTest(DirectorController.class)
class DirectorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DirectorService directorService;

	@MockBean
	private CompanyRepository companyRepository;

	@MockBean
	private WebClient.Builder webClientBuilder;

	@Test
	void contextLoads() {
	}

	@Test
	void searchDirector_positiveTest() throws Exception {
		// mockMvc.perform(get("/api/director/search")).andExpect(status().isOk());

		mockMvc.perform(get("/api/director/search")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray());
	}
}
