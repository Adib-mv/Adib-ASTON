import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostmanTest {

    private static final String BASE_URI = "https://postman-echo.com";

    @Test
    public void testGetRequest() {
        given()
                .baseUri(BASE_URI)
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"))
                .body("url", containsString("foo1=bar1"));
    }

    @Test
    public void testPostRawText() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("test", "value");

        given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/post")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("data.test", equalTo("value"))
                .body("headers.content-type", containsString("application/json"));
    }

    @Test
    public void testPostFormData() {
        given()
                .baseUri(BASE_URI)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .when()
                .post("/post")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("form.foo1", equalTo("bar1"))
                .body("form.foo2", equalTo("bar2"));
    }

    @Test
    public void testPutRequest() {
        String requestBody = "This is expected to be sent back as part of response body.";

        given()
                .baseUri(BASE_URI)
                .body(requestBody)
                .when()
                .put("/put")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("data", equalTo(requestBody));
    }

    @Test
    public void testPatchRequest() {
        String requestBody = "This is expected to be sent back as part of response body.";

        given()
                .baseUri(BASE_URI)
                .body(requestBody)
                .when()
                .patch("/patch")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("data", equalTo(requestBody));
    }

    @Test
    public void testDeleteRequest() {
        String requestBody = "This is expected to be sent back as part of response body.";

        given()
                .baseUri(BASE_URI)
                .body(requestBody)
                .when()
                .delete("/delete")
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("data", equalTo(requestBody));
    }
}