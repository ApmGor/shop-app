package ru.apmgor.productservice;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.apmgor.productservice.dto.ProductDto;
import ru.apmgor.productservice.generic.BaseTest;

import static ru.apmgor.productservice.controller.RequestUtil.PRODUCT_PATH;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceIT extends BaseTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void allProductsTest() {
        client.get()
                .uri(PRODUCT_PATH)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isNotEmpty();
    }

    @Order(1)
    @Test
    public void oneProductSuccessTest() {
        client.get()
                .uri(PRODUCT_PATH + "/5")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.description").isEqualTo("XBOX One");
    }

    @Test
    public void oneProductFailureTest() {
        client.get()
                .uri(PRODUCT_PATH + "/10")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void saveProductTest() {
        var dto = new ProductDto("1", "Kingston SSD", 50);
        client.post()
                .uri(PRODUCT_PATH)
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader()
                .location(PRODUCT_PATH + "/1");
    }

    @Order(2)
    @Test
    public void updateProductSuccessTest() {
        var dto = new ProductDto(null, "Kingston SSD", 50);
        client.put()
                .uri(PRODUCT_PATH + "/5")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    public void updateProductFailureTest() {
        var dto = new ProductDto(null, "Kingston SSD", 50);
        client.put()
                .uri(PRODUCT_PATH + "/10")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void allProductsWithPriceBetweenTest() {
        client.get()
                .uri(PRODUCT_PATH + "/search?min=100&max=400")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(2);
    }


}
