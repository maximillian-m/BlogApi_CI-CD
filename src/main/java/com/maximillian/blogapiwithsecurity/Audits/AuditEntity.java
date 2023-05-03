package com.maximillian.blogapiwithsecurity.Audits;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode
public abstract class AuditEntity implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name= "updated_At")
    private LocalDateTime updatedAt;
}
