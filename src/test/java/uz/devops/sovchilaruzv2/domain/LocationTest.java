package uz.devops.sovchilaruzv2.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uz.devops.sovchilaruzv2.domain.LocationTestSamples.*;
import static uz.devops.sovchilaruzv2.domain.ProfileTestSamples.*;

import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void profileTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Profile profileBack = getProfileRandomSampleGenerator();

        location.setProfile(profileBack);
        assertThat(location.getProfile()).isEqualTo(profileBack);
        assertThat(profileBack.getLocation()).isEqualTo(location);

        location.profile(null);
        assertThat(location.getProfile()).isNull();
        assertThat(profileBack.getLocation()).isNull();
    }
}
