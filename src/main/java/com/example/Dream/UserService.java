package com.example.Dream;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public User register(String loginId, String password, String name) {
        // loginId 중복 체크 (간단 버전)
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(password); // 실제 서비스라면 암호화 필요
        user.setName(name);

        return userRepository.save(user);
    }

    // 로그인 (성공 시 User 반환, 실패 시 null)
    public User login(String loginId, String password) {
        return userRepository
                .findByLoginIdAndPassword(loginId, password)
                .orElse(null);
    }
}
