package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AboutMapperTest {

    private AboutMapper aboutMapper;

    @BeforeEach
    public void setUp() {
        aboutMapper = new AboutMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(aboutMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aboutMapper.fromId(null)).isNull();
    }
}
