package uz.devops.sovchilaruzv2.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.devops.sovchilaruzv2.config.Constants;
import uz.devops.sovchilaruzv2.domain.Attachment;
import uz.devops.sovchilaruzv2.domain.User;
import uz.devops.sovchilaruzv2.domain.enumeration.AttachmentStatus;
import uz.devops.sovchilaruzv2.domain.enumeration.EntityState;
import uz.devops.sovchilaruzv2.domain.enumeration.Extension;
import uz.devops.sovchilaruzv2.service.AttachmentService;
import uz.devops.sovchilaruzv2.service.MinIoService;
import uz.devops.sovchilaruzv2.service.UserService;
import uz.devops.sovchilaruzv2.service.dto.AttachmentDTO;
import uz.devops.sovchilaruzv2.service.mapper.AttachmentMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinIoServiceImpl implements MinIoService {

    private static final Integer MINIO_PRE_SIGNED_URL_EXPIRY_TIME = 60 * 60 * 24; //ONE DAY

    private final MinioClient minioClient;
    private final AttachmentService attachmentService;
    private final UserService userService;
    private final AttachmentMapper attachmentMapper;

    @Override
    public String generatePreSignedUrl(String fileType, String userId) {
        UUID uuid = UUID.randomUUID();
        User user = userService.getByUserId(UUID.fromString(userId));
        try {
            Extension extension = Extension.findExtensionType(Objects.requireNonNull(fileType));
            createBucket();

            Attachment attachment = Attachment
                .builder()
                .profile(user.getProfile())
                .state(EntityState.ACTIVE)
                .status(AttachmentStatus.UNVERIFIED)
                .extension(extension)
                .user(user)
                .fileKey(uuid.toString())
                .build();

            attachmentService.save(attachmentMapper.toDto(attachment));

            return getPreSignedUrl(userId, uuid.toString(), extension, Method.PUT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String downloadAttachment(String fileId, String userId) {
        try {
            createBucket();

            return getPreSignedUrl(userId, fileId, null, Method.GET);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteAttachment(String fileId, String userId) {
        try {
            AttachmentDTO attachmentDTO = attachmentService.findByFileKey(fileId);
            attachmentDTO.setState(EntityState.DELETED);
            attachmentService.save(attachmentDTO);

            return getPreSignedUrl(userId, fileId, null, Method.DELETE);
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

    private String getPreSignedUrl(String userId, String fileKey, Extension extension, Method method)
        throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, XmlParserException, ServerException {
        return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs
                .builder()
                .method(method)
                .bucket(Constants.MIN_IO_BUCKET_NAME)
                .object(method == Method.PUT ? userId + "/" + fileKey + extension.getExtension() : userId + "/" + fileKey)
                .expiry(MINIO_PRE_SIGNED_URL_EXPIRY_TIME, TimeUnit.SECONDS)
                .build()
        );
    }
}
