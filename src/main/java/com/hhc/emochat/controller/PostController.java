package com.hhc.emochat.controller;

import com.hhc.emochat.entity.PostEntity;
import com.hhc.emochat.repository.PostRepository;
import com.hhc.emochat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    @GetMapping("/list")
    String postList(Model model,
                    Authentication auth
    ) {
        List<PostEntity> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        if(auth != null && auth.isAuthenticated()){
            model.addAttribute("username", auth.getName());
        }else {
            model.addAttribute("username", null);
        }

        return "list.html";
    }

    @GetMapping("/write")
    String writing(Authentication auth) {
        if(!auth.isAuthenticated()){
            return "redirect:/login";
        }
        return "write.html";
    }

    @PostMapping("/post")
    String posting(@RequestParam String title,
                   @RequestParam String content,
                   Authentication auth
    ) {
        PostEntity post = new PostEntity(title, content, auth.getName());
        postRepository.save(post);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(Model model, @PathVariable Long id) {
        Optional<PostEntity> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return "redirect:/error";
        }

        model.addAttribute("post", post.get());
        return "detail.html";
    }

    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable Long id) {
        Optional<PostEntity> post = postRepository.findById(id);
        if (!post.isPresent()) {
            return "redirect:/error";
        }

        model.addAttribute("post", post.get());
        return "edit.html";
    }

    @PostMapping("/edit/{id}")
    String reposting(@RequestParam String title,
                   @RequestParam String content,
                   @PathVariable Long id,
                     Authentication auth
    ) {
        PostEntity post = new PostEntity(title, content, auth.getName());
        post.setId(id);
        postRepository.save(post);
        return "redirect:/list";
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> delete(@RequestBody Map<String, Object> body,
                                  Authentication auth
                                  ){
        if(body.isEmpty()){
            return ResponseEntity.ok().body("not found");
        }
        postRepository.deleteById(Long.parseLong((String) body.get("id")));
        return ResponseEntity.ok().body("deleted");
    }
}
