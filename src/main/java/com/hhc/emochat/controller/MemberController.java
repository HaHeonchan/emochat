package com.hhc.emochat.controller;

import ch.qos.logback.core.model.Model;
import com.hhc.emochat.entity.MemberEntity;
import com.hhc.emochat.repository.MemberRepository;
import com.hhc.emochat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    final MemberRepository memberRepository;
    final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }
    @PostMapping("/register")
    public String addMember(@RequestParam String username, @RequestParam String password) {

        MemberEntity member = new MemberEntity();
        member.setUsername(username);
        var hash = new BCryptPasswordEncoder().encode(password);
        member.setPassword(hash);
        memberRepository.save(member);

        return "redirect:/login";
    }

    @GetMapping("/mypage")
    public String mypage(Authentication auth, Model model) {
        MemberService.CustomUser user = (MemberService.CustomUser) auth.getPrincipal();
        return "mypage.html";
    }
}
