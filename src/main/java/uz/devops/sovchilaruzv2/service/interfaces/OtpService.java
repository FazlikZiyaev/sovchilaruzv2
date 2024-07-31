package uz.devops.sovchilaruzv2.service.interfaces;

import uz.devops.sovchilaruzv2.domain.enumeration.OtpMode;

public interface OtpService {
    void send(String login, OtpMode mode);
    void verify(String login, String otp);
}
