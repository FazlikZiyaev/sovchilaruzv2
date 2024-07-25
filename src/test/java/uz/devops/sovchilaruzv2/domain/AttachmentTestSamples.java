package uz.devops.sovchilaruzv2.domain;

import java.util.UUID;

public class AttachmentTestSamples {

    public static Attachment getAttachmentSample1() {
        return new Attachment().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).fileKey("fileKey1");
    }

    public static Attachment getAttachmentSample2() {
        return new Attachment().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).fileKey("fileKey2");
    }

    public static Attachment getAttachmentRandomSampleGenerator() {
        return new Attachment().id(UUID.randomUUID()).fileKey(UUID.randomUUID().toString());
    }
}
