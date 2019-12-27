package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.Notification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Notification} entity.
 */
public interface NotificationSearchRepository extends ElasticsearchRepository<Notification, Long> {
}
