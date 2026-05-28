package com.example.warimoney.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.warimoney.domain.Project;
import com.example.warimoney.domain.User;
import com.example.warimoney.repository.ProjectRepository;
import com.example.warimoney.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Project getProject(Long id) {
        return projectRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    public void createProject(Long userId, String projectName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Project project = new Project();
        project.setProjectName(projectName);
        project.setUser(user);

        projectRepository.save(project);
    }

    public void editProject(Long id, String projectName) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        project.setProjectName(projectName);
        project.setUpdatedAt(LocalDateTime.now());

        projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        projectRepository.delete(project);
    }

    public void updateTimestamp(Project project) {
        project.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(project);
    }
}
