package uz.devops.sovchilaruzv2.domain.enumeration;

import lombok.Getter;

@Getter
public enum Extension {
    JPG("image/jpg", ".jpg"),
    PNG("image/png", ".png"),
    JPEG("image/jpeg", ".jpeg"),
    ;
    private final String contentType;
    private final String extension;

    Extension(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    public static Extension findExtensionType(String fileType) {
        if (fileType.endsWith("png")) {
            return Extension.PNG;
        } else if (fileType.endsWith("jpg")) {
            return Extension.JPG;
        }
        throw new RuntimeException("Unsupported file type: " + fileType);
    }
}
