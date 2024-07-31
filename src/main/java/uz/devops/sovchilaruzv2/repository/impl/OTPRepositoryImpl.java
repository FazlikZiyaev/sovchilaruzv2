package uz.devops.sovchilaruzv2.repository.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import uz.devops.sovchilaruzv2.domain.enumeration.OTPMode;
import uz.devops.sovchilaruzv2.repository.OTPRepository;

@Component
public class OTPRepositoryImpl implements OTPRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String OTP_COUNT_SUFFIX = ":otp_count";

    @Override
    public String generateOTP(OTPMode mode) {
        return mode == OTPMode.PROD_MODE ? String.valueOf(new Random().nextInt(9000) + 1000) : "1234";
    }

    @Override
    public String getOTP(String login) {
        return (String) redisTemplate.opsForValue().get(login);
    }

    @Override
    public String saveOTP(String login, String otp) {
        redisTemplate.opsForValue().set(login, otp);
        redisTemplate.expire(login, 2, TimeUnit.MINUTES);
        return getOTP(login);
    }

    @Override
    public Integer getOtpRequestCount(String login) {
        Integer count = (Integer) redisTemplate.opsForValue().get(login + OTP_COUNT_SUFFIX);
        return count == null ? 0 : count;
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
