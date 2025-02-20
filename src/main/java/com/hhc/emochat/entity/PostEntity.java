package com.hhc.emochat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;
    private LocalDateTime date;

    public PostEntity() {}
    public PostEntity(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = LocalDateTime.now();
    }
}
