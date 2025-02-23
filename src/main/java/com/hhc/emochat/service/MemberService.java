package com.hhc.emochat.service;

import com.hhc.emochat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var result = memberRepository.findByUsername(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("계정을 찾을 수 없습니다");
        }

        var user = result.get();
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("user"));

        CustomUser customUser = new CustomUser(user.getUsername(), user.getPassword(), auth);
        customUser.id = user.getId();
        customUser.createdAt = LocalDateTime.now();
        return customUser;
    }

    public class CustomUser extends User {
        public Long id;
        public LocalDateTime createdAt;
        public CustomUser(String username,
                          String password,
                          List<GrantedAuthority> auth) {
            super(username, password, auth);
        }
    }
}
