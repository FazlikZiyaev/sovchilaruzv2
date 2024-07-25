package uz.devops.sovchilaruzv2.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.devops.sovchilaruzv2.domain.ProfileDiscoverabilityTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class ProfileDiscoverabilityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileDiscoverability.class);
        ProfileDiscoverability profileDiscoverability1 = getProfileDiscoverabilitySample1();
        ProfileDiscoverability profileDiscoverability2 = new ProfileDiscoverability();
        assertThat(profileDiscoverability1).isNotEqualTo(profileDiscoverability2);

        profileDiscoverability2.setId(profileDiscoverability1.getId());
        assertThat(profileDiscoverability1).isEqualTo(profileDiscoverability2);

        profileDiscoverability2 = getProfileDiscoverabilitySample2();
        assertThat(profileDiscoverability1).isNotEqualTo(profileDiscoverability2);
    }
}
