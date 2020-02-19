package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class UserExtendTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExtend.class);
        UserExtend userExtend1 = new UserExtend();
        userExtend1.setId(1L);
        UserExtend userExtend2 = new UserExtend();
        userExtend2.setId(userExtend1.getId());
        assertThat(userExtend1).isEqualTo(userExtend2);
        userExtend2.setId(2L);
        assertThat(userExtend1).isNotEqualTo(userExtend2);
        userExtend1.setId(null);
        assertThat(userExtend1).isNotEqualTo(userExtend2);
    }
}
