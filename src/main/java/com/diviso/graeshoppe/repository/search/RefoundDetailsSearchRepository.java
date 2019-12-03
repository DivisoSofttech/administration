package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.RefoundDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefoundDetails entity.
 */
public interface RefoundDetailsSearchRepository extends ElasticsearchRepository<RefoundDetails, Long> {
}
