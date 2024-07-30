package uz.devops.sovchilaruzv2.repository.impl;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import uz.devops.sovchilaruzv2.repository.OTPRepository;

@Component
public class OTPRepositoryImpl implements OTPRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String generateOTP() {
        return String.valueOf(new Random().nextInt(9000) + 1000);
    }

    @Override
    public String getOTP(String login) {
        return (String) redisTemplate.opsForValue().get(login);
    }

    @Override
    public String saveOTP(String login, String otp) {
        redisTemplate.opsForValue().set(login, otp);
        return getOTP(login);
    }

    @Override
    public void deleteOTP(String otp) {
        redisTemplate.delete(otp);
    }

    @Override
    public Boolean checkOtp(String login, String otp) {
        return getOTP(login).equals(otp);
    }
}
