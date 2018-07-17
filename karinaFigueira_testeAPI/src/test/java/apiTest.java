import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static org.junit.Assert.assertTrue;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class apiTest {

    private static RequestSpecification spec;

    @BeforeClass
    public static void initTest() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://viacep.com.br/")
                .build();
    }

    @Test
    public void shouldReturnStatusCode200() {

        given()
                .spec(spec)
                .when()
                .get("ws/93226260/json/")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldReturnValidCEP() {
          given()
                .spec(spec)
                .when()
                .get("ws/93226260/json/")
                .then()
                .body("cep", containsString("93226-260"));
    }

    @Test
    public void shouldReturnInvalidCEP() {
        given()
                .spec(spec)
                .when()
                .get("ws/99999990/json")
                .then()
                .body("erro", equalTo(true));
    }

    @Test
    public void shouldReturnBadRequest() {
        given()
                .spec(spec)
                .when()
                .get("ws/93230/json")
                .then()
                .statusCode(404);
    }
}
