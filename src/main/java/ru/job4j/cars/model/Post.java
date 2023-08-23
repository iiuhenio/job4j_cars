package ru.job4j.cars.model;

import javax.persistence.*;
import lombok.*;
import lombok.EqualsAndHashCode.Include;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String description;
    private Timestamp created;
    private int userId;
}