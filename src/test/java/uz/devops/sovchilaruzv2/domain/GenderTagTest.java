package uz.devops.sovchilaruzv2.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.devops.sovchilaruzv2.domain.GenderTagTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.ProfileTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class GenderTagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenderTag.class);
        GenderTag genderTag1 = getGenderTagSample1();
        GenderTag genderTag2 = new GenderTag();
        assertThat(genderTag1).isNotEqualTo(genderTag2);

        genderTag2.setId(genderTag1.getId());
        assertThat(genderTag1).isEqualTo(genderTag2);

        genderTag2 = getGenderTagSample2();
        assertThat(genderTag1).isNotEqualTo(genderTag2);
    }

    @Test
    void profileTest() throws Exception {
        GenderTag genderTag = getGenderTagRandomSampleGenerator();
        Profile profileBack = getProfileRandomSampleGenerator();

        genderTag.addProfile(profileBack);
        assertThat(genderTag.getProfiles()).containsOnly(profileBack);
        assertThat(profileBack.getGenderTags()).containsOnly(genderTag);

        genderTag.removeProfile(profileBack);
        assertThat(genderTag.getProfiles()).doesNotContain(profileBack);
        assertThat(profileBack.getGenderTags()).doesNotContain(genderTag);

        genderTag.profiles(new HashSet<>(Set.of(profileBack)));
        assertThat(genderTag.getProfiles()).containsOnly(profileBack);
        assertThat(profileBack.getGenderTags()).containsOnly(genderTag);

        genderTag.setProfiles(new HashSet<>());
        assertThat(genderTag.getProfiles()).doesNotContain(profileBack);
        assertThat(profileBack.getGenderTags()).doesNotContain(genderTag);
    }
}
