package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RefoundDetailsMapperTest {

    private RefoundDetailsMapper refoundDetailsMapper;

    @BeforeEach
    public void setUp() {
        refoundDetailsMapper = new RefoundDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(refoundDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(refoundDetailsMapper.fromId(null)).isNull();
    }
}
