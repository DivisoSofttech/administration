package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.CancelledOrderLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CancelledOrderLine} entity.
 */
public interface CancelledOrderLineSearchRepository extends ElasticsearchRepository<CancelledOrderLine, Long> {
}
