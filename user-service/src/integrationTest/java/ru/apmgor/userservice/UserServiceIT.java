package ru.apmgor.userservice;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.apmgor.userservice.dto.UserDto;
import ru.apmgor.userservice.generic.BaseTest;

import static ru.apmgor.userservice.controller.UserUtil.USER_PATH;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class UserServiceIT extends BaseTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void allUsersTest() {
        webTestClient.get()
                .uri(USER_PATH)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isNotEmpty();
    }

    @Test
    public void oneUserTest() {
        webTestClient.get()
                .uri(USER_PATH + "/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.name").isEqualTo("sam");
    }

    @Test
    public void oneUserFailureTest() {
        webTestClient.get()
                .uri(USER_PATH + "/100")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void saveUserTest() {
        var dto = new UserDto("rick", 6000);
        webTestClient.post()
                .uri(USER_PATH)
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader()
                .location(USER_PATH + "/5");
    }

    @Test
    public void updateUserTest() {
        var dto = new UserDto("john", 7000);
        webTestClient.put()
                .uri(USER_PATH + "/4")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    public void updateUserFailureTest() {
        var dto = new UserDto("john", 7000);
        webTestClient.put()
                .uri(USER_PATH + "/10")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    @Order(1)
    public void deleteUserTest() {
        webTestClient.delete()
                .uri(USER_PATH + "/2")
                .exchange()
                .expectStatus().is2xxSuccessful();
        webTestClient.get()
                .uri(USER_PATH)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(3);
    }
}
