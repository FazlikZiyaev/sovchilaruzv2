package uz.devops.sovchilaruzv2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ContactInfo getContactInfoSample1() {
        return new ContactInfo().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).text("text1").ord(1);
    }

    public static ContactInfo getContactInfoSample2() {
        return new ContactInfo().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).text("text2").ord(2);
    }

    public static ContactInfo getContactInfoRandomSampleGenerator() {
        return new ContactInfo().id(UUID.randomUUID()).text(UUID.randomUUID().toString()).ord(intCount.incrementAndGet());
    }
}
