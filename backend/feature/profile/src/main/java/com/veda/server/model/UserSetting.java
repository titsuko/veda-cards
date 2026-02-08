package com.veda.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Table(name = "user_settings")
public class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Size(max = 20)
    @Column(name = "theme", length = 20)
    private String theme = "LIGHT";

    @Size(max = 2)
    @Column(name = "lang", length = 2)
    private String lang = "ru";
}