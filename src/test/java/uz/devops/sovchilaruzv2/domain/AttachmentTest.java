package uz.devops.sovchilaruzv2.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.devops.sovchilaruzv2.domain.AttachmentTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.ProfileTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class AttachmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attachment.class);
        Attachment attachment1 = getAttachmentSample1();
        Attachment attachment2 = new Attachment();
        assertThat(attachment1).isNotEqualTo(attachment2);

        attachment2.setId(attachment1.getId());
        assertThat(attachment1).isEqualTo(attachment2);

        attachment2 = getAttachmentSample2();
        assertThat(attachment1).isNotEqualTo(attachment2);
    }

    @Test
    void profileTest() throws Exception {
        Attachment attachment = getAttachmentRandomSampleGenerator();
        Profile profileBack = getProfileRandomSampleGenerator();

        attachment.setProfile(profileBack);
        assertThat(attachment.getProfile()).isEqualTo(profileBack);

        attachment.profile(null);
        assertThat(attachment.getProfile()).isNull();
    }
}
