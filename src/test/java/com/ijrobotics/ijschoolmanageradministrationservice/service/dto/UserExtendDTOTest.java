package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class UserExtendDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExtendDTO.class);
        UserExtendDTO userExtendDTO1 = new UserExtendDTO();
        userExtendDTO1.setId(1L);
        UserExtendDTO userExtendDTO2 = new UserExtendDTO();
        assertThat(userExtendDTO1).isNotEqualTo(userExtendDTO2);
        userExtendDTO2.setId(userExtendDTO1.getId());
        assertThat(userExtendDTO1).isEqualTo(userExtendDTO2);
        userExtendDTO2.setId(2L);
        assertThat(userExtendDTO1).isNotEqualTo(userExtendDTO2);
        userExtendDTO1.setId(null);
        assertThat(userExtendDTO1).isNotEqualTo(userExtendDTO2);
    }
}
