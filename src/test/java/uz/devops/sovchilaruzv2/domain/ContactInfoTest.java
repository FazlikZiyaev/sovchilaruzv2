package uz.devops.sovchilaruzv2.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.devops.sovchilaruzv2.domain.ContactInfoTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.ProfileTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class ContactInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactInfo.class);
        ContactInfo contactInfo1 = getContactInfoSample1();
        ContactInfo contactInfo2 = new ContactInfo();
        assertThat(contactInfo1).isNotEqualTo(contactInfo2);

        contactInfo2.setId(contactInfo1.getId());
        assertThat(contactInfo1).isEqualTo(contactInfo2);

        contactInfo2 = getContactInfoSample2();
        assertThat(contactInfo1).isNotEqualTo(contactInfo2);
    }

    @Test
    void profileTest() throws Exception {
        ContactInfo contactInfo = getContactInfoRandomSampleGenerator();
        Profile profileBack = getProfileRandomSampleGenerator();

        contactInfo.setProfile(profileBack);
        assertThat(contactInfo.getProfile()).isEqualTo(profileBack);

        contactInfo.profile(null);
        assertThat(contactInfo.getProfile()).isNull();
    }
}
