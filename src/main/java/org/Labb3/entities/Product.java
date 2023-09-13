package org.Labb3.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Product {

    private final int id;
    private String name;
    private String category;
    private int rating;
    private final LocalDateTime created;
    private LocalDateTime[] modified;
    private static int productsCreated;

    public Product(String name, String category) {
        productsCreated++;
        this.id = productsCreated;
        this.name = name;
        this.category = category;
        this.created = LocalDateTime.now();
    }


    public void test () {
        System.out.println("test");
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public Product setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public void setModified() {
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime[] getModified() {
        return modified;
    }

    public static int getProductsCreated() {
        return productsCreated;
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

