package uz.devops.sovchilaruzv2.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uz.devops.sovchilaruzv2.domain.enumeration.OTPMode;
import uz.devops.sovchilaruzv2.repository.OTPRepository;
import uz.devops.sovchilaruzv2.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
// todo create otp service interface
// and move current class to impl package
public class OTPService {

    private final OTPRepository otpRepository;
    private final UserRepository userRepository;

    private final int OTP_LIMIT = 3;

    public void generateAndSaveOTP(String login, OTPMode mode) {
        int currentOTPCounter = otpRepository.getOtpRequestCount(login);

        if (currentOTPCounter >= OTP_LIMIT) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
        }

        otpRepository.incrementOtpRequestCount(login);
        String otp = otpRepository.generateOTP(mode);
        otpRepository.saveOTP(login, otp);

        log.debug("Generating and Saving otp... login: {} otp: {}", login, otp);
    }

    public void verifyOTP(String login, String otp) {
        String cachedOtp = otpRepository.getOTP(login);

        log.debug("Comparing cached otp: {} otp: {}", cachedOtp, otp);

        if (!cachedOtp.equals(otp)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid OTP");
        }

        userRepository
            .findOneByLogin(login)
            .map(user -> {
                user.setActivated(true);
                user.setActivationKey(null);
                log.debug("Activated user: {}", user);
                userRepository.save(user);
                otpRepository.resetOtpRequestCount(login);
                return user;
            });
    }
}
