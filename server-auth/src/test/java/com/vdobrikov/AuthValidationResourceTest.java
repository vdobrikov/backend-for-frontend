package com.vdobrikov;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AuthValidationResourceTest {

    @Test
    public void testVerifyEndpoint() {
        given()
          .when().body("open-sesame").post("/validate")
          .then()
             .statusCode(200);
    }

    @Test
    public void testVerifyEndpointUnauthorized() {
        given()
          .when().body("wrong-token").post("/validate")
          .then()
             .statusCode(401);
    }

    @Test
    public void testVerifyEndpointTooShortToken() {
        given()
          .when().body("").post("/validate")
          .then()
             .statusCode(400);
    }
}