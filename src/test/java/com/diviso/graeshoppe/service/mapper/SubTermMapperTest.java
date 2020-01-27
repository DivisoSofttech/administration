package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SubTermMapperTest {

    private SubTermMapper subTermMapper;

    @BeforeEach
    public void setUp() {
        subTermMapper = new SubTermMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(subTermMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(subTermMapper.fromId(null)).isNull();
    }
}
