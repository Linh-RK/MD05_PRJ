package com.ra.md05_project.model.entity.ver1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories") // Đã được ánh xạ bởi `categories` trong Movie
    private List<Movie> movies = new ArrayList<>();
}
