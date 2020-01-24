package com.diviso.graeshoppe.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TicketIdGeneratorMapperTest {

    private TicketIdGeneratorMapper ticketIdGeneratorMapper;

    @BeforeEach
    public void setUp() {
        ticketIdGeneratorMapper = new TicketIdGeneratorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(ticketIdGeneratorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ticketIdGeneratorMapper.fromId(null)).isNull();
    }
}
