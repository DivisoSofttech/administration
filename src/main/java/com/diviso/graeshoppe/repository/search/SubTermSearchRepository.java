package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.SubTerm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SubTerm} entity.
 */
public interface SubTermSearchRepository extends ElasticsearchRepository<SubTerm, Long> {
}
