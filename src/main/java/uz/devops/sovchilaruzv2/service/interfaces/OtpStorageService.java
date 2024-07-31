package uz.devops.sovchilaruzv2.service.interfaces;

public interface OtpStorageService {
    String getValueForKey(String key);
    String saveValueForKey(String key, String value, int expirationTime);
    String saveValueForKey(String key, String value);
    void deleteValueForKey(String key);
}
