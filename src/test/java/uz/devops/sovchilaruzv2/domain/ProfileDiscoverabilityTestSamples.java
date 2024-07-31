package uz.devops.sovchilaruzv2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProfileDiscoverabilityTestSamples {

    private static final Random random = new Random();
    private static final UUID id = UUID.randomUUID();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ProfileDiscoverability getProfileDiscoverabilitySample1() {
        return new ProfileDiscoverability().id(UUID.randomUUID()).maxAge(1).minAge(1);
    }

    public static ProfileDiscoverability getProfileDiscoverabilitySample2() {
        return new ProfileDiscoverability().id(UUID.randomUUID()).maxAge(2).minAge(2);
    }

    public static ProfileDiscoverability getProfileDiscoverabilityRandomSampleGenerator() {
        return new ProfileDiscoverability().id(id).maxAge(intCount.incrementAndGet()).minAge(intCount.incrementAndGet());
    }
}
