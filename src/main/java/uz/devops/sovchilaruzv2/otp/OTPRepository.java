package uz.devops.sovchilaruzv2.otp;

import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository {
    String generateOTP();

    String getOTP(String login);
    String saveOTP(String login, String otp);
    void deleteOTP(String login);

    Boolean checkOtp(String login, String otp);
}
