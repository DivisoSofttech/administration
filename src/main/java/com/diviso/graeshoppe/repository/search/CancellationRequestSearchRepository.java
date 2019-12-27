package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.CancellationRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CancellationRequest} entity.
 */
public interface CancellationRequestSearchRepository extends ElasticsearchRepository<CancellationRequest, Long> {
}
