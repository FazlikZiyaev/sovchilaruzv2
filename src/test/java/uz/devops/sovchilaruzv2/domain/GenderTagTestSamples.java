package uz.devops.sovchilaruzv2.domain;

import java.util.UUID;

public class GenderTagTestSamples {

    public static GenderTag getGenderTagSample1() {
        return new GenderTag().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).hashtag("hashtag1");
    }

    public static GenderTag getGenderTagSample2() {
        return new GenderTag().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).hashtag("hashtag2");
    }

    public static GenderTag getGenderTagRandomSampleGenerator() {
        return new GenderTag().id(UUID.randomUUID()).hashtag(UUID.randomUUID().toString());
    }
}
