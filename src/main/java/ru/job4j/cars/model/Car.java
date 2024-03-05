package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String brand;

    private String name;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Set<HistoryOwner> owners = new HashSet<>();
}
