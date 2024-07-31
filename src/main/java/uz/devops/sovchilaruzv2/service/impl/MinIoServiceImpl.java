package uz.devops.sovchilaruzv2.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.devops.sovchilaruzv2.config.Constants;
import uz.devops.sovchilaruzv2.domain.Attachment;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.Extension;
import uz.devops.sovchilaruzv2.service.AttachmentService;
import uz.devops.sovchilaruzv2.service.MinIoService;
import uz.devops.sovchilaruzv2.service.UserService;
import uz.devops.sovchilaruzv2.service.mapper.AttachmentMapper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinIoServiceImpl implements MinIoService {


    private final MinioClient minioClient;
    private final AttachmentService attachmentService;
    private final UserService userService;
    private final AttachmentMapper attachmentMapper;

    @Override
    public String generatePreSignedUrl(String fileType, String userId) {
        UUID uuid = UUID.randomUUID();
        try {
            Extension extension = Extension.findExtensionType(Objects.requireNonNull(fileType));
            createBucket();
            Attachment attachment = Attachment.builder()
                .profile(userService.getByUserId(UUID.fromString(userId)).getProfile())
                .state(EntityState.ACTIVE)
                .extension(extension)
                .fileKey(uuid.toString())
                .build();

            attachmentService.save(attachmentMapper.toDto(attachment));

            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(Constants.MIN_IO_BUCKET_NAME)
                    .object(userId.concat("/") + uuid + extension.getExtension())
                    .expiry(60 * 60 * 24, TimeUnit.SECONDS)
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String downloadAttachment(String fileId, String userId) {
        try {
            createBucket();

            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .expiry(60 * 60 * 24, TimeUnit.SECONDS)
                    .bucket(Constants.MIN_IO_BUCKET_NAME)
                    .object(userId + "/" + fileId)
                    .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteAttachment(String fileId, String userId) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(Constants.MIN_IO_BUCKET_NAME)
                .expiry(60 * 60 * 24, TimeUnit.SECONDS)
                .method(Method.DELETE)
                .object(userId + fileId)
                .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void createBucket()
        throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(Constants.MIN_IO_BUCKET_NAME).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(Constants.MIN_IO_BUCKET_NAME).build());
        }
    }

}
