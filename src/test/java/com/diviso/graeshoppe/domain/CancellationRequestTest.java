package com.diviso.graeshoppe.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class CancellationRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancellationRequest.class);
        CancellationRequest cancellationRequest1 = new CancellationRequest();
        cancellationRequest1.setId(1L);
        CancellationRequest cancellationRequest2 = new CancellationRequest();
        cancellationRequest2.setId(cancellationRequest1.getId());
        assertThat(cancellationRequest1).isEqualTo(cancellationRequest2);
        cancellationRequest2.setId(2L);
        assertThat(cancellationRequest1).isNotEqualTo(cancellationRequest2);
        cancellationRequest1.setId(null);
        assertThat(cancellationRequest1).isNotEqualTo(cancellationRequest2);
    }
}
