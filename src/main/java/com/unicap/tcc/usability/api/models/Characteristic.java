package com.unicap.tcc.usability.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "characteristic")
public class Characteristic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category", columnDefinition = "varchar(2)")
    private String category;
    @Column(name = "key", columnDefinition = "varchar(3)")
    private String key;
    @Column(name = "description", columnDefinition = "text")
    private String description;
}
