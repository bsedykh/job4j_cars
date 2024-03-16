package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class File {
    private String name;
    private String path;
}
