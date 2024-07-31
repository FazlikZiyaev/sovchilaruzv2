package uz.devops.sovchilaruzv2.domain.enumeration;

import lombok.Getter;

@Getter
public enum Extension {
    JPG("image/jpg", ".jpg"),
    PNG("image/png", ".png"),
    JPEG("image/jpeg", ".jpeg");

    private final String contentType;
    private final String extension;

    Extension(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    public static Extension findExtensionType(String fileType) {
        if (!fileType.startsWith(".")) {
            fileType = "." + fileType;
        }
        for (Extension value : Extension.values()) {
            if (value.getExtension().equals(fileType)) return value;
        }
        throw new IllegalArgumentException("Invalid extension type: " + fileType);
    }
}
