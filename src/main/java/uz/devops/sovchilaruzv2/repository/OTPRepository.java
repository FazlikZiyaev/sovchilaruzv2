package uz.devops.sovchilaruzv2.repository;

import org.springframework.stereotype.Repository;
import uz.devops.sovchilaruzv2.domain.enumeration.OTPMode;

@Repository
public interface OTPRepository {
    String generateOTP(OTPMode mode);

    String getOTP(String login);
    String saveOTP(String login, String otp);
    void deleteOTP(String login);

    Boolean checkOtp(String login, String otp);
}
