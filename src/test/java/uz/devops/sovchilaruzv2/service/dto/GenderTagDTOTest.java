package uz.devops.sovchilaruzv2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class GenderTagDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenderTagDTO.class);
        GenderTagDTO genderTagDTO1 = new GenderTagDTO();
        genderTagDTO1.setId(UUID.randomUUID());
        GenderTagDTO genderTagDTO2 = new GenderTagDTO();
        assertThat(genderTagDTO1).isNotEqualTo(genderTagDTO2);
        genderTagDTO2.setId(genderTagDTO1.getId());
        assertThat(genderTagDTO1).isEqualTo(genderTagDTO2);
        genderTagDTO2.setId(UUID.randomUUID());
        assertThat(genderTagDTO1).isNotEqualTo(genderTagDTO2);
        genderTagDTO1.setId(null);
        assertThat(genderTagDTO1).isNotEqualTo(genderTagDTO2);
    }
}
