package com.example.warimoney.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.warimoney.common.AppUserDetails;
import com.example.warimoney.domain.Expense;
import com.example.warimoney.domain.Member;
import com.example.warimoney.domain.Project;
import com.example.warimoney.domain.User;
import com.example.warimoney.repository.ExpenseRepository;
import com.example.warimoney.repository.ProjectRepository;
import com.example.warimoney.repository.UserRepository;
import com.example.warimoney.repository.memberRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final UserRepository userRepository;
	private final ProjectRepository  projectRepository;
	private final memberRepository memberRepository;
	private final ExpenseRepository ExpenseRepository;

    
    @GetMapping("/projects")
    public String user(@AuthenticationPrincipal AppUserDetails userDetails, Model model) {
    	Long userId = userDetails.getUser().getId();
        User user = userRepository.findByIdWithProjects(userId).get();
        model.addAttribute("projects", user.getProjects());
        return "warimoney/projects";
    }
    
    @GetMapping("/projects/{id}")
    public String detail(@PathVariable Long id, Model model) {

    	Project projectDetail = projectRepository.findByIdWithRelations(id)
    	        .orElseThrow(() -> new IllegalArgumentException("Not found"));

        model.addAttribute("project", projectDetail);
        return "warimoney/project_detail";
    }
    
    @PostMapping("/projects/{id}/members")
    public String addMember(
            @PathVariable Long id,
            @RequestParam String memberName) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));

        Member member = new Member();
        member.setMemberName(memberName);
        member.setProject(project);

        memberRepository.save(member);

        return "redirect:/projects/" + id;
    }
    
    @PostMapping("/projects/{projectId}/members/{memberId}/edit")
    public String editMember(
            @PathVariable Long projectId,
            @PathVariable Long memberId,
            @RequestParam String memberName) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));

        member.setMemberName(memberName);
        memberRepository.save(member);

        return "redirect:/projects/" + projectId;
    }
    
    @PostMapping("/projects/{projectId}/members/{memberId}/delete")
    public String deleteMember(
            @PathVariable Long projectId,
            @PathVariable Long memberId,
            RedirectAttributes redirectAttributes) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow();

        // 支払者として使われているかチェック
        if (!member.getExpenses().isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "このメンバーは支払者として使用されています。支払者を変更してから削除してください。");
            return "redirect:/projects/" + projectId;
        }

        memberRepository.delete(member);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/projects/{projectId}/expenses/{expenseId}/edit")
    public String editExpense(
            @PathVariable Long projectId,
            @PathVariable Long expenseId,
            @RequestParam Double amount,
            @RequestParam String description,
            @RequestParam Long payerId) {

        Expense expense = ExpenseRepository.findById(expenseId)
                .orElseThrow();

        Member payer = memberRepository.findById(payerId)
                .orElseThrow();

        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setPayer(payer);

        ExpenseRepository.save(expense);

        return "redirect:/projects/" + projectId;
    }





    
}
