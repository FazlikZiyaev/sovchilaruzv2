package uz.devops.sovchilaruzv2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import uz.devops.sovchilaruzv2.web.rest.TestUtil;

class AttachmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentDTO.class);
        AttachmentDTO attachmentDTO1 = new AttachmentDTO();
        attachmentDTO1.setId(UUID.randomUUID());
        AttachmentDTO attachmentDTO2 = new AttachmentDTO();
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
        attachmentDTO2.setId(attachmentDTO1.getId());
        assertThat(attachmentDTO1).isEqualTo(attachmentDTO2);
        attachmentDTO2.setId(UUID.randomUUID());
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
        attachmentDTO1.setId(null);
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
    }
}
