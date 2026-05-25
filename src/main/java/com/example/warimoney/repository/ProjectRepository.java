package com.example.warimoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.warimoney.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
