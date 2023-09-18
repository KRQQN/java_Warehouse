package org.Labb3.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.Labb3.entities.Product;
import org.Labb3.entities.ProductDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.Labb3.enums.categories;

import static java.util.stream.Collectors.counting;
import static org.Labb3.entities.ProductDto.objToRecord;


public final class Warehouse {
    private final List<Product> products;
    Predicate<String> validStrLength = value -> value.length() > 2;
    Predicate<Integer> validRating_0_10 = rating -> rating >= 0 && rating <= 10;

    public Warehouse() {
        this.products = new ArrayList<>();
    }

    public boolean addProduct(@NotNull String name, @NotNull categories category) {
        boolean res = validStrLength.test(name);

        if (res) products.add(new Product(name, category));
        return res;
    }

    @TestOnly
    public boolean addProduct(int id, @NotNull String name, @NotNull categories category, Clock mockClock) {
        boolean res = validStrLength.test(name);

        if (res) products.add(new Product(id, name, category, mockClock));
        return res;
    }

    @TestOnly
    public boolean addProduct(int id, @NotNull String name, @NotNull categories category) {
        boolean res = validStrLength.test(name);

        if (res && findProductById(id).isEmpty()) products.add(new Product(id, name, category));
        return res;
    }

    public List<ProductDto> getAllProducts() {
        return products.stream()
                .map(ProductDto::objToRecord)
                .toList();
    }

    public boolean editProduct(int id, String name, categories category, int rating) {
        Optional<Product> res = findProductById(id);
        boolean validName = validStrLength.test(name);
        boolean validRating = validRating_0_10.test(rating);

        if (validName && validRating && res.isPresent()) {
            res.ifPresent(product -> {
                product.setCategory(category)
                        .setRating(rating)
                        .setName(name)
                        .setModified();
            });
            return true;
        }
        return false;
    }

    public ProductDto getProductById(int id) {
        return objToRecord(findProductById(id));
    }

    public List<ProductDto> getProductsFromDate(LocalDateTime ldt) {
        return products.stream()
                .filter(p -> ldt.isBefore(p.getCreated()))
                .map(ProductDto::objToRecord)
                .toList();
    }

    public List<ProductDto> getModifiedProducts() {
        return products.stream()
                .map(ProductDto::objToRecord)
                .filter(product -> product.modified().isAfter(product.created()))
                .toList();
    }

    public List<ProductDto> getProductsByCategory(String categoryToFind) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .filter(prod -> prod.getCategory().equals(categories.valueOf(categoryToFind)))
                .map(ProductDto::objToRecord)
                .toList();
    }

    public Map<Character, Long> ammountOfProductsByFirstLetter() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        product -> product.getName().toUpperCase().charAt(0), counting()
                ));
    }

    public int ammountOfProductsByCategory(String category) {
        return getProductsByCategory(category).size();
    }

    public Map<categories, List<ProductDto>> getCategoriesWithContent() {
        return products.stream()
                .map(ProductDto::objToRecord)
                .collect(Collectors.groupingBy(ProductDto::category));
    }

    public List<ProductDto> getTopRatedThisMonth() {
        return products.stream()
                .map(ProductDto::objToRecord)
                .filter(product -> YearMonth.from(product.created()).equals(YearMonth.now()))
                .filter(product -> product.rating() == 10)
                .sorted(Comparator.comparing(ProductDto::created))
                .toList();
    }

    private @NotNull Optional<Product> findProductById(int id) {
        return products.stream()
                .filter(obj -> obj.getId() == id)
                .findFirst();
    }
}
