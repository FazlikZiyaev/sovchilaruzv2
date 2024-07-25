package uz.devops.sovchilaruzv2.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.devops.sovchilaruzv2.domain.AttachmentTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.ContactInfoTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.GenderTagTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.LocationTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.NationalityTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.ProfileDiscoverabilityTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.ProfileTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class ProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = getProfileSample1();
        Profile profile2 = new Profile();
        assertThat(profile1).isNotEqualTo(profile2);

        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);

        profile2 = getProfileSample2();
        assertThat(profile1).isNotEqualTo(profile2);
    }

    @Test
    void locationTest() throws Exception {
        Profile profile = getProfileRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        profile.setLocation(locationBack);
        assertThat(profile.getLocation()).isEqualTo(locationBack);

        profile.location(null);
        assertThat(profile.getLocation()).isNull();
    }

    @Test
    void contactInfosTest() throws Exception {
        Profile profile = getProfileRandomSampleGenerator();
        ContactInfo contactInfoBack = getContactInfoRandomSampleGenerator();

        profile.addContactInfos(contactInfoBack);
        assertThat(profile.getContactInfos()).containsOnly(contactInfoBack);
        assertThat(contactInfoBack.getProfile()).isEqualTo(profile);

        profile.removeContactInfos(contactInfoBack);
        assertThat(profile.getContactInfos()).doesNotContain(contactInfoBack);
        assertThat(contactInfoBack.getProfile()).isNull();

        profile.contactInfos(new HashSet<>(Set.of(contactInfoBack)));
        assertThat(profile.getContactInfos()).containsOnly(contactInfoBack);
        assertThat(contactInfoBack.getProfile()).isEqualTo(profile);

        profile.setContactInfos(new HashSet<>());
        assertThat(profile.getContactInfos()).doesNotContain(contactInfoBack);
        assertThat(contactInfoBack.getProfile()).isNull();
    }

    @Test
    void attachmentsTest() throws Exception {
        Profile profile = getProfileRandomSampleGenerator();
        Attachment attachmentBack = getAttachmentRandomSampleGenerator();

        profile.addAttachments(attachmentBack);
        assertThat(profile.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getProfile()).isEqualTo(profile);

        profile.removeAttachments(attachmentBack);
        assertThat(profile.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getProfile()).isNull();

        profile.attachments(new HashSet<>(Set.of(attachmentBack)));
        assertThat(profile.getAttachments()).containsOnly(attachmentBack);
        assertThat(attachmentBack.getProfile()).isEqualTo(profile);

        profile.setAttachments(new HashSet<>());
        assertThat(profile.getAttachments()).doesNotContain(attachmentBack);
        assertThat(attachmentBack.getProfile()).isNull();
    }

    @Test
    void discoverabilityTest() throws Exception {
        Profile profile = getProfileRandomSampleGenerator();
        ProfileDiscoverability profileDiscoverabilityBack = getProfileDiscoverabilityRandomSampleGenerator();

        profile.setDiscoverability(profileDiscoverabilityBack);
        assertThat(profile.getDiscoverability()).isEqualTo(profileDiscoverabilityBack);

        profile.discoverability(null);
        assertThat(profile.getDiscoverability()).isNull();
    }

    @Test
    void nationalityTest() throws Exception {
        Profile profile = getProfileRandomSampleGenerator();
        Nationality nationalityBack = getNationalityRandomSampleGenerator();

        profile.setNationality(nationalityBack);
        assertThat(profile.getNationality()).isEqualTo(nationalityBack);

        profile.nationality(null);
        assertThat(profile.getNationality()).isNull();
    }

    @Test
    void genderTagsTest() throws Exception {
        Profile profile = getProfileRandomSampleGenerator();
        GenderTag genderTagBack = getGenderTagRandomSampleGenerator();

        profile.addGenderTags(genderTagBack);
        assertThat(profile.getGenderTags()).containsOnly(genderTagBack);

        profile.removeGenderTags(genderTagBack);
        assertThat(profile.getGenderTags()).doesNotContain(genderTagBack);

        profile.genderTags(new HashSet<>(Set.of(genderTagBack)));
        assertThat(profile.getGenderTags()).containsOnly(genderTagBack);

        profile.setGenderTags(new HashSet<>());
        assertThat(profile.getGenderTags()).doesNotContain(genderTagBack);
    }
}
