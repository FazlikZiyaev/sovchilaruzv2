package uz.devops.sovchilaruzv2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class NationalityTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Nationality getNationalitySample1() {
        return new Nationality().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1").ord(1);
    }

    public static Nationality getNationalitySample2() {
        return new Nationality().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2").ord(2);
    }

    public static Nationality getNationalityRandomSampleGenerator() {
        return new Nationality().id(UUID.randomUUID()).name(UUID.randomUUID().toString()).ord(intCount.incrementAndGet());
    }
}
