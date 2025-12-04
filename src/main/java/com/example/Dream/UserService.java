package com.example.Dream;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    // 생성자 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원가입
     */
    public User register(String loginId, String password, String name) {

        // 아이디 중복 체크
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(password);
        user.setName(name);

        return userRepository.save(user);
    }

    /**
     * 로그인
     */
    public User login(String loginId, String password) {
        return userRepository
                .findByLoginIdAndPassword(loginId, password)
                .orElse(null);
    }
}
