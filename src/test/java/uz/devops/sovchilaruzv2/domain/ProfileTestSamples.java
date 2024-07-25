package uz.devops.sovchilaruzv2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfileTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Profile getProfileSample1() {
        return new Profile()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .age(1)
            .profession("profession1")
            .workPlace("workPlace1")
            .healthIssues("healthIssues1")
            .placeOfBirth("placeOfBirth1")
            .numOfMembersInFamily(1)
            .numOfChildrenInFamily(1)
            .birthPositionInFamily(1)
            .knowledgeOfLanguages("knowledgeOfLanguages1")
            .skills("skills1")
            .bio("bio1")
            .requirements("requirements1");
    }

    public static Profile getProfileSample2() {
        return new Profile()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .age(2)
            .profession("profession2")
            .workPlace("workPlace2")
            .healthIssues("healthIssues2")
            .placeOfBirth("placeOfBirth2")
            .numOfMembersInFamily(2)
            .numOfChildrenInFamily(2)
            .birthPositionInFamily(2)
            .knowledgeOfLanguages("knowledgeOfLanguages2")
            .skills("skills2")
            .bio("bio2")
            .requirements("requirements2");
    }

    public static Profile getProfileRandomSampleGenerator() {
        return new Profile()
            .id(UUID.randomUUID())
            .age(intCount.incrementAndGet())
            .profession(UUID.randomUUID().toString())
            .workPlace(UUID.randomUUID().toString())
            .healthIssues(UUID.randomUUID().toString())
            .placeOfBirth(UUID.randomUUID().toString())
            .numOfMembersInFamily(intCount.incrementAndGet())
            .numOfChildrenInFamily(intCount.incrementAndGet())
            .birthPositionInFamily(intCount.incrementAndGet())
            .knowledgeOfLanguages(UUID.randomUUID().toString())
            .skills(UUID.randomUUID().toString())
            .bio(UUID.randomUUID().toString())
            .requirements(UUID.randomUUID().toString());
    }
}
