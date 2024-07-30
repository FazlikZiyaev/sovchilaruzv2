package uz.devops.sovchilaruzv2.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository {
    String generateOTP();

    String getOTP(String login);
    String saveOTP(String login, String otp);
    void deleteOTP(String login);

    Boolean checkOtp(String login, String otp);
}
