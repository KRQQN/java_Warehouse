package org.Labb3.entities;

import org.Labb3.enums.categories;
import java.time.LocalDateTime;
import java.util.Optional;

public record ProductDto(int id, String name, categories category, int rating,
                         LocalDateTime created, LocalDateTime modified) {

    public static ProductDto objToRecord(Product p) {

        return new ProductDto(
          p.getId(),
          p.getName(),
          p.getCategory(),
          p.getRating(),
          p.getCreated(),
          p.getModified()
        );
    }

    public static ProductDto objToRecord(Optional<Product> p) {

        return p.map(product -> new ProductDto(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getRating(),
                product.getCreated(),
                product.getModified()
        )).orElseGet(() -> new ProductDto(
                0,
                "",
                categories.NA,
                0, LocalDateTime.now().minusYears(200),
                LocalDateTime.now().minusYears(200))
        );


    }

}
