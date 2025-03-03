package uz.devops.sovchilaruzv2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class NationalityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NationalityDTO.class);
        NationalityDTO nationalityDTO1 = new NationalityDTO();
        nationalityDTO1.setId(UUID.randomUUID());
        NationalityDTO nationalityDTO2 = new NationalityDTO();
        assertThat(nationalityDTO1).isNotEqualTo(nationalityDTO2);
        nationalityDTO2.setId(nationalityDTO1.getId());
        assertThat(nationalityDTO1).isEqualTo(nationalityDTO2);
        nationalityDTO2.setId(UUID.randomUUID());
        assertThat(nationalityDTO1).isNotEqualTo(nationalityDTO2);
        nationalityDTO1.setId(null);
        assertThat(nationalityDTO1).isNotEqualTo(nationalityDTO2);
    }
}
