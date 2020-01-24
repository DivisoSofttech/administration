package com.diviso.graeshoppe.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Feedback.
 */
@Entity
@Table(name = "feedback")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "feedback")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "query")
    private String query;

    @Column(name = "subject")
    private String subject;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "date")
    private Instant date;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Feedback ticketId(String ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getQuery() {
        return query;
    }

    public Feedback query(String query) {
        this.query = query;
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSubject() {
        return subject;
    }

    public Feedback subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Feedback customerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Instant getDate() {
        return date;
    }

    public Feedback date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feedback)) {
            return false;
        }
        return id != null && id.equals(((Feedback) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Feedback{" +
            "id=" + getId() +
            ", ticketId='" + getTicketId() + "'" +
            ", query='" + getQuery() + "'" +
            ", subject='" + getSubject() + "'" +
            ", customerEmail='" + getCustomerEmail() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
