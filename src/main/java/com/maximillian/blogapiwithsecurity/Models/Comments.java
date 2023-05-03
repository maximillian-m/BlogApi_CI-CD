package com.maximillian.blogapiwithsecurity.Models;

import com.maximillian.blogapiwithsecurity.Audits.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comments;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Posts post;
}
