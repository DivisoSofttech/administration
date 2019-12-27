package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.RefundDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RefoundDetails} entity.
 */
public interface RefundDetailsSearchRepository extends ElasticsearchRepository<RefundDetails, Long> {
}
