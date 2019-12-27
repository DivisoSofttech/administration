package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CancellationRequestMapperTest {

    private CancellationRequestMapper cancellationRequestMapper;

    @BeforeEach
    public void setUp() {
        cancellationRequestMapper = new CancellationRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(cancellationRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cancellationRequestMapper.fromId(null)).isNull();
    }
}
