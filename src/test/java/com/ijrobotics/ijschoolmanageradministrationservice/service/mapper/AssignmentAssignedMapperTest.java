package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AssignmentAssignedMapperTest {

    private AssignmentAssignedMapper assignmentAssignedMapper;

    @BeforeEach
    public void setUp() {
        assignmentAssignedMapper = new AssignmentAssignedMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(assignmentAssignedMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(assignmentAssignedMapper.fromId(null)).isNull();
    }
}
