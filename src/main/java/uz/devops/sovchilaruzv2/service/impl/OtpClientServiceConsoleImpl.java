package uz.devops.sovchilaruzv2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.devops.sovchilaruzv2.service.interfaces.OtpClientService;

@Component
@Slf4j
public class OtpClientServiceConsoleImpl implements OtpClientService {

    @Override
    public void send(String login, String otp) {
        log.info("OtpClientServiceDemoImpl.send() called -> login: {}, otp: {}", login, otp);
    }
}
