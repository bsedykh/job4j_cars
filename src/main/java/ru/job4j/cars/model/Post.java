package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraph(
        name = "post",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode(value = "car", subgraph = "post.car"),
                @NamedAttributeNode("files")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "post.car",
                        attributeNodes = {
                                @NamedAttributeNode("category"),
                                @NamedAttributeNode(value = "model", subgraph = "post.car.model"),
                                @NamedAttributeNode("body"),
                                @NamedAttributeNode("engine")
                        }
                ),
                @NamedSubgraph(
                        name = "post.car.model",
                        attributeNodes = @NamedAttributeNode("brand")
                )
        }
)
@Table(name = "auto_post")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String description;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> participates = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToMany
    @JoinTable(
            name = "post_files",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "file_id") }
    )
    private Set<File> files = new HashSet<>();

    private int price;

    private boolean closed;
}
