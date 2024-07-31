package uz.devops.sovchilaruzv2.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import uz.devops.sovchilaruzv2.domain.enumeration.OTPMode;
import uz.devops.sovchilaruzv2.service.interfaces.OtpService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    private static final String OTP_COUNT_SUFFIX = ":otp_count";

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public String generateOtp(OTPMode mode) {
        return mode == OTPMode.PROD_MODE ? String.valueOf(new Random().nextInt(9000) + 1000) : "1234";
    }

    @Override
    public String getOTP(String login) {
        return (String) redisTemplate.opsForValue().get(login);
    }

    @Override
    public String saveOTP(String login, String otp, int expirationTime) {
        redisTemplate.opsForValue().set(login, otp);
        redisTemplate.expire(login, expirationTime, TimeUnit.SECONDS);
        return getOTP(login);
    }

    @Override
    public Integer getOtpRequestCount(String login) {
        String countStr = (String) redisTemplate.opsForValue().get(login + OTP_COUNT_SUFFIX);
        return countStr == null ? 0 : Integer.parseInt(countStr);
    }

    @Override
    public void incrementOtpRequestCount(String login) {
        redisTemplate.opsForValue().increment(login + OTP_COUNT_SUFFIX);
    }

    @Override
    public void resetOtpRequestCount(String login) {
        redisTemplate.delete(login + OTP_COUNT_SUFFIX);
    }
}
