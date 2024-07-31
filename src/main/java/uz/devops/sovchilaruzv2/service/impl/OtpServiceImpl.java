package uz.devops.sovchilaruzv2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import uz.devops.sovchilaruzv2.config.OtpProperties;
import uz.devops.sovchilaruzv2.domain.enumeration.OtpMode;
import uz.devops.sovchilaruzv2.repository.UserRepository;
import uz.devops.sovchilaruzv2.service.interfaces.OtpClientService;
import uz.devops.sovchilaruzv2.service.interfaces.OtpGeneratorService;
import uz.devops.sovchilaruzv2.service.interfaces.OtpService;
import uz.devops.sovchilaruzv2.service.interfaces.OtpStorageService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    private static final String OTP_COUNT_SUFFIX = ":otp_count";

    private final OtpProperties properties;

    private final OtpGeneratorService otpGeneratorService;
    private final OtpStorageService otpStorageService;
    private final OtpClientService otpClientService;
    private final UserRepository userRepository;

    @Override
    public void send(String login, OtpMode mode) {
        log.info("Otp -> OtpServiceImpl -> send beginning -> login = {} mode = {}", login, mode);

        if (otpStorageService.getValueForKey(login + OTP_COUNT_SUFFIX) == null) {
            otpStorageService.saveValueForKey(login + OTP_COUNT_SUFFIX, "0");
        }

        int currentOTPCounter = Integer.parseInt(otpStorageService.getValueForKey(login + OTP_COUNT_SUFFIX));

        if (currentOTPCounter >= properties.getSms().getSmsCodeResendLimit()) {
            log.info("Otp -> ClientDemo -> send -> too many requests: count = {}", currentOTPCounter);

            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
        }

        otpStorageService.saveValueForKey(login + OTP_COUNT_SUFFIX, String.format("%d", currentOTPCounter + 1));
        String otp = otpGeneratorService.generateOtp();
        otpStorageService.saveValueForKey(login, otp, properties.getSms().getSmsCodeExpireTime());

        otpClientService.send(login, otp);

        log.info("Otp -> ClientDemo -> send success -> login: {} otp: {}", login, otp);
    }

    @Override
    public void verify(String login, String otp) {
        log.info("Otp -> ClientDemo -> verify beginning -> login = {} otp = {}", login, otp);

        String cachedOtp = otpStorageService.getValueForKey(login);

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
                otpStorageService.deleteValueForKey(login + OTP_COUNT_SUFFIX);
                return user;
            });

        log.info("Otp -> ClientDemo -> verify success -> login: {} otp: {}", login, otp);
    }
}
