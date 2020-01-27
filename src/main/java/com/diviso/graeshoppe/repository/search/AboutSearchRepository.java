package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.About;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link About} entity.
 */
public interface AboutSearchRepository extends ElasticsearchRepository<About, Long> {
}
