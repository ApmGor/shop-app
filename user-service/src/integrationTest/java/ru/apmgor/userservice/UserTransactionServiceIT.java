package ru.apmgor.userservice;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.apmgor.userservice.dto.UserTransactionStatusDto;
import ru.apmgor.userservice.generic.BaseTest;

import static ru.apmgor.userservice.controller.UserUtil.USER_TRANSACTION_PATH;
import static ru.apmgor.userservice.dto.TransactionStatus.APPROVED;
import static ru.apmgor.userservice.dto.TransactionStatus.DECLINED;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class UserTransactionServiceIT extends BaseTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    public void createTransactionTest() {
        var dto = new UserTransactionStatusDto(3, 250);
        webTestClient.post()
                .uri(USER_TRANSACTION_PATH)
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.status").isEqualTo(APPROVED.toString());
    }

    @Test
    public void createTransactionFailureTest() {
        var dto = new UserTransactionStatusDto(30, 250);
        webTestClient.post()
                .uri(USER_TRANSACTION_PATH)
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.status").isEqualTo(DECLINED.toString());
    }

    @Test
    @Order(2)
    public void allUserTransactionsTest() {
        webTestClient.get()
                .uri(USER_TRANSACTION_PATH + "?userId=3")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(1);
    }

}
