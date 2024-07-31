package uz.devops.sovchilaruzv2.repository;

import uz.devops.sovchilaruzv2.domain.enumeration.OTPMode;

public interface OTPRepository {
    String generateOTP(OTPMode mode);

    String getOTP(String login);
    String saveOTP(String login, String otp);

    Integer getOtpRequestCount(String login);
    void incrementOtpRequestCount(String login);
    void resetOtpRequestCount(String login);
}
