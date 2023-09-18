package org.Labb3.service;

import org.Labb3.entities.ProductDto;
import org.junit.jupiter.api.Test;
import org.Labb3.enums.categories;
import java.time.*;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    Warehouse warehouse = new Warehouse();

    @Test
    void addProduct() {
        boolean successfulAdd = warehouse.addProduct(1, "NAME1", categories.CLASSIC);
        boolean failedAdd = warehouse.addProduct(2, "X", categories.CLASSIC); // "X" is too short for name

        assertTrue(successfulAdd);
        assertFalse(failedAdd);
    }

    @Test
    void getAllProducts() {
        warehouse.addProduct(1, "prodName1", categories.HEAVY_METAL);
        warehouse.addProduct(2, "prodName2", categories.HEAVY_METAL);

        int count = warehouse.getAllProducts().size();
        assertEquals(2, count);
    }

    @Test
    void getProductById() {
        warehouse.addProduct(1, "NAME", categories.HEAVY_METAL);
        assertEquals("NAME", warehouse.getProductById(1).name());
    }

    @Test
    void editProduct() {
        warehouse.addProduct(1, "NOT_EDITED", categories.DISCO);

        boolean successfulEdit = warehouse.editProduct(1, "EDITED", categories.CLASSIC, 5);
        boolean failedEdit = warehouse.editProduct(7, "", categories.DISCO, 9);

        assertEquals("EDITED", warehouse.getProductById(1).name());
        assertTrue(successfulEdit);
        assertFalse(failedEdit);
    }

    @Test
    void getProductFromDate() {
        Clock mockClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        Clock pastInTime = Clock.fixed(Instant.now().minusSeconds(600), ZoneId.systemDefault());
        LocalDateTime dateTime = LocalDateTime.now(mockClock);

        warehouse.addProduct(1, "NAME", categories.HEAVY_METAL, mockClock);
        warehouse.addProduct(2, "OTHERNAME", categories.HEAVY_METAL, mockClock);
        warehouse.addProduct(3, "OTHERERNAME", categories.HEAVY_METAL, pastInTime);
        // product id:3 is 10min behind current time

        // Gets all products from 5min back in time
        for (ProductDto product : warehouse.getProductsFromDate(dateTime.minusMinutes(5))) {
            assertEquals(dateTime, product.created());
        }
    }

    @Test
    void getModifiedProducts() throws InterruptedException {
        warehouse.addProduct(1, "PROD1", categories.HEAVY_METAL);
        warehouse.addProduct(2, "PROD2", categories.HEAVY_METAL);
        warehouse.addProduct(3, "PROD3", categories.HEAVY_METAL);

        Thread.sleep(100);
        warehouse.editProduct(2, "EDITED", categories.CLASSIC, 4);

        for (ProductDto product : warehouse.getModifiedProducts()) {
            assertTrue(product.created().isBefore(product.modified()));
        }
    }

    @Test
    void getProductsByCategory() {
        String searchInput = "HEAVY_METAL";

        warehouse.addProduct(1, "ZZZ", categories.HEAVY_METAL);
        warehouse.addProduct(2, "CCC", categories.CLASSIC);
        warehouse.addProduct(3, "BBB", categories.DISCO);
        warehouse.addProduct(4, "AAA", categories.HEAVY_METAL);
        warehouse.addProduct(5, "CCC", categories.HEAVY_METAL);
        warehouse.addProduct(6, "DDD", categories.HEAVY_METAL);

        List<ProductDto> categoryRes = warehouse.getProductsByCategory(searchInput);

        for (ProductDto prod : categoryRes) {
            assertEquals(prod.category().toString(), searchInput);
        }

        assertLinesMatch(
                List.of("AAA", "CCC", "DDD", "ZZZ"),
                categoryRes.stream().map(ProductDto::name).toList()
        );
    }
    @Test
    void ammountOfPruductsByFirstLetter() {
        warehouse.addProduct(1, "A-produkt", categories.OPERA);
        warehouse.addProduct(2, "B-produkt", categories.OPERA);
        warehouse.addProduct(3, "C-produkt", categories.OPERA);

        warehouse.addProduct(4, "A-produkt", categories.OPERA);
        warehouse.addProduct(5, "B-produkt", categories.OPERA);
        warehouse.addProduct(6, "B-produkt", categories.OPERA);

        Map<Character, Long> res = warehouse.ammountOfProductsByFirstLetter();

        assertEquals(2, res.get('A'));
        assertEquals(3, res.get('B'));
        assertEquals(1, res.get('C'));
    }

    @Test
    void ammountOfProductsByCategory() {
        warehouse.addProduct(1 , "PROD1", categories.DISCO);
        warehouse.addProduct(2 , "PROD2", categories.HEAVY_METAL);
        warehouse.addProduct(3 , "PROD3", categories.DISCO);
        warehouse.addProduct(4 , "PROD4", categories.DISCO);

        assertEquals(3, warehouse.ammountOfProductsByCategory("DISCO"));
        assertEquals(1, warehouse.ammountOfProductsByCategory("HEAVY_METAL"));
    }
    @Test
    void getCategoriesWithContent() {
        warehouse.addProduct(1 , "PROD1", categories.DISCO);
        warehouse.addProduct(2 , "PROD2", categories.DISCO);
        warehouse.addProduct(3 , "PROD3", categories.DISCO);

        warehouse.addProduct(4 , "PROD3", categories.CLASSIC);
        warehouse.addProduct(5 , "PROD3", categories.CLASSIC);

        var map = warehouse.getCategoriesWithContent();

        assertEquals(3, map.get(categories.valueOf("DISCO")).size());
        assertEquals(2, map.get(categories.valueOf("CLASSIC")).size());
        assertEquals(2, map.size());
    }

    @Test
    void getTopRatedThisMonth() {
        Clock mockClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        warehouse.addProduct(1, "produkt1", categories.DISCO, mockClock);
        warehouse.addProduct(2, "produkt2", categories.DISCO, mockClock);
        warehouse.addProduct(3, "produkt3", categories.DISCO, mockClock);

        warehouse.editProduct(1, "produkt1", categories.DISCO,10);
        warehouse.editProduct(2, "produkt2", categories.DISCO,10);
        warehouse.editProduct(3, "produkt3", categories.DISCO,10);

        for(ProductDto prod : warehouse.getTopRatedThisMonth()) {
            assertTrue(YearMonth.from(prod.created())
                    .equals(YearMonth.now(mockClock))
                    && prod.rating() == 10
            );

        }


    }




}