package messenger.backend.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.auth.dto.RefreshTokenDto;
import messenger.backend.auth.dto.RegisterRequestDto;
import messenger.backend.utils.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.LinkedHashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"/sql/clean.sql", "/sql/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    void setPort(int port) {
        RestAssured.port = port;
    }

    @Test
    void shouldLogin() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(
                new AuthRequestDto("user", "user")
        );

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void shouldRegister() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(
                new RegisterRequestDto("new_username", "Full Name", "pass")
        );

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void shouldLogout() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(
                new RefreshTokenDto(UUID.fromString("96810518-56a5-4786-96e2-4f7434dea41b"))
        );

        RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .post("/api/auth/logout")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void shouldGetMe() throws JsonProcessingException {
        RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/auth/me")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void shouldRefreshToken() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(
                new RefreshTokenDto(UUID.fromString("96810518-56a5-4786-96e2-4f7434dea41b"))
        );

        RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .post("/api/auth/refresh")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    private String getAccessToken() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(
                new AuthRequestDto("user", "user")
        );

        Response response = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .when()
                .post("/api/auth/login")
                .then()
                .extract()
                .as(Response.class);
        return (String) ((LinkedHashMap)response.getData()).get("accessToken");
    }
}