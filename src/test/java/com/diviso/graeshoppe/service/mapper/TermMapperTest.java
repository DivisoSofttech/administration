package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TermMapperTest {

    private TermMapper termMapper;

    @BeforeEach
    public void setUp() {
        termMapper = new TermMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(termMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(termMapper.fromId(null)).isNull();
    }
}
