package messenger.backend.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import messenger.backend.auth.dto.RegisterRequestDto;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

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
}