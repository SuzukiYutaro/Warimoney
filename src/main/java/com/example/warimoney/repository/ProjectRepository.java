package com.example.warimoney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.warimoney.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	@EntityGraph(attributePaths = {"members", "expenses"})
	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Optional<Project> findByIdWithRelations(Long id);

}
