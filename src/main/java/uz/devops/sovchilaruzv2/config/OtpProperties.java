package uz.devops.sovchilaruzv2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "otp", ignoreUnknownFields = false)
public class OtpProperties {

    private SmsProperties sms = new SmsProperties();

    @Data
    public class SmsProperties {

        private int smsCodeExpireTime;
        private int smsCodeResendLimit;
    }
}
