package com.example.Dream;

import org.springframework.stereotype.Service;

@Service
public class UserService {

<<<<<<< HEAD
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String loginId, String password, String name) {

        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(password);
        user.setName(name);

        return userRepository.save(user);
    }

    public User login(String loginId, String password) {
        return userRepository.findByLoginIdAndPassword(loginId, password)
                .orElse(null);
    }
}
=======
    public UserService() {
        // 일단 비워둬도 됨
    }

    // 나중에 회원가입 / 로그인 같은 기능 메서드 추가
}
>>>>>>> f96e2d56ffafd39cdde56c3ed2770536865e2144
