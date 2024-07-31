package uz.devops.sovchilaruzv2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LocationTestSamples {

    private static final Random random = new Random();
    private static final UUID id = UUID.randomUUID();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Location getLocationSample1() {
        return new Location().id(UUID.randomUUID()).country("country1").city("city1").district("district1").ord(1);
    }

    public static Location getLocationSample2() {
        return new Location().id(UUID.randomUUID()).country("country2").city("city2").district("district2").ord(2);
    }

    public static Location getLocationRandomSampleGenerator() {
        return new Location()
            .id(id)
            .country(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .district(UUID.randomUUID().toString())
            .ord(intCount.incrementAndGet());
    }
}
