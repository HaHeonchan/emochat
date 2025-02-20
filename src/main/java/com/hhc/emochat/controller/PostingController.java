package com.hhc.emochat.controller;

import com.hhc.emochat.entity.PostEntity;
import com.hhc.emochat.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
