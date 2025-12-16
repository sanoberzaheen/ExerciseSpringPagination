package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.entity.Director;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.service.DirectorService;
import com.example.demo.view.CompanyDirectorView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/director")
@RequiredArgsConstructor
@Slf4j
public class DirectorController {

	private final DirectorService directorService;
	private final CompanyRepository companyRepository;

	private final WebClient.Builder webClientBuilder;

	@GetMapping("/search")
	public Page<Director> findDirector(@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
		log.info("Page data");
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		return directorService.findDirector(name, page, size, sort);
	}

	@PostMapping("/create")
	public Director create(@RequestBody Director director) {
		return directorService.create(director);
	}

	@PutMapping("/update/{id}")
	public Director update(@PathVariable Long id, @RequestBody Director director) {
		return directorService.update(id, director);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		directorService.delete(id);
	}

	@GetMapping("/company-directors")
	public List<CompanyDirectorView> getCompanyDirectors() {
		return companyRepository.fetchCompanyDirector();
	}

	@GetMapping("/externalapi-countries")
	public List<Object> getCountries() {
		return webClientBuilder.build().get().uri("/europe").retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						response -> Mono.error(new RuntimeException("Client error from API")))
				.onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("API is down")))
				.bodyToFlux(Object.class).collectList().block();
	}
}
