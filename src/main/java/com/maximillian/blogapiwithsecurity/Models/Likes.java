package com.maximillian.blogapiwithsecurity.Models;


import com.maximillian.blogapiwithsecurity.Audits.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Users user;
    @ManyToOne
    @JoinColumn(name = "post_id" ,referencedColumnName = "id")
    private Posts post;
    private Boolean isLiked = true;
}
