package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Director;


@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
	List<Director> findAll();
	
    Page<Director> findByName(String name, Pageable pageable);
}
