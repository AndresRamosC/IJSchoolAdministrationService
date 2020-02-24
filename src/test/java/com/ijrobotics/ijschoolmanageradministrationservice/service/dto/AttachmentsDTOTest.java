package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class AttachmentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentsDTO.class);
        AttachmentsDTO attachmentsDTO1 = new AttachmentsDTO();
        attachmentsDTO1.setId(1L);
        AttachmentsDTO attachmentsDTO2 = new AttachmentsDTO();
        assertThat(attachmentsDTO1).isNotEqualTo(attachmentsDTO2);
        attachmentsDTO2.setId(attachmentsDTO1.getId());
        assertThat(attachmentsDTO1).isEqualTo(attachmentsDTO2);
        attachmentsDTO2.setId(2L);
        assertThat(attachmentsDTO1).isNotEqualTo(attachmentsDTO2);
        attachmentsDTO1.setId(null);
        assertThat(attachmentsDTO1).isNotEqualTo(attachmentsDTO2);
    }
}
