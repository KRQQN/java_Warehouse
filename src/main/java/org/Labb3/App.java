package org.Labb3;

import org.Labb3.entities.ProductDto;
import org.Labb3.service.Warehouse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Function;

public class App {
    public static void main(String[] args) {
        Warehouse wh = new Warehouse();

        wh.addProduct("Flöjt", "GREJ");
        wh.addProduct("Munspel", "GREJ");
        wh.addProduct("Trumma", "GREJ");

        ArrayList<ProductDto> products = new ArrayList<>(wh.getAllProducts());

        var datesAfter = wh.getProductFromDate(LocalDateTime.now().minusMinutes(1));
        System.out.println(datesAfter);







        /*products.forEach(System.out::println);
        products.forEach(x -> System.out.println(x.id()));

        System.out.println(products.stream()
                .map(x -> x.name().contains("l"))
                .collect(Collectors.toList()));


        System.out.println(products.stream()
                .filter(x -> x.name().contains("l"))
                .collect(Collectors.toList()));*/



    }
}
