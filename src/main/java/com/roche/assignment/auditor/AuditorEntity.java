package com.roche.assignment.auditor;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditorEntity<U> {

  // TODO could use ZonedDateTime
  @CreatedDate
  @Temporal(TIMESTAMP)
  @Column(name = "created_date")
  private Date createdDate;

  public Date getCreatedDate() {
    return createdDate;
  }
}
