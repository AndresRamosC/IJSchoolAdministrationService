package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class AttachmentsContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentsContent.class);
        AttachmentsContent attachmentsContent1 = new AttachmentsContent();
        attachmentsContent1.setId(1L);
        AttachmentsContent attachmentsContent2 = new AttachmentsContent();
        attachmentsContent2.setId(attachmentsContent1.getId());
        assertThat(attachmentsContent1).isEqualTo(attachmentsContent2);
        attachmentsContent2.setId(2L);
        assertThat(attachmentsContent1).isNotEqualTo(attachmentsContent2);
        attachmentsContent1.setId(null);
        assertThat(attachmentsContent1).isNotEqualTo(attachmentsContent2);
    }
}
