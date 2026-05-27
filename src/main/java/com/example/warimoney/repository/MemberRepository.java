package com.example.warimoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.warimoney.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
