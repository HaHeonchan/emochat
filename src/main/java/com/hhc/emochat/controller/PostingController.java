package com.hhc.emochat.controller;

import com.hhc.emochat.entity.PostEntity;
import com.hhc.emochat.repository.PostRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PostingController {
    private final PostRepository postRepository;

    @Autowired
    public PostingController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/list")
    String postList(Model model) {
        List<PostEntity> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "list.html";
    }

    @GetMapping("/write")
    String writing() {
        return "write.html";
    }

    @PostMapping("/post")
    String posting(@RequestParam String title,
                   @RequestParam String content
    ) {
        PostEntity post = new PostEntity(title, content, "me");
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
}
