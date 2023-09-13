package org.Labb3.entities;

import java.time.LocalDateTime;

public record ProductDto(int id, String name, String category, int rating,
                         LocalDateTime created, LocalDateTime[] modified) {

}
