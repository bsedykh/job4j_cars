package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "history_owners")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistoryOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id")
    private History history;
}
