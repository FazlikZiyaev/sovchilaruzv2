package uz.devops.sovchilaruzv2.service.impl;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import uz.devops.sovchilaruzv2.domain.enumeration.OTPMode;
import uz.devops.sovchilaruzv2.repository.UserRepository;
import uz.devops.sovchilaruzv2.service.interfaces.OtpClientService;
import uz.devops.sovchilaruzv2.service.interfaces.OtpService;
import uz.devops.sovchilaruzv2.service.interfaces.RedisCacheService;

@Component
@RequiredArgsConstructor
@Slf4j
// todo create otp service interface
// and move current class to impl package
public class OtpClientServiceDemoImpl implements OtpClientService {

    private final OtpService otpService;
    private final RedisCacheService redisCacheService;
    private final UserRepository userRepository;

    private static final String OTP_COUNT_SUFFIX = ":otp_count";

    @Value("${otp.sms.smsCodeExpireTime}")
    private int codeExpireTime;

    @Value("${otp.sms.smsCodeResendLimit}")
    private int codeResendLimit;

    @Override
    public void send(String login, OTPMode mode) {
        log.info("Otp -> ClientDemo -> send beginning -> login = {} mode = {}", login, mode);

        if (redisCacheService.getValueForKey(login + OTP_COUNT_SUFFIX) == null) {
            redisCacheService.saveValueForKey(login + OTP_COUNT_SUFFIX, "0");
        }

        int currentOTPCounter = Integer.parseInt(redisCacheService.getValueForKey(login + OTP_COUNT_SUFFIX));

        if (currentOTPCounter >= codeResendLimit) {
            log.info("Otp -> ClientDemo -> send -> too many requests: count = {}", currentOTPCounter);

            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
        }

        redisCacheService.saveValueForKey(login + OTP_COUNT_SUFFIX, String.format("%d", currentOTPCounter + 1));
        String otp = generateOtp(mode);
        redisCacheService.saveValueForKey(login + OTP_COUNT_SUFFIX, otp, codeExpireTime);

        log.info("Otp -> ClientDemo -> send success -> login: {} otp: {}", login, otp);
    }

    @Override
    public void verify(String login, String otp) {
        log.info("Otp -> ClientDemo -> verify beginning -> login = {} otp = {}", login, otp);

        String cachedOtp = otpService.getOTP(login);

        if (!cachedOtp.equals(otp)) {
            log.info("Otp -> ClientDemo -> verify -> otp is not identical: cachedOtp: {} paramOtp: {}", cachedOtp, otp);

            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid OTP");
        }

        userRepository
            .findOneByLogin(login)
            .map(user -> {
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                otpService.resetOtpRequestCount(login);
                return user;
            });

        log.info("Otp -> ClientDemo -> verify success -> login: {} otp: {}", login, otp);
    }

    private String generateOtp(OTPMode mode) {
        return mode == OTPMode.PROD_MODE ? String.valueOf(new Random().nextInt(9000) + 1000) : "1234";
    }
}
