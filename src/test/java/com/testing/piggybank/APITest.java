package com.testing.piggybank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// API TEST
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APITest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void OK() {
        // 200 OK
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/transactions/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void BadRequest() {
        // 400 Bad Request

        // Act
        String url = "/api/v1/transactions/invalidInput";
        ResponseEntity<Void> response = restTemplate
                .getForEntity(url, Void.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void NotFound() {
        // 404 Not Found

        // Act
        String url = "/api/v1/404";
        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
