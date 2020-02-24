package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class GroupNoticesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupNoticesDTO.class);
        GroupNoticesDTO groupNoticesDTO1 = new GroupNoticesDTO();
        groupNoticesDTO1.setId(1L);
        GroupNoticesDTO groupNoticesDTO2 = new GroupNoticesDTO();
        assertThat(groupNoticesDTO1).isNotEqualTo(groupNoticesDTO2);
        groupNoticesDTO2.setId(groupNoticesDTO1.getId());
        assertThat(groupNoticesDTO1).isEqualTo(groupNoticesDTO2);
        groupNoticesDTO2.setId(2L);
        assertThat(groupNoticesDTO1).isNotEqualTo(groupNoticesDTO2);
        groupNoticesDTO1.setId(null);
        assertThat(groupNoticesDTO1).isNotEqualTo(groupNoticesDTO2);
    }
}
