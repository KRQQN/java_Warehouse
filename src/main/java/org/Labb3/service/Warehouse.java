package org.Labb3.service;

import org.Labb3.entities.Product;
import org.Labb3.entities.ProductDto;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Warehouse {
    Function<Product, Integer> getId = Product::getId;
    private final List<Product> products = new ArrayList<>();
    private LocalDateTime lastUpdated;

    //Todo: Fix proper constructor.
    public Warehouse() {
    }

    public void addProduct(@NotNull String name, @NotNull String category) {
        boolean validInput = (name.length()) >= 2 && (category.length() >= 2);

        if (validInput) { products.add(new Product(name, category));}
        else throw new IllegalArgumentException("Productname or category to short");
    }

    public List<ProductDto> getAllProducts() {
        return products.stream()
                .map(this::objToRecord)
                .toList();
    }

    public ProductDto getProductById(int id) {
        return objToRecord(findProductById(id));
    }

    public void editProduct(int id, String name, String category, int rating) {
        Product product = findProductById(id);

        Product edited = product
                .setCategory(category)
                .setRating(rating)
                .setName(name);

        products.remove(product);
        products.add(edited);
    }

    public List<ProductDto> getProductFromDate(LocalDateTime ltd) {
        return products.stream()
                .filter(p -> ltd.isBefore(p.getCreated()))
                .map(this::objToRecord)
                .toList();
    }

    public void getModifiedProducts() {

    }



/*    public Product getProductsByCategory(String category) {
        return product;
    }


    }*/

    private Product findProductById(int id) {
        return products.stream()
                .filter(obj -> obj.getId() == id)
                .findFirst()
                .orElseThrow();
    }

    private ProductDto objToRecord(Product p) {
        return new ProductDto(
                p.getId(),
                p.getName(),
                p.getCategory(),
                p.getRating(),
                p.getCreated(),
                p.getModified()
        );
    }
}
