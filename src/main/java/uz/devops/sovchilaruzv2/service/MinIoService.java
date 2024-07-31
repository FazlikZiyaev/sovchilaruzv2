package uz.devops.sovchilaruzv2.service;

public interface MinIoService {
    String generatePreSignedUrl(String fileType, String userId);

    String downloadAttachment(String fileId, String userId);

    String deleteAttachment(String fileId, String userId);
}
