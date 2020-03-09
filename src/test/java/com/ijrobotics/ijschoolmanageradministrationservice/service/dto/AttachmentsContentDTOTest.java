package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class AttachmentsContentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentsContentDTO.class);
        AttachmentsContentDTO attachmentsContentDTO1 = new AttachmentsContentDTO();
        attachmentsContentDTO1.setId(1L);
        AttachmentsContentDTO attachmentsContentDTO2 = new AttachmentsContentDTO();
        assertThat(attachmentsContentDTO1).isNotEqualTo(attachmentsContentDTO2);
        attachmentsContentDTO2.setId(attachmentsContentDTO1.getId());
        assertThat(attachmentsContentDTO1).isEqualTo(attachmentsContentDTO2);
        attachmentsContentDTO2.setId(2L);
        assertThat(attachmentsContentDTO1).isNotEqualTo(attachmentsContentDTO2);
        attachmentsContentDTO1.setId(null);
        assertThat(attachmentsContentDTO1).isNotEqualTo(attachmentsContentDTO2);
    }
}
