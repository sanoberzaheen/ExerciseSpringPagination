package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Company;
import com.example.demo.view.CompanyDirectorView;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query("""
			    SELECT
			        c.name as companyName,
			        c.location as location,
			        d.name as directorName
			    FROM Company c, Director d
			    WHERE c.dirId = d.id
			""")
	List<CompanyDirectorView> fetchCompanyDirector();
}