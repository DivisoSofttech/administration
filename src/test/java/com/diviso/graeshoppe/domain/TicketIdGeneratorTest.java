package com.diviso.graeshoppe.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class TicketIdGeneratorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketIdGenerator.class);
        TicketIdGenerator ticketIdGenerator1 = new TicketIdGenerator();
        ticketIdGenerator1.setId(1L);
        TicketIdGenerator ticketIdGenerator2 = new TicketIdGenerator();
        ticketIdGenerator2.setId(ticketIdGenerator1.getId());
        assertThat(ticketIdGenerator1).isEqualTo(ticketIdGenerator2);
        ticketIdGenerator2.setId(2L);
        assertThat(ticketIdGenerator1).isNotEqualTo(ticketIdGenerator2);
        ticketIdGenerator1.setId(null);
        assertThat(ticketIdGenerator1).isNotEqualTo(ticketIdGenerator2);
    }
}
