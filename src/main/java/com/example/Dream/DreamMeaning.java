package com.example.Dream;

import jakarta.persistence.*;

@Entity
public class DreamMeaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;   // "물", "불" 같은 값

    @Column(length = 1000)
    private String description; // 해몽 문장

    public DreamMeaning() {
    }

    public Long getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
