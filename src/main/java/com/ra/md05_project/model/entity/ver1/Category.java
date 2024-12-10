package com.ra.md05_project.model.entity.ver1;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.math3.analysis.function.Abs;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private int categoryId;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @ManyToMany(mappedBy = "categories") // Đã được ánh xạ bởi `categories` trong Movie
    private List<Movie> movies = new ArrayList<>();
}
