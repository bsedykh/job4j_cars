package ru.job4j.cars.dto;

public record PostDto(
        int categoryId,
        int modelId,
        int bodyId,
        int engineId,
        int year,
        int mileage,
        int price,
        String description
) {
}
