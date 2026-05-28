package com.example.warimoney.service;

import org.springframework.stereotype.Service;

import com.example.warimoney.domain.Member;
import com.example.warimoney.domain.Project;
import com.example.warimoney.repository.MemberRepository;
import com.example.warimoney.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final ProjectRepository projectRepository;
	private final MemberRepository memberRepository;
	
	private final ProjectService projectService;

	public void addMember(Long projectId, String memberName) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new IllegalArgumentException("Not found"));
		Member member = new Member();
		member.setMemberName(memberName);
		member.setProject(project);
		memberRepository.save(member);
		
		projectService.updateTimestamp(project);
	}
	
	public void editMember(Long memberId, String memberName) {
		Member member = getMember(memberId);

		member.setMemberName(memberName);
		memberRepository.save(member);

		Project project = member.getProject();
		projectService.updateTimestamp(project);
	}
	
	public void deleteMember(Long memberId) {
		Member member = getMember(memberId);

		// 支払者として使われているかチェック
		if (!member.getExpenses().isEmpty()) {
			throw new IllegalStateException("このメンバーは支払者として使われているため、削除できません。");
		}

		memberRepository.delete(member);

		Project project = member.getProject();
		projectService.updateTimestamp(project);
	}
	
	public Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("Not found"));
	}
		

}
