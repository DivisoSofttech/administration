package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.CancelledAuxilaryOrderLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CancelledAuxilaryOrderLine} entity.
 */
public interface CancelledAuxilaryOrderLineSearchRepository extends ElasticsearchRepository<CancelledAuxilaryOrderLine, Long> {
}
