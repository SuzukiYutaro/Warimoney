package com.example.warimoney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.warimoney.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	// プロジェクトをIDで取得（関連エンティティも一緒に）
	@Query("""
			    SELECT DISTINCT p FROM Project p
			    LEFT JOIN FETCH p.members m
			    LEFT JOIN FETCH p.expenses e
			    LEFT JOIN FETCH e.payer
			    WHERE p.id = :id
			""")
	Optional<Project> findByIdWithRelations(Long id);

}
