package com.example.demo.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Director;
import com.example.demo.repository.DirectorRepository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorService {

	private final DirectorRepository directorRepository;

	public Page<Director> findDirector(String name, int page, int size, Sort sort) {
		Pageable pageable = PageRequest.of(page, size, sort);
		if (StringUtils.isBlank(name)) {

			return directorRepository.findAll(pageable);
		} else {
			log.info("Director Name:{}", name);
			return directorRepository.findByName(name, pageable);
		}
	}

	public Director create(Director director) {
		Objects.requireNonNull(director, "Director must not be null");
		director.setId(null); 
		return directorRepository.save(director);
	}

	public Director update(Long id, Director director) {
		Objects.requireNonNull(id, "Id must not be null");

		Director existingDir = directorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Director not found"));

		existingDir.setName(director.getName());
		existingDir.setAge(director.getAge());

		return directorRepository.save(existingDir);
	}

	public void delete(Long id) {
		Objects.requireNonNull(id, "Id must not be null");
		directorRepository.deleteById(id);
	}

}
