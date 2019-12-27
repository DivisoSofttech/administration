package com.diviso.graeshoppe.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CancellationRequestSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CancellationRequestSearchRepositoryMockConfiguration {

    @MockBean
    private CancellationRequestSearchRepository mockCancellationRequestSearchRepository;

}
