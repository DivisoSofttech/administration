package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RefundDetailsMapperTest {

    private RefundDetailsMapper refundDetailsMapper;

    @BeforeEach
    public void setUp() {
        refundDetailsMapper = new RefundDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(refundDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(refundDetailsMapper.fromId(null)).isNull();
    }
}
