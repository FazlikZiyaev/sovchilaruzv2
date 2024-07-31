package uz.devops.sovchilaruzv2.service.impl;

import org.springframework.stereotype.Component;
import uz.devops.sovchilaruzv2.service.interfaces.OtpGeneratorService;

@Component
public class OtpGeneratorServiceSimulateImpl implements OtpGeneratorService {

    private static final String SIMULATE_OTP_CODE = "1234";

    @Override
    public String generateOtp() {
        return SIMULATE_OTP_CODE;
    }
}
