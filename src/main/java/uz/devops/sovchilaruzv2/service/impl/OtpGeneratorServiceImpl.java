package uz.devops.sovchilaruzv2.service.impl;

import java.util.Random;
import org.springframework.stereotype.Component;
import uz.devops.sovchilaruzv2.service.interfaces.OtpGeneratorService;

//@Component
public class OtpGeneratorServiceImpl implements OtpGeneratorService {

    @Override
    public String generateOtp() {
        return String.valueOf(new Random().nextInt(9000) + 1000);
    }
}
