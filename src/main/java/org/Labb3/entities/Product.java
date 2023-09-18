package org.Labb3.entities;

import org.Labb3.enums.categories;
import org.jetbrains.annotations.TestOnly;
import java.time.Clock;
import java.time.LocalDateTime;

public class Product {

    private static int productsCreated;
    private final int id;
    private final LocalDateTime created;
    private String name;
    private categories category;
    private int rating;
    private LocalDateTime modified;

    public Product(String name, categories category) {
        productsCreated++;
        this.id = productsCreated;
        this.name = name;
        this.category = category;
        this.created = LocalDateTime.now();
        this.modified = created;
    }

    @TestOnly
    public Product(int id, String name, categories category, Clock mockClock) {
        productsCreated++;
        this.id = id;
        this.name = name;
        this.category = category;
        this.created = LocalDateTime.now(mockClock);
        this.modified = created;
    }

    @TestOnly
    public Product(int id, String name, categories category) {
        productsCreated++;
        this.id = id;
        this.name = name;
        this.category = category;
        this.created = LocalDateTime.now();
        this.modified = created;
    }

    public static int getProductsCreated() {
        return productsCreated;
    }

    public Product setModified() {
        this.modified = LocalDateTime.now();
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public categories getCategory() {
        return category;
    }

    public Product setCategory(categories category) {
        this.category = category;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Product setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}

