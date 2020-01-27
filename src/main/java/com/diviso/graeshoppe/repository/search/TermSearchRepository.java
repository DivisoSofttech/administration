package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.Term;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Term} entity.
 */
public interface TermSearchRepository extends ElasticsearchRepository<Term, Long> {
}
