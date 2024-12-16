package com.ra.md05_project.service.token;

import com.ra.md05_project.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Scheduled(cron = "0 3 15 * * ?")  // Chạy vào lúc 00:00 hàng ngày
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.deleteByExpiredAtBefore(now);
    }
}
