package uz.devops.sovchilaruzv2.service.interfaces;

import uz.devops.sovchilaruzv2.domain.enumeration.OTPMode;

// Can have a lot of implementations, sms-code, telegram etc...
public interface OtpClientService {
    void send(String login, OTPMode mode);
    void verify(String login, String otp);
}
