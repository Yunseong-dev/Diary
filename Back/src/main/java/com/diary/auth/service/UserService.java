package com.diary.auth.service;

import com.diary.auth.dto.CreateUserDto;
import com.diary.auth.dto.LoginUserDto;
import com.diary.auth.dto.TokenDto;
import com.diary.auth.model.Token;
import com.diary.auth.model.User;
import com.diary.auth.repository.TokenRepository;
import com.diary.auth.repository.UserRepository;
import com.diary.auth.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> createUser(CreateUserDto dto) {
        if (userRepository.findById(dto.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디 입니다.");
        }

        User user = new User(
                dto.getId(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword())
        );
        userRepository.save(user);

        return ResponseEntity.ok().body("회원가입이 성공적으로 되었습니다.");
    }

    public ResponseEntity<?> loginUser(LoginUserDto dto) {
        User user = userRepository.findById(dto.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 아이디 입니다.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        String accessToken = jwtTokenProvider.generateAccessToken(refreshToken);

        Token token = new Token(
                UUID.randomUUID(),
                user,
                refreshToken
        );
        tokenRepository.save(token);

        return ResponseEntity.ok().body(new TokenDto(accessToken, refreshToken));
    }

    public ResponseEntity<?> refreshToken(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 존재하지 않습니다.");
        }

        User user = token.getUser();
        if (!jwtTokenProvider.validateRefreshToken(refreshToken, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 토큰을 가지고 있습니다.");
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(refreshToken);

        return ResponseEntity.ok().body(new TokenDto(newAccessToken, refreshToken));
    }

}
