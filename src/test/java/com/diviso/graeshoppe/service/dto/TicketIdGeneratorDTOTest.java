package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class TicketIdGeneratorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketIdGeneratorDTO.class);
        TicketIdGeneratorDTO ticketIdGeneratorDTO1 = new TicketIdGeneratorDTO();
        ticketIdGeneratorDTO1.setId(1L);
        TicketIdGeneratorDTO ticketIdGeneratorDTO2 = new TicketIdGeneratorDTO();
        assertThat(ticketIdGeneratorDTO1).isNotEqualTo(ticketIdGeneratorDTO2);
        ticketIdGeneratorDTO2.setId(ticketIdGeneratorDTO1.getId());
        assertThat(ticketIdGeneratorDTO1).isEqualTo(ticketIdGeneratorDTO2);
        ticketIdGeneratorDTO2.setId(2L);
        assertThat(ticketIdGeneratorDTO1).isNotEqualTo(ticketIdGeneratorDTO2);
        ticketIdGeneratorDTO1.setId(null);
        assertThat(ticketIdGeneratorDTO1).isNotEqualTo(ticketIdGeneratorDTO2);
    }
}
