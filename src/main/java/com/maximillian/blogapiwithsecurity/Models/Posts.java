package com.maximillian.blogapiwithsecurity.Models;

import com.maximillian.blogapiwithsecurity.Audits.AuditEntity;
import com.maximillian.blogapiwithsecurity.Dto.PostsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Posts extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(length = 2000, nullable = false)
    private String content;
    private String image_url;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Users user;
    private String category;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comments> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Likes> likes = new ArrayList<>();

    public Posts(PostsDto postsDto){
        this.title = postsDto.getTitle();
        this.content = postsDto.getContent();
        this.image_url =postsDto.getImage_url();
        this.category = postsDto.getCategory();
    }
}
