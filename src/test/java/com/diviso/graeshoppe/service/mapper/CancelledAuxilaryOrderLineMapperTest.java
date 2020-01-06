package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CancelledAuxilaryOrderLineMapperTest {

    private CancelledAuxilaryOrderLineMapper cancelledAuxilaryOrderLineMapper;

    @BeforeEach
    public void setUp() {
        cancelledAuxilaryOrderLineMapper = new CancelledAuxilaryOrderLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(cancelledAuxilaryOrderLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cancelledAuxilaryOrderLineMapper.fromId(null)).isNull();
    }
}
