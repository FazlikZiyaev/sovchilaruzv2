package uz.devops.sovchilaruzv2.service.interfaces;

import uz.devops.sovchilaruzv2.domain.enumeration.OTPMode;

public interface OtpService {
    String generateOtp(OTPMode mode);

    String getOTP(String login);
    String saveOTP(String login, String otp, int expirationTime);

    Integer getOtpRequestCount(String login);
    void incrementOtpRequestCount(String login);
    void resetOtpRequestCount(String login);
}
