package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class GroupNoticesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupNotices.class);
        GroupNotices groupNotices1 = new GroupNotices();
        groupNotices1.setId(1L);
        GroupNotices groupNotices2 = new GroupNotices();
        groupNotices2.setId(groupNotices1.getId());
        assertThat(groupNotices1).isEqualTo(groupNotices2);
        groupNotices2.setId(2L);
        assertThat(groupNotices1).isNotEqualTo(groupNotices2);
        groupNotices1.setId(null);
        assertThat(groupNotices1).isNotEqualTo(groupNotices2);
    }
}
