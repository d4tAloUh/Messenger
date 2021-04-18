package messenger.backend.chat.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.utils.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"/sql/clean.sql", "/sql/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GeneralChatControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    void setPort(int port) {
        RestAssured.port = port;
    }

    @Test
    void shouldGetChatsList() throws JsonProcessingException {
        RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .when()
                .get("/api/chat/general/all")
                .then();
    }

    @Test
    void shouldGetSeenList() throws JsonProcessingException {
        RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .when()
                .get("/api/chat/general/seen")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void shouldRead() throws JsonProcessingException {
        RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .when()
                .post("/api/chat/general/read/51c07af2-5ed1-4e30-b054-e5a3d51da5a5")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    public String getAccessToken() throws JsonProcessingException {
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