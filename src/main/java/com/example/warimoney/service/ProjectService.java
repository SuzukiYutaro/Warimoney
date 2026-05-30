package com.example.warimoney.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.warimoney.domain.Project;
import com.example.warimoney.domain.User;
import com.example.warimoney.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final UserService userService;

	// プロジェクト作成
	public void createProject(Long userId, String projectName) {
		User user = userService.getUser(userId);
		Project project = new Project();
		project.setProjectName(projectName);
		project.setUser(user);

		projectRepository.save(project);
	}

	// プロジェクト編集
	public void editProject(Long projectId, String projectName) {
		Project project = getProject(projectId);
		project.setProjectName(projectName);
		project.setUpdatedAt(LocalDateTime.now());

		projectRepository.save(project);
	}

	// プロジェクト削除
	public void deleteProject(Long projectId) {
		Project project = getProject(projectId);

		projectRepository.delete(project);
	}

	// プロジェクトをIDで取得
	public Project getProject(Long projectId) {

		return projectRepository.findById(projectId)
				.orElseThrow(() -> new IllegalArgumentException("Project not found"));

	}

	// プロジェクトをIDで取得（関連エンティティも一緒に）
	public Project getProjectWithRelations(Long projectId) {
		return projectRepository.findByIdWithRelations(projectId)
				.orElseThrow(() -> new IllegalArgumentException("Project not found"));
	}

	// プロジェクトの更新日時を更新
	public void updateTimestamp(Project project) {
		project.setUpdatedAt(LocalDateTime.now());

		projectRepository.save(project);
	}
}
