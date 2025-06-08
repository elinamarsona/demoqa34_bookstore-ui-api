package api;

import specs.Specs;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class BookStoreApiSteps {

    public static void addBook(String token, String userId, String isbn) {
        String requestBody = String.format(
                "{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                userId, isbn);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(Specs.responseSpec(201));
    }

    public static void deleteBook(String token, String userId, String isbn) {
        String requestBody = String.format(
                "{\"isbn\":\"%s\",\"userId\":\"%s\"}",
                isbn, userId);

        given()
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .log().all()
                .spec(Specs.responseSpec(204));
    }

    public static void deleteAllBooks(String token, String userId) {
        given()
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(Specs.responseSpec(204));
    }
}