package uz.devops.sovchilaruzv2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class ProfileDiscoverabilityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileDiscoverabilityDTO.class);
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO1 = new ProfileDiscoverabilityDTO();
        profileDiscoverabilityDTO1.setId(UUID.randomUUID());
        ProfileDiscoverabilityDTO profileDiscoverabilityDTO2 = new ProfileDiscoverabilityDTO();
        assertThat(profileDiscoverabilityDTO1).isNotEqualTo(profileDiscoverabilityDTO2);
        profileDiscoverabilityDTO2.setId(profileDiscoverabilityDTO1.getId());
        assertThat(profileDiscoverabilityDTO1).isEqualTo(profileDiscoverabilityDTO2);
        profileDiscoverabilityDTO2.setId(UUID.randomUUID());
        assertThat(profileDiscoverabilityDTO1).isNotEqualTo(profileDiscoverabilityDTO2);
        profileDiscoverabilityDTO1.setId(null);
        assertThat(profileDiscoverabilityDTO1).isNotEqualTo(profileDiscoverabilityDTO2);
    }
}
