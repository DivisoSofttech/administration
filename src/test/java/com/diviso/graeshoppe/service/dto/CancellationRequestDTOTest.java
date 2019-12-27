package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class CancellationRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancellationRequestDTO.class);
        CancellationRequestDTO cancellationRequestDTO1 = new CancellationRequestDTO();
        cancellationRequestDTO1.setId(1L);
        CancellationRequestDTO cancellationRequestDTO2 = new CancellationRequestDTO();
        assertThat(cancellationRequestDTO1).isNotEqualTo(cancellationRequestDTO2);
        cancellationRequestDTO2.setId(cancellationRequestDTO1.getId());
        assertThat(cancellationRequestDTO1).isEqualTo(cancellationRequestDTO2);
        cancellationRequestDTO2.setId(2L);
        assertThat(cancellationRequestDTO1).isNotEqualTo(cancellationRequestDTO2);
        cancellationRequestDTO1.setId(null);
        assertThat(cancellationRequestDTO1).isNotEqualTo(cancellationRequestDTO2);
    }
}
