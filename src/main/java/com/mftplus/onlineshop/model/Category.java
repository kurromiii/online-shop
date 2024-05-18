package com.mftplus.onlineshop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "categoryEntity")
@Table(name = "category_tbl")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq")
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_deleted")
    private boolean deleted;



}