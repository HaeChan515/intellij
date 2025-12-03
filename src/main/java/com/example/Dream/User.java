package com.example.Dream;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인 아이디(중복X), ex) haechan
    @Column(unique = true)
    private String loginId;

    // 비밀번호 (실제 서비스라면 암호화해야 하지만, 과제라서 그냥 저장해도 OK)
    private String password;

    // 이름 or 닉네임
    private String name;

    public User() {}

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
