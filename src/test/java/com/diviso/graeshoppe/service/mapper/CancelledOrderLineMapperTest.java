package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CancelledOrderLineMapperTest {

    private CancelledOrderLineMapper cancelledOrderLineMapper;

    @BeforeEach
    public void setUp() {
        cancelledOrderLineMapper = new CancelledOrderLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(cancelledOrderLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cancelledOrderLineMapper.fromId(null)).isNull();
    }
}
