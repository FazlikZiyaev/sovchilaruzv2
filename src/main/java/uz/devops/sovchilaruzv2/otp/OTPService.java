package uz.devops.sovchilaruzv2.otp;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uz.devops.sovchilaruzv2.domain.User;
import uz.devops.sovchilaruzv2.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OTPService {

    private final OTPRepositoryImpl otpRepositoryImpl;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    public void generateAndSaveOTP(String login) {
        String otp = otpRepositoryImpl.generateOTP();
        otpRepositoryImpl.saveOTP(login, otp);

        log.debug("Generating and Saving otp... result is {}", otp);
    }

    public void verifyOTP(String login, String otp) {
        Boolean isSuccess = otpRepositoryImpl.checkOtp(login, otp);

        log.debug("Checking otp... result is {}", isSuccess);

        if (!isSuccess) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid OTP");
        }

        userRepository
            .findOneByLogin(login)
            .map(user -> {
                user.setActivated(true);
                user.setActivationKey(null);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                userRepository.save(user);
                return user;
            });
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
