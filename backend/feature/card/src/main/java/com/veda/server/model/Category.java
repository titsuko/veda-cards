package com.veda.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer id;

    @NotNull
    @Size(max = 100)
    @Column(name = "slug", unique = true, nullable = false, length = 100)
    private String slug;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "is_visible", nullable = false)
    private Byte isVisible = 1;

    @Column(name = "card_count")
    private Integer cardCount = 0;

}