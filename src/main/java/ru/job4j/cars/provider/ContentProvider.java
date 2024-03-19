package ru.job4j.cars.provider;

public interface ContentProvider {
    byte[] getContent(String path);
}
