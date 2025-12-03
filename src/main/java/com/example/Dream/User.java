package com.example.Dream;

import jakarta.persistence.*;

@Entity
@Table(name = "user")  // 테이블 이름 user로 매핑
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // PK, AUTO_INCREMENT

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;  // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name;     // 이름

    protected User() {}  // JPA 기본 생성자

    // getter / setter
    public Long getId() { return id; }

    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
