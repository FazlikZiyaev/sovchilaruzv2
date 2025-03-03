package uz.devops.sovchilaruzv2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class ProfileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileDTO.class);
        ProfileDTO profileDTO1 = new ProfileDTO();
        profileDTO1.setId(UUID.randomUUID());
        ProfileDTO profileDTO2 = new ProfileDTO();
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO2.setId(profileDTO1.getId());
        assertThat(profileDTO1).isEqualTo(profileDTO2);
        profileDTO2.setId(UUID.randomUUID());
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO1.setId(null);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
    }
}
